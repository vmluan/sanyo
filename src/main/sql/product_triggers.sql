DROP FUNCTION IF EXISTS checkOverlap;
DELIMITER $$
CREATE FUNCTION checkOverlap(p_product_id  Int,
                                              p_start_date date,
                                              p_end_date date)
  RETURNS bool
  LANGUAGE SQL -- This element is optional and will be omitted from subsequent examples

BEGIN
                DECLARE exit_loop BOOLEAN;
                DECLARE v_count int(11) ;
				select count(*) into v_count
				from labour_price
				where product_id = p_product_id
				and expired_date is not null
				and issued_date is not null
				and  (date(p_start_date) between date(issued_date) and date(expired_date)
                                or date(p_end_date) between date(issued_date) and date(expired_date)
                                or date(issued_date) between date(p_start_date) and date(p_end_date)
                                or date(expired_date) between date(p_start_date) and date(p_end_date)
                                )
                                ;
if v_count > 0 then
  RETURN true;
else
    return false;
end if;
END;
$$
DELIMITER ;


### trigger
		DROP TRIGGER IF EXISTS productUpdateTrigger;
		DELIMITER //

		CREATE TRIGGER productUpdateTrigger
		before update
		   ON sanyo.product FOR EACH ROW

		BEGIN



			DECLARE is_overlapped BOOLEAN ;
                declare msg varchar(255);
                declare p_end_date date;
                declare usdToVnd FLOAT;
                declare v_count int(11);
                declare isUpdate boolean;

								set  isUpdate = FALSE;
                -- start date and end date must not be null
            if new.startDate is NULL || new.endDate is NULL THEN
             	set msg = 'Start Date and End Date must not be empty';
                signal sqlstate '46000' set message_text = msg;
            end if;
            ## do not allow to update past product
            if NEW.startDate < OLD.startDate || NEW.endDate < OLD.startDate then
            	set msg = concat('Time range is the past. Please select date after ',  date(OLD.startDate));
                signal sqlstate '46000' set message_text = msg;
            end if;
            if new.startDate > new.endDate THEN
            	set msg = 'End Date must be after Start Date ';
                signal sqlstate '46000' set message_text = msg;
            end if;
            select checkOverlap(old.product_id, new.startDate, new.endDate) into is_overlapped;
            if is_overlapped is true then
                set msg = 'Overlapped range.';
                signal sqlstate '45000' set message_text = msg;

            end if;
				if NEW.startDate > OLD.startDate then -- case of updating start date

							set p_end_date = date(subdate(NEW.startDate,1));

							## make sure end_date greater than or equal start_date
							## otherwise, set end_date = start_date
							if p_end_date < OLD.startDate THEN
								set p_end_date = OLD.startDate;
							end if;
							if date(NEW.startDate) > date (p_end_date) THEN
									set  isUpdate = TRUE;
							end if;
							if isUpdate is true then
									#### insert data to labour_price when start time changed. We ONLY keep the latest change for a day.
									#### If users make some price changes a days without changing start date, DO NOT store that history.
									INSERT INTO `sanyo`.`labour_price`
									(
									`EXPIRED_DATE`,
									`IN_PRICE`,
									`ISSUED_DATE`,
									`labour`,
									`max_w_o_tax_usd`,
									`max_w_o_tax_vnd`,
									`OUT_SALE_PRICE`,
									`OUT_WHLSE_PRICE`,
									`PRICE_TYPE`,
									`PRODUCT_ID`)
									VALUES
									(
									p_end_date,
									0,
									date(OLD.startDate),
									OLD.labour,
									OLD.TAX_USD,
									OLD.TAX_VND,
									0,
									0,
									null,
									OLD.PRODUCT_ID);

							end if;
    end if;


		if  (NEW.TAX_USD != OLD.TAX_USD
					|| NEW.TAX_VND != OLD.TAX_VND
					|| NEW.LABOUR != OLD.LABOUR)
				then
				begin

				-- Do not add new record to labour_price table as we dont keep those changes unless user changes date time.

					-- check if there is a record exists in labour_price between old.startDate and old.endDate.
					-- if not, insert new record
					-- if yes, just update those 3 fields.


/*
					select count(*) into v_count
					from `sanyo`.`labour_price`
					where PRODUCT_ID = OLD.PRODUCT_ID
					AND ISSUED_DATE = date(OLD.startDate)
					AND EXPIRED_DATE = date(OLD.endDate);




					if v_count = 0 then
							INSERT INTO `sanyo`.`labour_price`
							(
							`EXPIRED_DATE`,
							`IN_PRICE`,
							`ISSUED_DATE`,
							`labour`,
							`max_w_o_tax_usd`,
							`max_w_o_tax_vnd`,
							`OUT_SALE_PRICE`,
							`OUT_WHLSE_PRICE`,
							`PRICE_TYPE`,
							`PRODUCT_ID`)
							VALUES
							(
							date(now()),
							0,
							date(OLD.startDate),
							OLD.labour,
							OLD.TAX_USD,
							OLD.TAX_VND,
							0,
							0,
							null,
							OLD.PRODUCT_ID);
					else
							update `sanyo`.`labour_price`
							set labour = OLD.labour
									, max_w_o_tax_usd = OLD.TAX_USD
									, max_w_o_tax_vnd = OLD.TAX_VND
							where PRODUCT_ID = OLD.PRODUCT_ID
							AND ISSUED_DATE = date(OLD.startDate)
							AND EXPIRED_DATE = date(OLD.endDate);

					end if;
*/
					update sanyo.encounter a, sanyo.region b, sanyo.location l, sanyo.project p
					set
					  -- a.needUpdatePrice = 1,
						a.TAX_USD = NEW.TAX_USD
						, a.TAX_VND = NEW.TAX_VND
						, a.LABOUR = NEW.LABOUR
						,a.Unit_Price_After_Discount = NEW.TAX_VND / p.usdToVnd + NEW.TAX_USD
						, a.UNIT_RATE = ((NEW.TAX_VND / p.usdToVnd + NEW.TAX_USD)* p.allowance) /100
						, a.Unit_Price_W_Tax_Profit =  ( NEW.TAX_VND / p.usdToVnd + NEW.TAX_USD)*(1+(1+(p.specialTax/100)*(1+p.impTax/100))*(p.vat/100))*(p.discountRate/100)
						-- unit_Price_After_Discount*(1+(1+specialCon*(1+impTax))*vat)*discountRate;
						, a.Unit_Price_W_Tax_Labour = NEW.LABOUR* a.Subcon_Profit/100
						, a.Cost_Mat_Amount_USD =(NEW.TAX_VND / p.usdToVnd + NEW.TAX_USD)*a.quantity
						, a.Cost_Labour_Amount_USD = (NEW.LABOUR* a.Subcon_Profit/100) * a.quantity
						, a.labourAfterTax = (NEW.LABOUR* a.Subcon_Profit/100) * a.ACTUAL_QUANTITY
						, a.amount = (((NEW.TAX_VND / p.usdToVnd + NEW.TAX_USD)* p.allowance) /100) * a.ACTUAL_QUANTITY
					where a.product_id = NEW.product_id
					and a.ENCOUNTER_TIME between OLD.startDate and OLD.endDate
					and a.region_id = b.region_id
					and b.location_id = l.location_id
					and l.project_id = p.project_id
					and p.status=0 -- still open
						;
				END;
		end if;


		END; //

		DELIMITER ;


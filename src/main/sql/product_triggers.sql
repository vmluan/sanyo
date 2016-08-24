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
            ## do not allow to update past product
            if NEW.startDate < OLD.startDate || NEW.endDate < OLD.startDate then
            	set msg = concat('Time range is the past. Please select date after ',  date(OLD.startDate));
                signal sqlstate '46000' set message_text = msg;
            end if;
            select checkOverlap(old.product_id, new.startDate, new.endDate) into is_overlapped;
            if is_overlapped is true then
                set msg = 'Overlapped range.';
                signal sqlstate '45000' set message_text = msg;
            else
				if NEW.startDate <> OLD.startDate || NEW.endDate <> OLD.endDate then

					set p_end_date = OLD.endDate;
					if p_end_date is null THEN
					 	set p_end_date = now();
					end if;
					## make sure end_date greater than or equal start_date
					## otherwise, set end_date = start_date
					if p_end_date < OLD.startDate THEN
						set p_end_date = OLD.startDate;
					end if;
					#### insert data to labour_price
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
				if NEW.TAX_USD <=> OLD.TAX_USD || NEW.TAX_VND <=> OLD.TAX_VND
					|| NEW.LABOUR <> OLD.LABOUR
				then
				begin
					update sanyo.encounter
					set needUpdatePrice = 1
					where product_id = NEW.product_id
					and ENCOUNTER_TIME between OLD.startDate and OLD.endDate;
				END;
				end if;
             end if;


		END; //

		DELIMITER ;


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
				and  (issued_date between p_start_date and p_end_date
                                or expired_date is not null and expired_date between p_start_date and p_end_date
                                )
                                ;
if v_count > 0 then                                
  RETURN true;
else
				select count(*) into v_count
				from product
				where product_id = p_product_id
				and  (startDate between p_start_date and p_end_date
                                or endDate is not null and endDate between p_start_date and p_end_date
                                )
                                ;
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
            select checkOverlap(old.product_id, new.startDate, new.endDate) into is_overlapped;
            if is_overlapped is true then
                set msg = 'Overllapped range.';
                signal sqlstate '45000' set message_text = msg;
            else
				if NEW.startDate <> OLD.startDate || NEW.endDate <> OLD.endDate then
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
					date(NEW.endDate),
					0,
					date(NEW.startDate),
					NEW.labour,
					NEW.TAX_USD,
					NEW.TAX_VND,
					0,
					0,
					null,
					NEW.PRODUCT_ID);
                end if;    
             end if;   
		END; //

		DELIMITER ;	
        

### trigger
		DROP TRIGGER IF EXISTS productAddTrigger;
		DELIMITER //

		CREATE TRIGGER productAddTrigger
		after insert
		   ON sanyo.product FOR EACH ROW
		   
		BEGIN
			
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
			date(NEW.endDate),
			0,
			date(NEW.startDate),
			NEW.labour,
			NEW.TAX_USD,
			NEW.TAX_VND,
			0,
			0,
			null,
			NEW.PRODUCT_ID);
                
		END; //

		DELIMITER ;	
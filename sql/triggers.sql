

-- 2. trigger when delete makerProject

		   -- get projectId, then find all encounter of that project in a cursor. get list of productGroupMaker
		   -- each encounter in cursor: 
		   		-- get product, then get productGroup
		   		-- check if that productGroup is in the above productGroupMaker list
		   			-- if yes, it is ok
		   			-- if no, update encounter set hasMakerDelete to true
	   					
		DROP TRIGGER IF EXISTS makerProjectDelte;
		DELIMITER //

		CREATE TRIGGER makerProjectDelte
		before delete
		   ON sanyo.maker_project FOR EACH ROW
		BEGIN
			declare v_encounter int(11);
			DECLARE exit_loop BOOLEAN; 
		begin
			
			 -- declare cursor for employee email
			 DEClARE encounter_cursor CURSOR FOR 
				select distinct e.encounter_id
				from encounter e, region r, location l, project p,
					  productgroup pg, product pro
				where e.region_id = r.REGION_ID
				and r.LOCATION_ID = l.LOCATION_ID
				and l.PROJECT_ID = p.PROJECT_ID
				and p.PROJECT_ID = old.project_id
				and pro.product_group_id = pg.product_group_id
				and e.PRODUCT_ID = pro.PRODUCT_ID
	            and not exists (select * from maker_project mp, productgroup_maker pgm
								where mp.id <> old.id
                                and mp.product_group_maker_id = pgm.id
                                and pgm.Maker_id = pro.maker_id
								)
				;
				-- declare NOT FOUND handler
				 DECLARE CONTINUE HANDLER 
				        FOR NOT FOUND SET exit_loop = true;
				 OPEN encounter_cursor;
				 
				 get_encounter: LOOP
				     IF exit_loop THEN
				         CLOSE encounter_cursor;
				         LEAVE get_encounter;
				     END IF;				 
					 FETCH encounter_cursor INTO v_encounter;
					update sanyo.encounter
					set hasMakerDeleted = true
					where encounter_id = v_encounter;
			
				END LOOP get_encounter;
		END;	

		END; //

		DELIMITER ;


-- 3.Update discount rate and allowance from productgrouprate table (Code table)


		DROP TRIGGER IF EXISTS discountAllowanceUpdateTrigger;
		DELIMITER //

		CREATE TRIGGER discountAllowanceUpdateTrigger
		after UPDATE
		   ON sanyo.productgrouprate FOR EACH ROW


		BEGIN
			declare  v_discount_rate FLOAT;
			declare v_allowance_rate FLOAT;
           
		begin

			-- if new.Discount_rate <> OLD.Discount_rate or NEW.ALLOWANCE <> OLD.ALLOWANCE then
            -- update discount rate and allowance rate in encounter
			select discount_rate into v_discount_rate from productgrouprate g where g.product_group_id=NEW.product_group_id AND g.PROJECT_ID=NEW.PROJECT_ID;
			select allowance_rate into v_allowance_rate from productgrouprate g where g.product_group_id=NEW.product_group_id AND g.PROJECT_ID=NEW.PROJECT_ID;
            
            -- set v_unit_price_w_tax_profit = unit_Price_After_Discount*(1+(1+specialCon*(1+impTax))*vat)*discountRate;
			update encounter
			SET Discount_rate = v_discount_rate
           , ALLOWANCE=v_allowance_rate
            , Unit_Price_W_Tax_Profit = cast(Unit_Price_After_Discount * (1+(1+Special_Con_Tax*(1+imp_tax))*vat)*(v_discount_rate/100) as decimal(12,4))
            , unit_rate = cast((v_allowance_rate/100) * Unit_Price_After_Discount as decimal(12,4))
            , amount = cast(ACTUAL_QUANTITY * ((v_allowance_rate/100) * Unit_Price_After_Discount) as decimal(12,4))
			WHERE
            PRODUCT_ID in (select PRODUCT_ID from product p where p.product_group_id=NEW.product_group_id)
			AND REGION_ID in (select REGION_ID from region r where r.LOCATION_ID in (select LOCATION_ID from location l where l.PROJECT_ID=NEW.PROJECT_ID));
            
            -- update unit_Price_W_Tax_Profit = unit_Price_After_Discount*(1+(1+specialCon*(1+impTax))*vat)*discountRate;
            -- update unitRate = v_allowance_rate * Unit_Price_After_Discount
            -- update amount = Number(quantity) * Number(unitRate)

		END;

		END; //

		DELIMITER ;


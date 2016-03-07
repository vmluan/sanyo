drop function IF EXISTS SPLIT_STR;

CREATE FUNCTION SPLIT_STR(
  x VARCHAR(255),
  delim VARCHAR(12),
  pos INT
)
RETURNS VARCHAR(255)
RETURN REPLACE(SUBSTRING(SUBSTRING_INDEX(x, delim, pos),
       LENGTH(SUBSTRING_INDEX(x, delim, pos -1)) + 1),
       delim, '');
    
DROP TRIGGER IF EXISTS encounterAddTrigger;
		DELIMITER //

		CREATE TRIGGER encounterAddTrigger
		before insert
		   ON sanyo.encounter FOR EACH ROW
		   
		BEGIN
			declare v_max_orderNo int(11);
                declare v_total_usd int(11);
                declare v_start int(3);
                declare v_order_no int(3);
                declare v_total float;
                declare v_cost_Mat_Amount_USD float;
                declare v_percent_value float;
		begin
                if TRIM(NEW.nonameRange) is not null and TRIM(NEW.nonameRange)  <> '' then
                    -- slplit range into an array
                    set v_total_usd =0; -- just for testing
                    set v_start = 1;
                    set v_order_no= 0;
                    
                    while v_start < length(NEW.nonameRange) do
                        set v_start = v_start + 1;
                        select SPLIT_STR(NEW.nonameRange, '-', v_start) into v_order_no;
                        select  cost_Mat_Amount_USD into v_cost_Mat_Amount_USD
                        from encounter
                        where region_id = NEW.region_id
                        and order_no = v_order_no;
                        
                        set v_total = v_total + v_cost_Mat_Amount_USD;
                    end while;
                    if v_total > 0 then
                        set v_percent_value = CAST(NEW.nonamePercent * v_total as DECIMAL(20,5));
                        set NEW.cost_Mat_Amount_USD = v_percent_value;
                    end if;
                end if;
			select max(order_No) into v_max_orderNo
			from sanyo.encounter
			where region_Id=NEW.region_Id;
			
            if v_max_orderNo is null then
				set v_max_orderNo =0;
            end if;
			set NEW.order_No = v_max_orderNo +1;

		END;

		END; //

		DELIMITER ;

		
		DROP TRIGGER IF EXISTS encounterOrderHistInsertTrigger;
		DELIMITER //

		CREATE TRIGGER encounterOrderHistInsertTrigger
		before insert
		   ON sanyo.encounter_order_hist FOR EACH ROW
		BEGIN
			declare v_encounter_id int(11);
			declare v_start_pos int(11);
			declare v_end_pos int(11);
			declare v_region_id int(11);
			declare v_source_encounter_id int(11);
			
			DECLARE exit_loop BOOLEAN;
			declare is_increased boolean;
			if NEW.fromPos > NEW.toPos then
				set v_start_pos = new.toPos;
				set v_end_pos = new.fromPos;
				set is_increased = false;
			else
				set v_start_pos = new.fromPos;
				set v_end_pos = new.toPos;
				set is_increased = true;
			end if;
			
			set v_source_encounter_id = new.encounter_id;
			select region_id into v_region_id
			from sanyo.encounter
			where encounter_id = new.encounter_id;
            
		begin
			
				if is_increased then
					update sanyo.encounter
					set order_No = order_No -1,
					dataTableChange = false
					where region_id = v_region_id
					and order_No >= v_start_pos
					and order_No <= v_end_pos
					and encounter_id <> v_source_encounter_id;
				else
					update sanyo.encounter
					set order_No = order_No + 1,
					dataTableChange = false
					where region_id = v_region_id
					and order_No >= v_start_pos
					and order_No <= v_end_pos
					and encounter_id <> v_source_encounter_id;
				end if;				
		END;

		END; //

		DELIMITER ;



DROP TRIGGER IF EXISTS encounterInsertTrigger;
		DELIMITER //

		CREATE TRIGGER encounterInsertTrigger
		after INSERT
		   ON sanyo.encounter FOR EACH ROW

		BEGIN
			declare encounter_total_material FLOAT;
			declare encounter_total_labour FLOAT;
			declare v_project_id int(11);
			declare v_product_group_id int(11);

		begin


			select project_id into v_project_id from location l where l.LOCATION_ID in (SELECT LOCATION_ID from region where REGION_ID=NEW.REGION_ID);
			select product_group_id into v_product_group_id from product p where p.PRODUCT_ID in (SELECT PRODUCT_ID from encounter where PRODUCT_ID=NEW.PRODUCT_ID);

			select sum(Cost_Mat_Amount_USD),sum(Cost_Labour_Amount_USD) into encounter_total_material, encounter_total_labour
			FROM encounter
			WHERE PRODUCT_ID in (select PRODUCT_ID from product where product_group_id=v_product_group_id) AND
			REGION_ID in (select REGION_ID from region r where r.LOCATION_ID in (select LOCATION_ID from location l where l.PROJECT_ID=v_project_id ));
			update productgrouprate
			SET total_material = encounter_total_material, total_labor=encounter_total_labour
			WHERE  product_group_id = v_product_group_id AND PROJECT_ID=v_project_id;
		END;

		END; //

		DELIMITER ;



DROP TRIGGER IF EXISTS encounterUpdateTrigger;
		DELIMITER //

		CREATE TRIGGER encounterUpdateTrigger
		after UPDATE
		   ON sanyo.encounter FOR EACH ROW

		BEGIN
			declare encounter_total_material FLOAT;
			declare encounter_total_labour FLOAT;
			declare v_project_id int(11);
			declare v_product_group_id int(11);

		begin

			-- Update total material and total labour
			select project_id into v_project_id from location l where l.LOCATION_ID in (SELECT LOCATION_ID from region where REGION_ID=NEW.REGION_ID);
			select product_group_id into v_product_group_id from product p where p.PRODUCT_ID in (SELECT PRODUCT_ID from encounter where PRODUCT_ID=NEW.PRODUCT_ID);

			select sum(Cost_Mat_Amount_USD),sum(Cost_Labour_Amount_USD) into encounter_total_material, encounter_total_labour
			FROM encounter
			WHERE PRODUCT_ID in (select PRODUCT_ID from product where product_group_id=v_product_group_id) AND
			REGION_ID in (select REGION_ID from region r where r.LOCATION_ID in (select LOCATION_ID from location l where l.PROJECT_ID=v_project_id ));
			update productgrouprate
			SET total_material = encounter_total_material, total_labor=encounter_total_labour
			WHERE  product_group_id = v_product_group_id AND PROJECT_ID=v_project_id;
		END;

		END; //

		DELIMITER ;


-- after Delete Encounter
DROP TRIGGER IF EXISTS encounterDeleteTrigger;
		DELIMITER //

		CREATE TRIGGER encounterDeleteTrigger
		after DELETE
		   ON sanyo.encounter FOR EACH ROW

		BEGIN
			declare encounter_total_material FLOAT;
			declare encounter_total_labour FLOAT;
			declare v_project_id int(11);
			declare v_product_group_id int(11);

		begin

			-- Update total material and total labour
			select project_id into v_project_id 
			from location l 
			where l.LOCATION_ID in (SELECT LOCATION_ID from region where REGION_ID=OLD.REGION_ID);
			select product_group_id into v_product_group_id from product p where p.PRODUCT_ID in (SELECT PRODUCT_ID from encounter where PRODUCT_ID=OLD.PRODUCT_ID);

			select 
				CAST(SUM(Cost_Mat_Amount_USD) as DECIMAL(20,5))
				,CAST(SUM(Cost_Labour_Amount_USD)as DECIMAL(20,5))			
			into encounter_total_material, encounter_total_labour
			FROM encounter
			WHERE PRODUCT_ID in (select PRODUCT_ID from product where product_group_id=v_product_group_id) AND
			REGION_ID in (select REGION_ID from region r where r.LOCATION_ID in (select LOCATION_ID from location l where l.PROJECT_ID=v_project_id ));
			update productgrouprate
			SET total_material = encounter_total_material, total_labor=encounter_total_labour
			WHERE  product_group_id = v_product_group_id AND PROJECT_ID=v_project_id;
		END;

		END; //

		DELIMITER ;

DROP TRIGGER IF EXISTS encounterAddTrigger;
		DELIMITER //

		CREATE TRIGGER encounterAddTrigger
		before insert
		   ON sanyo.encounter FOR EACH ROW
		   
		BEGIN
			declare v_max_orderNo int(11);
			declare encounter_total_material FLOAT;
			declare v_project_id FLOAT;
			declare v_product_group_id FLOAT;

		begin
			select max(order_No) into v_max_orderNo
			from sanyo.encounter
			where region_Id=NEW.region_Id;
			
            if v_max_orderNo is null then
				set v_max_orderNo =0;
            end if;
			set NEW.order_No = v_max_orderNo +1;

			-- Update total material
			select project_id into v_project_id from location l where l.LOCATION_ID in (SELECT LOCATION_ID from region where REGION_ID=NEW.REGION_ID);
			select product_group_id into v_product_group_id from product p where p.PRODUCT_ID in (SELECT PRODUCT_ID from encounter where PRODUCT_ID=NEW.PRODUCT_ID);

			select sum (Cost_Mat_Amount_USD) into encounter_total_material
			FROM encounter
			WHERE PRODUCT_ID in (select PRODUCT_ID from product where product_group_id=v_product_group_id) AND
			REGION_ID in (select REGION_ID from region r where r.LOCATION_ID in (select LOCATION_ID from location l where l.PROJECT_ID=v_project_id ));
			update productgrouprate
			SET total_material = encounter_total_material
			WHERE  product_group_id = v_product_group_id AND PROJECT_ID=v_project_id;
		END;

		END; //

		DELIMITER ;
		
		
		
		/*
		DROP TRIGGER IF EXISTS encounterUpDateTrigger;
		DELIMITER //

		CREATE TRIGGER encounterUpDateTrigger
		after update
		   ON sanyo.encounter FOR EACH ROW
		   
		BEGIN
			declare v_encounter_id int(11);
			declare v_start_pos int(11);
			declare v_end_pos int(11);
			DECLARE exit_loop BOOLEAN;
			declare is_increased boolean;
			if old.order_no <> new.order_no && new.dataTableChange = true then
				insert into encounter_order_hist(encounter_id, fromPos, toPos)
				values(old.encounter_id,old.order_no, new.order_no);
			end if;			

		END; //

		DELIMITER ;
		
		*/
		
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
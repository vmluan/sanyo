DROP TRIGGER IF EXISTS encounterAddTrigger;
		DELIMITER //

		CREATE TRIGGER encounterAddTrigger
		before insert
		   ON sanyo.encounter FOR EACH ROW
		   
		BEGIN
			declare v_max_orderNo int(11);
		begin
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



		DROP TRIGGER IF EXISTS encounterTotalMaterialByCodeInsertTrigger;
		DELIMITER //

		CREATE TRIGGER encounterTotalMaterialByCodeInsertTrigger
		before insert
		   ON sanyo.encounter FOR EACH ROW
		BEGIN
			declare encounter_total FLOAT;
			select project_id into v_project_id from location l where l.LOCATION_ID in (SELECT LOCATION_ID in region where REGION_ID=NEW.REGION_ID)
			--chuong is doing here
			if   then
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
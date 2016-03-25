DROP TRIGGER IF EXISTS locationAddTrigger;
		DELIMITER //

		CREATE TRIGGER locationAddTrigger
		before insert
		   ON sanyo.location FOR EACH ROW
		   
		BEGIN
			declare v_max_orderNo int(11);
		begin 
			select max(orderNo) into v_max_orderNo
			from sanyo.location
			where project_id=NEW.project_id;
			
            if v_max_orderNo is null then
				set v_max_orderNo =0;
            end if;
			set NEW.orderNo = v_max_orderNo +1;        
		END;

		END; //

		DELIMITER ;
		
		
		
	
		### for udpate trigger
DROP TRIGGER IF EXISTS locationOrderHistAddTrigger;
		DELIMITER //

		CREATE TRIGGER locationOrderHistAddTrigger
		after insert
		   ON sanyo.location_order_hist FOR EACH ROW
		BEGIN
			declare v_location_id int(11);
			declare v_start_pos int(11);
			declare v_end_pos int(11);
			declare v_project_id int(11);
			declare v_source_location_id int(11);
            declare v_current_pos int(11);
			DECLARE exit_loop BOOLEAN;
			declare is_increased boolean;
            
            select orderNo into v_current_pos
            from location
            where location_id = new.location_id;
            
			if v_current_pos = NEW.fromPos then
				if NEW.fromPos > NEW.toPos then
					set v_start_pos = new.toPos;
					set v_end_pos = new.fromPos;
					set is_increased = false;
				else
					set v_start_pos = new.fromPos;
					set v_end_pos = new.toPos;
					set is_increased = true;
				end if;
				
				set v_source_location_id = new.location_id;
				select project_id into v_project_id
				from sanyo.location
				where location_id = new.location_id;
            
				begin
					
						if is_increased then
							update sanyo.location
							set orderNo = orderNo -1
							where project_id = v_project_id
							and orderNo >= v_start_pos
							and orderNo <= v_end_pos
							and location_id <> v_source_location_id;
						else
							update sanyo.location
							set orderNo = orderNo +1
							where project_id = v_project_id
							and orderNo >= v_start_pos
							and orderNo <= v_end_pos
							and location_id <> v_source_location_id;
						end if;
						
						update location
						set orderNo = new.toPos
						where location_id=v_source_location_id;
				END;
			end if;

		END; //

		DELIMITER ;
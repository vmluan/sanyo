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
		
		
		
		
		DROP TRIGGER IF EXISTS locationDeleteTrigger;
		DELIMITER //

		CREATE TRIGGER locationDeleteTrigger
		before delete
		   ON sanyo.location FOR EACH ROW
		   
		BEGIN
			declare v_locaion_id int(11);
			DECLARE exit_loop BOOLEAN; 
		begin 
			DEClARE location_cursor CURSOR FOR
				select location_id
				from location
				where project_id = OLD.project_id
				and orderNo > OLD.orderNo;
				 DECLARE CONTINUE HANDLER 
				        FOR NOT FOUND SET exit_loop = true;
				        
				 OPEN location_cursor;
				 
				 get_location: LOOP
				     IF exit_loop THEN
				         CLOSE location_cursor;
				         LEAVE get_location;
				     END IF;				 
					 FETCH location_cursor INTO v_locaion_id;
					update sanyo.location
					set orderNo = orderNo -1
					where location_id = v_locaion_id;
			
				END LOOP get_location;
		END;

		END; //

		DELIMITER ;		
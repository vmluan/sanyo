# sanyo
references links for JPA documentation:
http://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-three-custom-queries-with-query-methods/

http://docs.spring.io/spring-data/data-jpa/docs/current/reference/html/#repositories.query-methods.details

html template: http://startbootstrap.com/template-overviews/sb-admin/


cau truc project:
 - repository package:
 	+ contains class to interactive to database. It uses JPA.
 	+ query is created by naming conventions in JPA
 	Example class Person {
 				private String name
 				private String email;
 			}
 			PersonRepository{
 				public List<Person> findByName(String name); // it will use built-in function implemented by JPA. no need to override this method.
 				public Person findByNameAndEmail(String name, String email); // search by name and email. it will use built-in function implemented by JPA. no need to override this method.
 			}
 			
 			
 			
Every price is updated, we should update related quotation but how?
 - there will be a button in project page (or quotation page), it will be enabled when there is any price updated.
 - how to know if a price is updated?
 	- when a product is updated for 3 fields: price, start date, end date
 		- find all encounter that have that product
 		- Encounter --> region --> location --> project.
 	- add a new field: needUpdatePrice to Project.
 	- add a new filed  needUpdatePrice to Encounter will have faster performance?
 	
 	-- we can have an alternative to have a trigger
	
		DROP TRIGGER IF EXISTS productUpdate;
		DELIMITER //

		CREATE TRIGGER productUpdate
		AFTER UPDATE
		   ON sanyo.product FOR EACH ROW
		   
		BEGIN
		if NEW.TAX_USD <=> OLD.TAX_USD || NEW.TAX_VND <=> OLD.TAX_VND then
		begin 
			update sanyo.encounter
			set needUpdatePrice = 1
			where product_id = NEW.product_id
			and ENCOUNTER_TIME between OLD.startDate and OLD.endDate;
		END;	
		end if;

		END; //

		DELIMITER ;
		
when the trigger fired?
- case 1: price is updated only. start date and end date is not changed. -->Update current price.
- case 2: price, start date and end date are updated. it is a new price for new duration. -->Add new price for new duration.

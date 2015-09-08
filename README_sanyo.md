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
 			
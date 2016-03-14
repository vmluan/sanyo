Implementation plan
------------
1. Very Important note: create sanyo database that support UTF8 charset.
CREATE DATABASE sanyo
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;

2. build application, run app.
3. create triggers in the following orders:
	- product_triggers.sql
	- encounter_triggers.sql
	- location_triggers.sll
	- triggers.sql
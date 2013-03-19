#--- !Ups
CREATE TABLE Item (
	item_id				varchar(255),
	item_name           varchar(255),
	number_items        int,
	supplier			varchar(255),
	PRIMARY KEY (item_id))
;

CREATE TABLE User_Entity (
	user_id				varchar(255),
	first_name			varchar(255),
	last_name			varchar(255),
	address				varchar(255),
	PRIMARY KEY (user_id))
;

CREATE TABLE Transaction (
	trans_id			int AUTO_INCREMENT,
	user_id 			varchar(255),
	item_id				varchar(255),
	noPurch				int,
	trans_date			DATE,
	PRIMARY KEY (trans_id))
;

#--- !Downs
DROP TABLE Item;
DROP TABLE User_Entity;
DROP TABLE Transaction;
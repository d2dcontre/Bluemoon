#--- !Ups
INSERT INTO Item(item_id,item_name,number_items,supplier) values ('11111', 'Wheels', 100, 'Sir Alampay');
INSERT INTO Item(item_id,item_name,number_items,supplier) values ('22222', 'Engine', 200, 'Sir Boaz');
INSERT INTO Item(item_id,item_name,number_items,supplier) values ('33333', 'Steering Wheel', 300, 'Sir Santos');
INSERT INTO Item(item_id,item_name,number_items,supplier) values ('44444', 'Car Seat', 400, 'Sir Jongko');
INSERT INTO Item(item_id,item_name,number_items,supplier) values ('55555', 'Pedals', 500, 'Sir Wil');
INSERT INTO Item(item_id,item_name,number_items,supplier) values ('66666', 'Brakes', 600, 'Maam Jess');
INSERT INTO Item(item_id,item_name,number_items,supplier) values ('77777', 'Radio', 700, 'Doc Jon');
INSERT INTO Item(item_id,item_name,number_items,supplier) values ('88888', 'Bolts', 800, 'Sir Ybanez');

INSERT INTO User_Entity(user_id,first_name,last_name,address) values ('103168', 'Spongebob', 'Squarepants', 'Somewhere far far away');
INSERT INTO User_Entity(user_id,first_name,last_name,address) values ('109090', 'Justin', 'Bieber', 'Over the rainbow');
INSERT INTO User_Entity(user_id,first_name,last_name,address) values ('000001', 'Kevin', 'Bacon', 'Hollywood');
INSERT INTO User_Entity(user_id,first_name,last_name,address) values ('000002', 'Steve', 'McGarrett', 'Hawaii');
INSERT INTO User_Entity(user_id,first_name,last_name,address) values ('000003', 'Spencer', 'Reid', 'Las Vegas');

INSERT INTO Transaction(user_id,item_id,noPurch,trans_date) values ('103168','11111',200,'2013-03-18');
INSERT INTO Transaction(user_id,item_id,noPurch,trans_date) values ('109090','22222',200,'2013-03-18');
INSERT INTO Transaction(user_id,item_id,noPurch,trans_date) values ('000001','33333',200,'2013-03-18');

#--- !Downs
DELETE FROM Item;
DELETE FROM User_Entity;


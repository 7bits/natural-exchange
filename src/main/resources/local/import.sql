SET NAMES  'utf8';
INSERT INTO searchVariant(id,email,created_date,key_words,categories) VALUES (1,'dimaaasik.s@gmail.com',1369229028460,'man coat','games clothes');
INSERT INTO searchVariant(id,email,created_date,key_words,categories) VALUES (2,'test@gmail.com',1369229024460,'coat','games clothes');
INSERT INTO searchVariant(id,email,created_date,key_words,categories) VALUES (3,'test@gmail.com',1369229028660,'boots','games clothes');
INSERT INTO searchVariant(id,email,created_date,key_words,categories) VALUES (4,'bandit@gmail.com',1369229022460,'skate','games clothes');
INSERT INTO searchVariant(id,email,created_date,key_words,categories) VALUES (5,'ashaeva73@mail.ru',1369229027460,'bicycle','games clothes notclothes');
INSERT INTO Category (id,name,description,created_date,is_deleted,updated_date) VALUES (1,'clothes','very good',460,1,461);
INSERT INTO Category (id,name,description,created_date,is_deleted,updated_date) VALUES (2,'notclothes','very good',460,1,461);
INSERT INTO Category (id,name,description,created_date,is_deleted,updated_date) VALUES (3,'games','very good',460,1,461);
INSERT INTO user (id,first_name,last_name,created_date,is_deleted,updated_date,vk_link,email, password, role,activation_date, avatar) VALUES (1,'Dmitry','Silerd',460,0,461,'vk.com/322233','test@gmail.com', md5('123'),'ROLE_USER',0,'avatar111.jpg');
INSERT INTO user (id,first_name,last_name,created_date,is_deleted,updated_date,vk_link,email, password, role,activation_date, avatar) VALUES (2,'Sergey','Konskih',460,0,461,'vk.com/325656','bandit@gmail.com', md5('111'),'ROLE_USER',0,NULL);
INSERT INTO user (id,first_name,last_name,created_date,is_deleted,updated_date,vk_link,email, password, role,activation_date, avatar) VALUES (3,'Ivan','Vovan',460,0,461,'vk.com/322237','mostwanted@mail.ru', md5('100'),'ROLE_ADMIN',0,NULL);
INSERT INTO user (id,first_name,last_name,created_date,is_deleted,updated_date,vk_link,email, password, role,activation_date, avatar) VALUES (5,'Alex','Nikitin',460,0,461,'19460382','nikitinlexey@gmail.com', md5('111'),'ROLE_MODERATOR',0,NULL);
INSERT INTO user (id,first_name,last_name,created_date,is_deleted,updated_date,vk_link,email, password, role,activation_date, avatar) VALUES (4, 'Eugene','Zakharov',460,0,461,'vk.com/rocketjump','rocketjump07@gmail.com', md5('111'),'ROLE_MODERATOR',0,NULL);
INSERT INTO user (id,first_name,last_name,created_date,is_deleted,updated_date,vk_link,email, password, role,activation_date, avatar) VALUES (6,'Julia','Ashaeva',460,0,461,'vk.com/32','ashaeva73@mail.ru', md5('123'),'ROLE_USER',0,'avatar068.gif');
INSERT INTO advertisement (id,created_date,is_deleted,is_new,photo_file,text,title,updated_date,category_id,user_id) VALUES (1,1369229028460,1,0,'image1.jpg','this is coat','man coat',15000,1,1);
INSERT INTO advertisement (id,created_date,is_deleted,is_new,photo_file,text,title,updated_date,category_id,user_id) VALUES (2,1369232628460,0,0,'image2.jpg','this is coat2','boots',25000,1,2);
INSERT INTO advertisement (id,created_date,is_deleted,is_new,photo_file,text,title,updated_date,category_id,user_id) VALUES (3,1369239828460,1,0,'image3.jpg','this is coat3','bicycle',35000,2,1);
INSERT INTO advertisement (id,created_date,is_deleted,is_new,photo_file,text,title,updated_date,category_id,user_id) VALUES (12,1369239828460,0,1,'image3.jpg','very the best improved BMX','bicycle',35000,2,2);
INSERT INTO advertisement (id,created_date,is_deleted,is_new,photo_file,text,title,updated_date,category_id,user_id) VALUES (4,1369249028460,0,0,'image1.jpg','this is another coat','blue coat',45000,1,1);
INSERT INTO advertisement (id,created_date,is_deleted,is_new,photo_file,text,title,updated_date,category_id,user_id) VALUES (5,1369242628460,0,0,'image2.jpg','this is shoes','white coat',55000,1,1);
INSERT INTO advertisement (id,created_date,is_deleted,is_new,photo_file,text,title,updated_date,category_id,user_id) VALUES (6,1369249828460,0,0,'image3.jpg','this is cat','skate',65000,2,1);
INSERT INTO advertisement (id,created_date,is_deleted,is_new,photo_file,text,title,updated_date,category_id,user_id) VALUES (7,1369259028460,0,0,'image1.jpg','this is dog','skate',75000,2,1);
INSERT INTO advertisement (id,created_date,is_deleted,is_new,photo_file,text,title,updated_date,category_id,user_id) VALUES (8,1369252628460,0,0,'image2.jpg','this is toys','skate',85000,2,1);
INSERT INTO advertisement (id,created_date,is_deleted,is_new,photo_file,text,title,updated_date,category_id,user_id) VALUES (9,1369259828460,0,0,'image3.jpg','this is something','skate',95000,2,1);
INSERT INTO advertisement (id,created_date,is_deleted,is_new,photo_file,text,title,updated_date,category_id,user_id) VALUES (10,1369259828465,0,0,'no_photo.png','настольная игра','Ужас Аркхэма',95000,3,1);
INSERT INTO advertisement (id,created_date,is_deleted,is_new,photo_file,text,title,updated_date,category_id,user_id) VALUES (11,1369259828466,0,0,'no_photo.png','настольная игра','Каркассон',95000,3,1);
INSERT INTO advertisement (id,created_date,is_deleted,is_new,photo_file,text,title,updated_date,category_id,user_id) VALUES (13,1369235828460,0,0,'image3.jpg','this is coat3','bicycle',35000,2,1);
INSERT INTO advertisement (id,created_date,is_deleted,is_new,photo_file,text,title,updated_date,category_id,user_id) VALUES (14,1369237828460,0,0,'image3.jpg','very the best improved BMX','bicycle',35000,2,2);

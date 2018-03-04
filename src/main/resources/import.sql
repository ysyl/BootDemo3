INSERT INTO user (id, username, password, name, age) VALUES (1, 'zhou', '123456', '周建龙', 30);
INSERT INTO user (id, username, password, name, age)  VALUES (2, 'admin', '123456', 'jay', 29);

INSERT INTO user (id, username, password, name, age)  VALUES (3, 'admin1', '123456', 'jay1', 29);

INSERT INTO authority (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO authority (id, name) VALUES (2, 'ROLE_ADMIN');

INSERT INTO user_authority (user_id, authority_id) VALUES (1, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 2);

INSERT INTO user_authority (user_id, authority_id) VALUES (3, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (3, 2);

INSERT INTO article (content,creation_date, last_update,description, user_id) VALUES ("article content",'20151208000000','20151208000000', "fjskdlfs", 1);
INSERT INTO article (content,creation_date, last_update,description, user_id) VALUES ("article content",'20151208000000','20151208000000', "fjskdlfs", 2);
INSERT INTO article (content,creation_date, last_update,description, user_id) VALUES ("article content",'20151208000000','20151208000000', "fjskdlfs", 2);
INSERT INTO article (content,creation_date, last_update,description, user_id) VALUES ("article content",'20151208000000','20151208000000', "fjskdlfs", 1);
INSERT INTO article (content,creation_date, last_update,description, user_id) VALUES ("article content",'20151208000000','20151208000000', "fjskdlfs", 1);
INSERT INTO article (content,creation_date, last_update,description, user_id) VALUES ("article content",'20151208000000','20151208000000', "fjskdlfs", 1);
INSERT INTO article (content,creation_date, last_update,description, user_id) VALUES ("article content",'20151208000000','20151208000000', "fjskdlfs", 1);
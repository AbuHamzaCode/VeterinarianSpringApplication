-- when run application first time created admin
-- read more in application.properties

INSERT INTO roles (name) VALUES ("ROLE_ADMIN");
INSERT INTO users (username, password, full_name, address, phone, email)
VALUES ("admin", "$2a$10$ofGs5sNE9q05NRvgXlax/eCsIGq97/bLXedkvVqZig5qkwbXQlFOG", --Decoding for admin(Islam1995)
"Islam Ataballyyev", "Istanbul, Gaziosmanpasha, Sarigol mahalle",
"+905454919594", "labeloneside@gmail.com");
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
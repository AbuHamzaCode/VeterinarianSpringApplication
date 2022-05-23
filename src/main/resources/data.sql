-- when run application first time created admin
-- read more in application.properties

INSERT INTO roles (name) VALUES ("ROLE_ADMIN");
INSERT INTO users (username, password, full_name, address, phone, email)
VALUES ("admin", "$2a$10$rKn/Nn96ZTIBvacvjReV5.yMXPwH2GKrKf2pCmz9mYd.eDHWZ31dm", --Decoding for admin(adminadmin)
"Islam Ataballyyev", "Istanbul, Gaziosmanpasha, Sarigol mahallesi",
"+905454919594", "labeloneside@gmail.com");
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
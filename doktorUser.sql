BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "doktorUser" (
	"id"	INTEGER,
	"first_name"	TEXT,
	"last_name"	TEXT,
	"user_name"	TEXT,
	"password"	TEXT,
	"e_mail"	TEXT,
	PRIMARY KEY("id")
);

INSERT INTO `doktorUser` VALUES (1,'Ilhan','Licina','ilicina1','Manijaci1921','ilhan.licina@outlook.com');
INSERT INTO `doktorUser` VALUES (2,'Hilal','Piciguz','hlicina2','Manijaci1921','hilal.licina@outlook.com');

COMMIT;

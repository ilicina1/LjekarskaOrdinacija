BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Doktori" (
	"first_name"	TEXT,
	"last_name"	TEXT,
	"user_name"	TEXT,
	"password"	TEXT,
	"e_mail"	INTEGER,
	PRIMARY KEY("first_name")
);
INSERT INTO "Doktori" VALUES ('Ilhan','Licina','ilicina1','Manijaci1921','ilicina1@outlook.com');
COMMIT;

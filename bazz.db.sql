BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Dijagnoze" (
	"id"	INTEGER,
	"text"	TEXT,
	"pacijent"	INTEGER,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "Doktori" (
	"first_name"	TEXT,
	"last_name"	TEXT,
	"user_name"	TEXT,
	"password"	TEXT,
	"e_mail"	TEXT,
	PRIMARY KEY("first_name")
);
CREATE TABLE IF NOT EXISTS "Pacijenti" (
	"medical_record_number"	INTEGER,
	"full_name"	TEXT,
	"phone_number"	TEXT,
	"city"	TEXT,
	"address"	TEXT,
	"birth_date"	TEXT
);
INSERT INTO "Dijagnoze" VALUES (1,'Poremecaj posteljice',123);
INSERT INTO "Doktori" VALUES ('Ilhan','Licina','ilicina1','Manijaci1921','ilicina1@outlook.com');
INSERT INTO "Doktori" VALUES ('Marin','Marinac','mmarinac1','Marinac123','mmarinac1@gmail.com');
INSERT INTO "Pacijenti" VALUES (123,'Manga Mangafic','062322477','Sarajevo','Aleja Pamuka 21','12-06-99');
INSERT INTO "Pacijenti" VALUES (43,'Pepa Pig','033156877','Sarajevo','Drinska 50c','22-06-87');
COMMIT;

BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Rezultati" (
	"id"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"sample"	TEXT,
	"type_of_analysis"	TEXT,
	"result"	REAL,
	"normal_value"	TEXT,
	"report"	INTEGER
);
CREATE TABLE IF NOT EXISTS "Nalazi" (
	"id"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"date"	TEXT,
	"pacijent"	INTEGER,
	FOREIGN KEY("pacijent") REFERENCES "Pacijenti"("medical_record_number")
);
CREATE TABLE IF NOT EXISTS "Historija" (
	"id"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"allergies"	TEXT,
	"family_medical_issues"	TEXT,
	"addictions"	TEXT,
	"current_health_issues"	TEXT,
	"pacijent"	INTEGER,
	FOREIGN KEY("pacijent") REFERENCES "Pacijenti"("medical_record_number")
);
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
INSERT INTO "Historija" VALUES (1,'Cafetin','Heart attacks','Nicotine','Asthma',123);
INSERT INTO "Historija" VALUES (2,'Dexomen','Heart attack','No','No',123);
INSERT INTO "Dijagnoze" VALUES (1,'Poremecaj posteljice',123);
INSERT INTO "Dijagnoze" VALUES (2,'Poremecaj glave',123);
INSERT INTO "Dijagnoze" VALUES (4,'Krvarenje prije porođaja',123);
INSERT INTO "Dijagnoze" VALUES (12,'Svinjetina poremecan',43);
INSERT INTO "Dijagnoze" VALUES (13,'Upala gacetina',43);
INSERT INTO "Doktori" VALUES ('Ilhan','Licina','ilicina1','Manijaci1921','ilicina1@outlook.com');
INSERT INTO "Doktori" VALUES ('Marin','Marinac','mmarinac1','Marinac123','mmarinac1@gmail.com');
INSERT INTO "Pacijenti" VALUES (123,'Manga Mangafic','062322477','Sarajevo','Aleja Pamuka 21','1999-05-12');
INSERT INTO "Pacijenti" VALUES (43,'Pepa Pig','033156877','Sarajevo','Drinska 50c','1988-06-21');
COMMIT;

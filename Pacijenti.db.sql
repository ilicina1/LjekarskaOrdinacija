BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Pacijenti" (
	"medical_record_number"	INTEGER,
	"full_name"	TEXT,
	"phone_number"	TEXT,
	"city"	TEXT,
	"address"	TEXT,
	"birth_date"	TEXT
);
COMMIT;

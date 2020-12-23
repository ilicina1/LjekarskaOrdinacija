# Opis aplikacije
* Aplikacija koja je razvijena je softver za ljekarsku ordinaciju.
* Ovaj softver je efikasan program za sve vrste ljekarskih ordinacija koje imaju potrebu da vode evidenciju o svojim pacijentima, njihovim dijagnozama, rendgen snimcima, historiji bolesti, voditi racuna o najavljenim pacijentima itd.
* Doktor se najprije registruje klikom na signUp button i tu smjesta svoje podatke.
* Nakon sto se registrovao vracen je na Login formu gdje se moze ulogovat podacima kojima se registrovao.
* Nakon toga otvara se novi prozor u kojem mozemo vidjet tabelu koja predstavlja listu pacijenata koji su upisani u toj privatnoj ljekarskor ordinaciji(svaki doktor vidi sve pacijente).
* Sa njima moze raditi neke od operacija kao sto su brisanje ili dodavanje novog paci- jenta ili mijenjanje podataka vec postojeceg.
* Takođe se svi podaci o svakom pacijentu i povezani podaci kao sto su historija bolesti, rendgen snimci itd exportovati u xml fajlove.
* Nakon toga s lijeve strane se nalaze najave gdje doktor moze pogledati najavljene pacijente, najaviti nove ili obrisati neke ako je neki od njih otkazao dolazak.
* Zatim moguce je da za svakog pacijenta pogleda redgen snimke, historiju bolesti, dijagnoze, nalaze i iste po potrebi mijenjati dodavati ili brisati.
* Na kraju kolone vidimo searchBox pomocu kojeg doktor moze pronaci odgovarajuceg pacijenta po imenu i prezimenu. 
* Takodje doktor posjeduje chat sa salterom.
# Osnovne ideje pri implementaciji
* Pri implementaciji koristena je SQLite baza podataka jer je zadovoljavajuća za projekat ovih razmjera. 
* Osnovna ideja bila je da se prije svega razviju neke osnovne funkcionalnosti za rad sa bazom podataka. 
* U klasi ClassDAO nalaze se sve metode i preparedstatementsi i nude ih aplikaciji na koristenje. 
* Izgled aplikacije je jako elegantan i konzistentan kroz cijelu aplikaciju iako je prioritet demostracija funkcionalnosti. 
* Takodje je napravljen chat koji je implementiran odvojeno od ove aplikacije. 
* Pokrece se tako sto se prvo ”serverSide” projekat pokrene (nalazi se odvojeno na repozitoriju), a zatim se pokrenu dvije instance ”klijentSide” (nalazi se odvojeno na repozitoriju) projekta i onda se kroz gui klijentNovog mogu dopi- sivati.

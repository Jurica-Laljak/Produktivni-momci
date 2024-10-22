# Programsko inženjerstvo
Projekt je napravljen u sklopu predmeta Programsko inženjerstvo [Fakulteta elektrotehnike i računarstva](https://www.fer.unizg.hr/). 

# Opis projekta
U današnje doba, velika količina ljudi pohađa koncerte. Na primjer, Ultra Europe festival elektroničke glazbe 2024. godine posjetilo je [preko 140,000 posjetitelja](https://www.festground.com/events/ultra-europe-2024/312). I mnogi manji događaji mogu privući velike publike, kao na primjer [“Balkan festival”](https://www.vecernji.hr/showbiz/balkanske-trap-zvijezde-odrzale-koncert-u-areni-zagreb-desingerica-se-polugol-bacio-u-publiku-1768616). 

Gledano iz druge perspektive, naši životi su svake godine sve kaotičniji. U [članku za ZME Science](https://www.zmescience.com/feature-post/pieces/theres-way-more-chaos-in-our-lives-than-you-think-this-scientist-says-its-empowering/) dr. Brian Klass govori: “There’s this bizarre aspect where we feel more in control of our daily lives than has ever been true in human history, at the same time that the overall structure of the world is changing at an unprecedented pace”. Stoga, velika većina ljudi traži fleksibilnost i prilagodljivost u uslugama koje koriste.

U ovakvom okruženju, smisleno bi bilo implementirati sustav koji velikom broju posjetitelja glazbenih festivala smanjuje rizik od kupnje ulaznica za festival. Predstavljamo {ime aplikacije}, aplikacija koja nastoji smanjiti rizik kod kupnje ulaznicama, te koja svojim korisnicima daje veću fleksibilnost u odabiru točno one ulaznice koju žele. 

# Funkcijski zahtjevi
* Grubo se dijele na dvije skupine.
**Zahtjevi korisnika** - oni zahtjevi koji se odražavaju na korisničko iskustvo
* Sustav mora neregistriranim korisnicima omogućiti pregled popisa događaja i oglasa za ulaznice.
Sustav mora omogućiti korisnicima da se registriraju. 
Registrirani korisnici moraju moći objavljivati oglase, odgovarati na svoje i tuđe oglase.
Registrirani korisnik svoje oglase može proizvoljan broj puta ažurirati, te ih po volji izbrisati.
Korisnik može postaviti oglas za zamjenu ili za prodaju ulaznica.
Korisnici mora moći kroz svoj profil definirati tipove događaja koji su mu zanimljivi (“preferencije”). 
Korisnik može promijeniti svoj popis preferencija.
Korisnici mogu koristiti filtere kako bi lakše pronašli željenu ulaznicu. 
Korisnici mogu označiti da im se oglas sviđa. U bilo kojem trenutku, korisnik mora moći ukloniti oglas sa popisa oglasa koji im se sviđaju.
Korisnici mogu zanemariti oglase. U tom slučaju, taj oglas im se više neće prikazivati.
Korisnici mogu zanemariti događaje. U tom slučaj, svi oglasi povezani uz taj događaj im se neće prikazivati.
Sustav mora omogućiti korisniku da “prati” određenog izvođača. 
Ako izvođač koji korisnik prati ima nadolazeći koncert, korisniku može dobiti obavijest o tom događaju putem e-pošte ili unutar aplikacije. 
Korisnici moraju moći pogledati tuđe korisničke račune. Tijekom pregleda korisničkog računa, korisniku je omogućeno da pošalje zahtjev za praćenje, koji drugi korisnik mora potvrditi kako bi se umrežili.
Korisnici moraju moći pogledati svoj popis praćenih korisničkih računa, te samostalno ukloniti bilo koji račun s tog popisa.
Korisnici mogu stvoriti grupe, koje mogu sadržavati samo one korisničke račune koje korisnik prati.
Proces zamjene ulaznica može se obavljati između dva korisnika, između više od dva korisnika (“lanac razmjene”), te između dvije grupe korisnika.
U slučaju zamjene ulaznica između više korisnika (“lanac zamjene”), svaki korisnik mora zasebno potvrditi zamjenu.
U slučaju zamjene ulaznica između dvije grupe korisnika, svi članovi obje grupe moraju potvrditi zamjenu.

**Zahtjevi sustava** - oni zahtjevi kojih korisnik nije svjestan
U sklopu procesa registracije korisnika, korisnik mora navesti svoju adresu e-pošte. Na tu adresu će se prilikom završetka registracije poslati poruka u kojoj će se nalaziti link za potvrdu korisničkog računa.
Adresa e-pošte korištene u procesu registracije korisnika mora biti neiskorištena.
Svi oglasi moraju imati datum isteka nakon kojeg se automatski uklanjaju iz sustava. Taj datum isteka mora odgovarati datumu održavanja događaja.
Ako je oglas vrste zamjena ulaznica, tada korisnik može navesti željeni događaj ili vrstu ulaznice.
Aplikacija se mora automatski povezati na javnu uslugu s katalogom izvođača kako bi se podaci o izvođaču mogli automatski izvući. Podaci o izvođaču uključuju naziv izvođača, žanr glazbe, fotografiju izvođača.
U slučaju zamjene ulaznica između dva korisnika, sustav obama korisnicima šalje obavijest. Nakon što oba korisnika potvrde zamjenu, ona će se provesti u sustavu.
Sustav mora moći identificirati “lanac zamjene” između više korisnika.
Zamjena ulaznica između više grupa korisnika može se ostvariti samo ako su grupe jednakih veličina.
Temeljem podataka o mjestu i datumu održavanja događaja, sustav mora korisniku predstaviti vremensku prognozu. 
U sustavu postoji uloga administratora, koja se ne može postići registriranjem i koja ima više dopuštenja.
Sustav mora pratiti podatke o razmjenama ulaznica, tj. prikazati transakcije korisnika.
Administrator temeljem prikupljenih podataka mora moći upravljati korisničkim računima, rješavati sporove između korisnika, te generirati izvještaje o aktivnostima korisnika.

# Nefunkcijski zahtjevi
# Tehnologije
# Projektni tim
Projektni tim TG06.2 ("Produktivni momci") sastoji se od:
* [Roko Barišić](roko.barisic@fer.unizg.hr)
* [Dino Hodžić Tumpić](dino.hodzic-tumpic@fer.unizg.hr)
* [Jurica Laljak](jurica.laljak@fer.unizg.hr)
* [Ivan Kanjuh](ivan.kanjuh@fer.unizg.hr)
* [Andrija Maček](andrija.macek@fer.unizg.hr)
* [Filip Pogač](filip.pogac@fer.unizg.hr)
* [Ivan Stipic](ivan.stipic@fer.unizg.hr)
# Licenca
Repozitorij je licenciran prema [MIT licenci](https://opensource.org/license/mit). Licenca dozvoljava neograničeno korištenje, preuzimanje, uređivanje, publiciranje i
prodaju bilo koje datoteke koja se nalazi u repozitoriju. 
# **Kodeks ponašanja (možda zaseban file)
# **Kontribucije (možda zaseban file)

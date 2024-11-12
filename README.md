# Programsko inženjerstvo

Projekt je napravljen u sklopu predmeta Programsko inženjerstvo [Fakulteta elektrotehnike i računarstva](https://www.fer.unizg.hr/). 

# Opis projekta

U današnje doba, velika količina ljudi pohađa koncerte. Na primjer, Ultra Europe festival elektroničke glazbe 2024. godine posjetilo je [preko 140,000 posjetitelja](https://www.festground.com/events/ultra-europe-2024/312). I mnogi manji događaji mogu privući velike publike, kao na primjer [“Balkan festival”](https://www.vecernji.hr/showbiz/balkanske-trap-zvijezde-odrzale-koncert-u-areni-zagreb-desingerica-se-polugol-bacio-u-publiku-1768616). 

Gledano iz druge perspektive, naši životi su svake godine sve kaotičniji. U [članku za ZME Science](https://www.zmescience.com/feature-post/pieces/theres-way-more-chaos-in-our-lives-than-you-think-this-scientist-says-its-empowering/) dr. Brian Klass govori: “There’s this bizarre aspect where we feel more in control of our daily lives than has ever been true in human history, at the same time that the overall structure of the world is changing at an unprecedented pace”. Stoga, velika većina ljudi traži fleksibilnost i prilagodljivost u uslugama koje koriste.

U ovakvom okruženju, smisleno bi bilo implementirati sustav koji velikom broju posjetitelja glazbenih festivala smanjuje rizik od kupnje ulaznica za festival. Predstavljamo Ticket4Ticket, aplikaciju koja nastoji smanjiti rizik kod kupnje ulaznicama, te koja svojim korisnicima daje veću fleksibilnost u odabiru točno one ulaznice koju žele. Aplikacija omogućuje korisnicima da jednostavno kreiraju svoje i pregledavaju tuđe oglase za prodaju ili zamjenu karata. Osim funkcionalnosti prodaje i zamjene, aplikacija nudi i ograničeno omrežavanje korisnika, kreiranje grupa korisnika, te praćenje omiljenih žanrova i izvođača. Aplikacija je u trenutnoj inačici namijenjena isključivo za stolna računala.

U procesu izrade aplikacije glavni prioriteti bili su strukturiran pristup problemu, te kontinuirana komunikacija ne samo unutar projektnog tima, nego i sa svim dionicima projekta.

# Funkcijski zahtjevi

* Sustav mora neregistriranim korisnicima omogućiti pregled popisa događaja i oglasa za ulaznice.
* Sustav mora omogućiti korisnicima da se registriraju.
* Registrirani korisnici moraju moći objavljivati oglase, odgovarati na svoje i tuđe oglase.
* Registrirani korisnik svoje oglase može proizvoljan broj puta ažurirati, te ih po volji izbrisati.
* Za oglas vrste zamjena ulaznica, korisnik mora moći navesti željeni događaj i vrstu ulaznice.
* Svi oglasi moraju imati datum isteka nakon kojeg se automatski uklanjaju iz sustava, koji odgovara datumu održavanja događaja
* Korisnik mora moći pregledati svoj korisnički profil, te na njemu podesiti svoje podatke.
* Korisnik mora moći kroz svoj profil definirati tipove događaja koji su mu zanimljivi.
* Sustav mora omogućiti korisniku da “prati” određenog izvođača. 
* Sustav mora omogućiti korisniku da "prati" određenog drugog korisnika.
* Korisnik mora moći promijeniti popis zanimljivih događaja i praćenih izvođača i korisnika.
* Ako je nadolezeći događaj koji korisnik prati pripada žanru glazbe koji je korisniku zanimljiv, korisnik može dobiti obavijest o tome putem e-pošte ili unutar aplikacije
* Ako izvođač koji korisnik prati ima nadolazeći koncert, korisniku može dobiti obavijest o tom događaju putem e-pošte ili unutar aplikacije.
* Korisnici mogu stvoriti grupe, koje mogu sadržavati samo one korisničke račune koje korisnik prati.
* U procesu traženja ulaznice, korisnici mogu koristiti filtere kako bi lakše pronašli željenu ulaznicu
* Korisnici mogu označiti da im se oglas sviđa. U bilo kojem trenutku, korisnik mora moći ukloniti oglas sa popisa oglasa koji im se sviđaju.
* Korisnici mogu zanemariti oglase. U tom slučaju, taj oglas im se više neće prikazivati.
* Korisnici mogu zanemariti događaje. U tom slučaj, svi oglasi povezani uz taj događaj im se neće prikazivati.
* Proces zamjene ulaznica može se obavljati između dva korisnika, između više od dva korisnika (“lanac razmjene”), te između dvije grupe korisnika.
* Sustav mora omogućiti korisniku pregled lanaca razmjene za danu ulaznicu.
* Zamjena ulaznica između grupa korisnika može se obaviti samo u slučaju kada su obje grupe korisnika jednake veličine.
* Nakon što su zadovoljeni uvijeti za transakciju ulaznica, sustav mora svim relevantnim korisnicima poslati poruku potvrde transakcije na adresu e-pošte.
* U sustavu postoji uloga administratora, koja se ne može postići registriranjem i koja ima više dopuštenja.
* Administrator temeljem prikupljenih podataka mora moći upravljati korisničkim računima, rješavati sporove između korisnika, te generirati izvještaje o aktivnostima korisnika.

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

> Repozitorij je licenciran prema [MIT licenci](https://opensource.org/license/mit). Licenca dozvoljava neograničeno korištenje, preuzimanje, uređivanje, publiciranje i
prodaju bilo koje datoteke koja se nalazi u repozitoriju.
> 
# Kodeks ponašanja



# Kontribucije



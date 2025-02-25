# Ticket4Ticket 

Trenutnoj inačici aplikacije može se pristupiti preko https://ticket4ticket.onrender.com/.

# Opis projekta

U današnje doba, velika količina ljudi pohađa koncerte. Na primjer, Ultra Europe festival elektroničke glazbe 2024. godine posjetilo je [preko 140,000 posjetitelja](https://www.festground.com/events/ultra-europe-2024/312). I mnogi manji događaji mogu privući velike publike, kao na primjer [“Balkan festival”](https://www.vecernji.hr/showbiz/balkanske-trap-zvijezde-odrzale-koncert-u-areni-zagreb-desingerica-se-polugol-bacio-u-publiku-1768616). 

Gledano iz druge perspektive, naši životi su svake godine sve kaotičniji. U [članku za ZME Science](https://www.zmescience.com/feature-post/pieces/theres-way-more-chaos-in-our-lives-than-you-think-this-scientist-says-its-empowering/) dr. Brian Klass govori: “There’s this bizarre aspect where we feel more in control of our daily lives than has ever been true in human history, at the same time that the overall structure of the world is changing at an unprecedented pace”. Stoga, velika većina ljudi traži fleksibilnost i prilagodljivost u uslugama koje koriste.

U ovakvom okruženju, smisleno bi bilo implementirati sustav koji velikom broju posjetitelja glazbenih festivala smanjuje rizik od kupnje ulaznica za festival. Predstavljamo Ticket4Ticket, aplikaciju koja nastoji smanjiti rizik kod kupnje ulaznicama, te koja svojim korisnicima daje veću fleksibilnost u odabiru točno one ulaznice koju žele. Aplikacija omogućuje korisnicima da jednostavno kreiraju svoje i pregledavaju tuđe oglase za prodaju ili zamjenu ulaznica. Osim funkcionalnosti prodaje i zamjene, aplikacija nudi i ograničeno omrežavanje korisnika, kreiranje grupa korisnika, te obaviještavanje o najnovijim glazbenim događajima. 

Ovaj projekt je napravljen u sklopu predmeta *Programsko inženjerstvo* [Fakulteta elektrotehnike i računarstva](https://www.fer.unizg.hr/). Na predmetu se osim učenja najpovoljnijih tehnologija i radni okvira za izradu aplikacija, uči o cjelokupnom procesu programskog inženjerstva. U taj proces spadaju izlučivanje zahtjeva iz dionika, kreiranje dokumentacije u obliku [UML dijagrama](https://www.uml-diagrams.org/uml-25-diagrams.html), kreiranje implementacijskog dijagrama razreda i sheme baze podataka, sâma implementacija programskog rješenja, te konačno puštanje programskog sustava u pogon. U sklopu projekta svaki član tima naučio je ponešto o svakom aspektu razvoja programske podrške, a najviše o važnosti kontinuirane i konkretne komunikacije, ne samo unutar projektnog tima, nego i sa svim dionicima projekta. 

Napomena: Aplikacija je u trenutnoj inačici namijenjena isključivo za stolna računala.

# Funkcijski zahtjevi

* Sustav mora neregistriranim korisnicima omogućiti pregled oglasa.
* Sustav mora omogućiti korisnicima da se registriraju.
* Registrirani korisnici moraju moći objavljivati oglase, te odgovarati na tuđe.
* Registrirani korisnik svoje oglase može proizvoljan broj puta ažurirati, te ih po volji izbrisati.
* Svi oglasi moraju imati datum isteka nakon kojeg se automatski uklanjaju iz sustava, koji odgovara datumu održavanja događaja.
* Korisnik mora moći pregledati svoj korisnički profil, te na njemu podesiti svoje podatke.
* Korisnik mora moći kroz svoj profil definirati žanrove glazbe koji su mu zanimljivi.
* Sustav mora omogućiti korisniku da "prati" bilo kojeg drugog korisnika.
* Ako novokreirani oglas pripada žanru glazbe koji je korisniku zanimljiv, korisnik mora moći dobiti obavijest o tom oglasu putem e-pošte ili unutar aplikacije.
* Korisnici mogu stvoriti grupe, koje mogu sadržavati samo one korisničke račune koje korisnik prati.
* U procesu traženja ulaznice, korisnici mogu koristiti filtere kako bi lakše pronašli željenu ulaznicu.
* Korisnici mogu označiti da im se oglas sviđa. U bilo kojem trenutku, korisnik mora moći ukloniti oglas sa popisa oglasa koji im se sviđaju.
* Korisnici mogu zanemariti oglase. U tom slučaju, taj oglas im se više neće prikazivati.
* Proces zamjene ulaznica može se obavljati između dva korisnika, između više od dva korisnika (“lanac razmjene”), te između dvije grupe korisnika.
* Sustav mora omogućiti korisniku pregled lanaca razmjene za danu ulaznicu.
* Zamjena ulaznica između grupa korisnika može se obaviti samo u slučaju kada su obje grupe korisnika jednake veličine.
* Nakon što su zadovoljeni uvijeti za transakciju ulaznica, sustav mora svim relevantnim korisnicima poslati poruku potvrde transakcije na adresu e-pošte.
* U sustavu postoji uloga administratora, koja se ne može postići registriranjem i koja ima više dopuštenja.
* Administrator temeljem prikupljenih podataka mora moći upravljati korisničkim računima, te generirati izvještaje o aktivnostima korisnika.

# Nefunkcijski zahtjevi

* Sustav mora koristiti OAuth 2.0 protokol za autentikaciju korisnika.
* Sustav mora biti parametriziran.
* Aplikacija se mora automatski povezati na javnu uslugu s katalogom izvođača kako bi se podaci o izvođaču mogli automatski izvući.
* Sustav se mora automatski povezati na uslugu za vremensku prognozu. Podaci o vremenskoj prognozi prezentirat će se korisniku samo ako je događaj unutar 15 dana od trenutnog datuma.
* Korisnička stranica mora biti prilagođena za razne veličine ekrana (responzivna).
* Sustav mora prikupljati podatke o ponašanjima i transakcijama korisnika.
* Registracija korisnika mora se ostvariti nakon prve autentikacije korisnika putem OAuth 2.0 protokola.
* Sustav mora biti ostvaren koristeći tehnologije Spring i JPA za poslužiteljsku stranu, te React za korisničku stranu.
* Sustav mora biti otporan na interne pogreške, te vanjske smetnje i napade.
* Učitavanje bilo koje stranice ne smije trajati više od 7 sekundi.

# Tehnologije

* [Astah UML](https://astah.net/products/astah-uml/) - crtanje UML dijagrama
* [React](https://react.dev/learn) - tehnologija za razvoj klijentske strane
* [Spring Boot](https://spring.io/projects/spring-boot#learn) - tehnologija za razvoj poslužiteljske strane
* [Spring JPA](https://spring.io/projects/spring-data-jpa#learn) - baze podataka
* [Render](https://render.com/) - puštanje u pogon
* [Google Cloud Console](https://console.cloud.google.com) - implementacija OAuth protokola

# Projektni tim

| **Član tima** | **Uloge** | **Poveznica na GitHub** | **Službena mail adresa** |
|---|---|---|---|
| Roko Barišić | Razvoj baze podataka | https://github.com/rokobarisic | roko.barisic@fer.unizg.hr |
| Dino Hodžić Tumpić | Razvoj klijentske strane | https://github.com/Dino-HodzicTumpic | dino.hodzic-tumpic@fer.unizg.hr |
| Ivan Kanjuh | Razvoj poslužiteljske strane | https://github.com/IvanKanjuh | ivan.kanjuh@fer.unizg.hr |
| Jurica Laljak | Voditelj tima, razvoj poslužiteljske strane | https://github.com/Jurica-Laljak | jurica.laljak@fer.unizg.hr |
| Andrija Maček | Razvoj poslužiteljske strane, puštanje u pogon | https://github.com/Andrija44 | andrija.macek@fer.unizg.hr |
| Filip Pogač | Razvoj klijentske strane | https://github.com/Filip-Pogac | filip.pogac@fer.unizg.hr |
| Ivan Stipić | Razvoj klijentske strane | https://github.com/Ivan-Stipic | ivan.stipic@fer.unizg.hr |


# Licenca

> Repozitorij je licenciran prema [MIT licenci](https://opensource.org/license/mit). Licenca dozvoljava neograničeno korištenje, preuzimanje, uređivanje, publiciranje i
prodaju bilo koje datoteke koja se nalazi u repozitoriju.


# Kodeks ponašanja

[CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md)

# Kontribucije

[CONTRIBUTING.md](CONTRIBUTING.md)



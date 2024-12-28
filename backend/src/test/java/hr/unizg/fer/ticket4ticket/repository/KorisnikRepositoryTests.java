package hr.unizg.fer.ticket4ticket.repository;

import hr.unizg.fer.ticket4ticket.entity.Korisnik;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class KorisnikRepositoryTests {

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Test
    public void korisnikRepository_save_returnsSavedKorisnik() {

        Korisnik korisnik = Korisnik.builder()
                .imeKorisnika("John")
                .prezimeKorisnika("Doe")
                .emailKorisnika("john.doe@gmail.com")
                .fotoKorisnika("img.jpg")
                .googleId("123456").build();

        Korisnik savedKorisnik = korisnikRepository.save(korisnik);

        Assertions.assertNotNull(savedKorisnik);
        Assertions.assertTrue(savedKorisnik.getIdKorisnika() > 0);
    }

    @Test
    public void korisnikRepository_findAll_returnsListOfKorisnik() {

        Korisnik korisnik1 = Korisnik.builder()
                .imeKorisnika("John")
                .prezimeKorisnika("Doe")
                .emailKorisnika("john.doe@gmail.com")
                .fotoKorisnika("img.jpg")
                .googleId("123456").build();
        Korisnik korisnik2 = Korisnik.builder()
                .imeKorisnika("Ivan")
                .prezimeKorisnika("Horvat")
                .emailKorisnika("ivan.horvat@gmail.com")
                .fotoKorisnika("slika.jpg")
                .googleId("654321").build();

        korisnikRepository.save(korisnik1);
        korisnikRepository.save(korisnik2);

        List<Korisnik> korisnikList = korisnikRepository.findAll();

        Assertions.assertNotNull(korisnikList);
        Assertions.assertEquals(2, korisnikList.size());
    }

    @Test
    public void korisnikRepository_findByGoogleId_returnsKorisnik() {

        Korisnik korisnik = Korisnik.builder()
                .imeKorisnika("John")
                .prezimeKorisnika("Doe")
                .emailKorisnika("john.doe@gmail.com")
                .fotoKorisnika("img.jpg")
                .googleId("123456").build();

        korisnikRepository.save(korisnik);

        Korisnik foundKorisnik = korisnikRepository.findByGoogleId("123456");

        Assertions.assertNotNull(foundKorisnik);
        Assertions.assertEquals(korisnik.getGoogleId(), foundKorisnik.getGoogleId());
        Assertions.assertEquals(korisnik.getEmailKorisnika(), foundKorisnik.getEmailKorisnika());
    }

}

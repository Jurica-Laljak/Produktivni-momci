package hr.unizg.fer.ticket4ticket.repository;

import hr.unizg.fer.ticket4ticket.entity.Izvodac;
import hr.unizg.fer.ticket4ticket.entity.Korisnik;
import hr.unizg.fer.ticket4ticket.entity.Oglas;
import hr.unizg.fer.ticket4ticket.entity.Ulaznica;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class OglasRepositoryTests {

    @Autowired
    private OglasRepository oglasRepository;

    @Autowired
    private UlaznicaRepository ulaznicaRepository;

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private IzvodacRepository izvodacRepository;

    @Test
    public void oglasRepository_findOglasiByFilter_returnsListOfOglasi() {
        Ulaznica ulaznica1 = Ulaznica.builder()
                .datumKoncerta(LocalDate.now())
                .lokacijaKoncerta("Zagreb")
                .odabranaZona("Zona")
                .vrstaUlaznice("VIP")
                .build();
        Ulaznica ulaznica2 = Ulaznica.builder()
                .datumKoncerta(LocalDate.now())
                .lokacijaKoncerta("Samobor")
                .odabranaZona("Zona")
                .vrstaUlaznice("Regular")
                .build();

        ulaznicaRepository.save(ulaznica1);
        ulaznicaRepository.save(ulaznica2);

        Izvodac izvodac1 = Izvodac.builder()
                .imeIzvodaca("Taylor")
                .prezimeIzvodaca("Swift")
                .starostIzvodaca(30)
                .fotoIzvodaca("img.png")
                .ulaznice(Set.of(ulaznica1))
                .build();
        Izvodac izvodac2 = Izvodac.builder()
                .imeIzvodaca("Kendrick")
                .prezimeIzvodaca("Lamar")
                .starostIzvodaca(30)
                .fotoIzvodaca("img.png")
                .ulaznice(Set.of(ulaznica2))
                .build();

        izvodacRepository.save(izvodac1);
        izvodacRepository.save(izvodac2);

        Korisnik korisnik = Korisnik.builder()
                .imeKorisnika("John")
                .prezimeKorisnika("Doe")
                .emailKorisnika("john.doe@gmail.com")
                .fotoKorisnika("img.jpg")
                .googleId("123456").build();

        korisnikRepository.save(korisnik);

        Oglas oglas1 = Oglas.builder().status("status1").ulaznica(ulaznica1)
                .korisnik(korisnik).build();
        Oglas oglas2 = Oglas.builder().status("status2").ulaznica(ulaznica2)
                .korisnik(korisnik).build();

        oglasRepository.save(oglas1);
        oglasRepository.save(oglas2);

        List<Oglas> savedOglasi = oglasRepository.findOglasiByFilter(null, "wif");

        Assertions.assertEquals(1, savedOglasi.size());
        Assertions.assertNotNull(savedOglasi.getFirst());
        Assertions.assertEquals("status1", savedOglasi.getFirst().getStatus());
    }
}

package hr.unizg.fer.ticket4ticket.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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

}

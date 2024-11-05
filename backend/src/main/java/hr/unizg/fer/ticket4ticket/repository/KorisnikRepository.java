package hr.unizg.fer.ticket4ticket.repository;

import hr.unizg.fer.ticket4ticket.entity.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


//We use this to do CRUD operations on Korisnik entity
@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik,Long> {
    // Custom method to find user by Google ID
    Korisnik findByGoogleId(String googleId);

}

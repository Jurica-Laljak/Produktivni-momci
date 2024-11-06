package hr.unizg.fer.ticket4ticket.repository;

import hr.unizg.fer.ticket4ticket.entity.Zanr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZanrRepository extends JpaRepository<Zanr, Long> {
    // Custom method to find Zanr by name (ime_zanra)
    List<Zanr> findByImeZanra(String imeZanra);
}

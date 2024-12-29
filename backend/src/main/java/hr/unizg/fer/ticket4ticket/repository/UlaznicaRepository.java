package hr.unizg.fer.ticket4ticket.repository;

import hr.unizg.fer.ticket4ticket.entity.Ulaznica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UlaznicaRepository extends JpaRepository<Ulaznica,Long> {
    // Add this method to find the Ulaznica by its sifraUlaznice
    Ulaznica findBySifraUlaznice(String sifraUlaznice);
}

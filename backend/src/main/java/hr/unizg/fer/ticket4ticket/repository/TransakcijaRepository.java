package hr.unizg.fer.ticket4ticket.repository;

import hr.unizg.fer.ticket4ticket.entity.Transakcija;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransakcijaRepository extends JpaRepository<Transakcija, Long> {
    List<Transakcija> findByStatusTransakcije(Transakcija.StatusTransakcije statusTransakcije);

    List<Transakcija> findByKorisnikPonuda_IdKorisnikaAndStatusTransakcije(Long korisnikPonudaId, Transakcija.StatusTransakcije statusTransakcije);

    List<Transakcija> findByKorisnikOglas_IdKorisnikaAndStatusTransakcije(Long korisnikPonudaId, Transakcija.StatusTransakcije statusTransakcije);
}
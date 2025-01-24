package hr.unizg.fer.ticket4ticket.repository;

import hr.unizg.fer.ticket4ticket.entity.Obavijest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ObavijestRepository extends JpaRepository<Obavijest,Long> {

    @Query("SELECT o FROM Obavijest o " +
            "JOIN o.oglas u " +
            "WHERE u.idOglasa = :oglasId")
    List<Obavijest> findByOglasId(Long oglasId);

    @Query("SELECT o FROM Obavijest o " +
            "JOIN o.zanr u " +
            "WHERE u.idZanra = :zanrId")
    List<Obavijest> findByZanrId(Long zanrId);

    @Query("SELECT o FROM Obavijest o " +
            "JOIN o.transakcija t " +
            "WHERE t.idTransakcije = :transakcijaId")
    List<Obavijest> findByTransakcijaId(Long transakcijaId);

    List<Obavijest> findByKorisnikId(Long korisnikId);
}

package hr.unizg.fer.ticket4ticket.repository;

import hr.unizg.fer.ticket4ticket.entity.Transakcija;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TransakcijaRepository extends JpaRepository<Transakcija, Long> {
    List<Transakcija> findByStatusTransakcije(Transakcija.StatusTransakcije statusTransakcije);

    List<Transakcija> findByKorisnikPonuda_IdKorisnikaAndStatusTransakcije(Long korisnikPonudaId, Transakcija.StatusTransakcije statusTransakcije);

    List<Transakcija> findByKorisnikOglas_IdKorisnikaAndStatusTransakcije(Long korisnikPonudaId, Transakcija.StatusTransakcije statusTransakcije);

    List<Transakcija> findByKorisnikPonuda_IdKorisnikaOrKorisnikOglas_IdKorisnika(Long korisnikPonudaId, Long korisnikOglasId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Transakcija t WHERE t.oglas.idOglasa = :oglasId")
    void deleteByOglasId(@Param("oglasId") Long oglasId);

    // Custom method to delete all Transakcije where either ulaznicaPonuda or ulaznicaOglas matches the provided id
    void deleteByUlaznicaPonuda_IdUlazniceOrUlaznicaOglas_IdUlaznice(Long ulaznicaPonudaId, Long ulaznicaOglasId);


    @Query("SELECT t FROM Transakcija t " +
            "WHERE (t.ulaznicaPonuda.idUlaznice = :ulaznicaPonudaId OR t.ulaznicaOglas.idUlaznice = :ulaznicaOglasId) " +
            "AND t.statusTransakcije = :statusTransakcije")
    List<Transakcija> findMatchingTransakcije(
            @Param("ulaznicaPonudaId") Long ulaznicaPonudaId,
            @Param("ulaznicaOglasId") Long ulaznicaOglasId,
            @Param("statusTransakcije") Transakcija.StatusTransakcije statusTransakcije);

    @Modifying
    @Transactional
    @Query("UPDATE Transakcija t SET t.oglas = NULL WHERE t.oglas.idOglasa = :oglasId")
    void setOglasToNullByOglasId(@Param("oglasId") Long oglasId);

    // Query to find all Transakcije by oglasId
    List<Transakcija> findByOglas_IdOglasa(Long oglasId);
}
package hr.unizg.fer.ticket4ticket.repository;

import hr.unizg.fer.ticket4ticket.entity.Oglas;
import  hr.unizg.fer.ticket4ticket.entity.Izvodac;
import  hr.unizg.fer.ticket4ticket.entity.Zanr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface OglasRepository extends JpaRepository<Oglas,Long> {


    List<Oglas> findByKorisnik_IdKorisnikaAndStatus(Long korisnikId, Oglas.Status status);

    @Query("SELECT o FROM Oglas o " +
            "JOIN o.korisnik u " +
            "WHERE u.idKorisnika = :korisnikId")
    List<Oglas> findByKorisnikId(Long korisnikId);

    // Finds all Oglas instances that have an associated Izvodac with the given ID
    @Query("SELECT o FROM Oglas o " +
            "JOIN o.ulaznica u " +
            "JOIN u.izvodaci i " +
            "WHERE i.idIzvodaca = :izvodacId")
    Set<Oglas> findByIzvodacId(@Param("izvodacId") Long izvodacId);

    @Query("SELECT DISTINCT o FROM Oglas o " +
            "JOIN o.ulaznica u " +
            "JOIN u.izvodaci i " +
            "JOIN i.zanrIzvodaca z " +
            "JOIN z.korisnici korisnik " +
            "WHERE korisnik.idKorisnika = :idKorisnika " +
            "AND o.korisnik.idKorisnika != :idKorisnika") // Exclude ads created by the same user
    List<Oglas> findOglasiByKorisnikPreference(@Param("idKorisnika") Long idKorisnika);


    // Finds all Izvodac instances associated with the given Ulaznica ID
    @Query("SELECT i FROM Izvodac i " +
            "JOIN i.ulaznice u " +
            "WHERE u.idUlaznice = :ulaznicaId")
    List<Izvodac> findIzvodaciByUlaznicaId(@Param("ulaznicaId") Long ulaznicaId);


    // Method to find all Oglasi by Korisnik ID
    List<Oglas> findByKorisnik_IdKorisnika(Long korisnikId);


    @Query("SELECT DISTINCT z FROM Oglas o " +
            "JOIN o.ulaznica u " +
            "JOIN u.izvodaci i " +
            "JOIN i.zanrIzvodaca z " +
            "WHERE o.idOglasa = :oglasId")
    List<Zanr> findZanrsByOglasId(@Param("oglasId") Long oglasId);

    // Custom method to delete Oglas by Ulaznica ID
    void deleteByUlaznica_IdUlaznice(Long ulaznicaId);

    @Query("SELECT o FROM Oglas o WHERE o.korisnik.idKorisnika != :idKorisnika")
    List<Oglas> findAllExcludingKorisnik(@Param("idKorisnika") Long idKorisnika);

    // Method to find a single Oglas by Ulaznica ID, returns Optional
    Optional<Oglas> findByUlaznica_IdUlaznice(Long ulaznicaId);
}

package hr.unizg.fer.ticket4ticket.repository;

import hr.unizg.fer.ticket4ticket.entity.Oglas;
import  hr.unizg.fer.ticket4ticket.entity.Izvodac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Set;

@Repository
public interface OglasRepository extends JpaRepository<Oglas,Long> {

    // Finds all Oglas instances that have an associated Izvodac with the given ID
    @Query("SELECT o FROM Oglas o " +
            "JOIN o.ulaznica u " +
            "JOIN u.izvodaci i " +
            "WHERE i.idIzvodaca = :izvodacId")
    Set<Oglas> findByIzvodacId(@Param("izvodacId") Long izvodacId);

    // Finds Oglas instances by Izvodac's first and/or last name if provided (null or empty parameters are ignored)
    @Query("SELECT o FROM Oglas o " +
            "JOIN o.ulaznica u " +
            "JOIN u.izvodaci i " +
            "WHERE ((:izvodacIme IS NULL OR TRIM(:izvodacIme) = '') OR LOWER(i.imeIzvodaca) = LOWER(:izvodacIme)) AND " +
            "((:izvodacPrezime IS NULL OR TRIM(:izvodacPrezime) = '') OR LOWER(i.prezimeIzvodaca) = LOWER(:izvodacPrezime))")
    List<Oglas> findOglasiByFilter(@Param("izvodacIme") String izvodacIme,
                                   @Param("izvodacPrezime") String izvodacPrezime);

    // Finds Oglas instances that match the user's preferences based on the user's preferred genres
    @Query("SELECT DISTINCT o FROM Oglas o " +
            "JOIN o.ulaznica u " +
            "JOIN u.izvodaci i " +
            "JOIN i.zanrIzvodaca z " +
            "JOIN z.korisnici korisnik " +
            "WHERE korisnik.idKorisnika = :idKorisnika")
    List<Oglas> findOglasiByKorisnikPreference(@Param("idKorisnika") Long idKorisnika);


    // Finds all Izvodac instances associated with the given Ulaznica ID
    @Query("SELECT i FROM Izvodac i " +
            "JOIN i.ulaznice u " +
            "WHERE u.idUlaznice = :ulaznicaId")
    List<Izvodac> findIzvodaciByUlaznicaId(@Param("ulaznicaId") Long ulaznicaId);
}

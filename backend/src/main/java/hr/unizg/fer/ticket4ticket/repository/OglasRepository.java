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

    @Query("SELECT o FROM Oglas o " +
            "JOIN o.ulaznica u " +  // Replaced "koncert" with "ulaznica"
            "JOIN u.izvodaci i " +  // Replaced "koncert" with "ulaznica"
            "WHERE i.idIzvodaca = :izvodacId")
    Set<Oglas> findByIzvodacId(@Param("izvodacId") Long izvodacId);

    @Query("SELECT o FROM Oglas o " +
            "JOIN o.ulaznica u " +  // Replaced "koncert" with "ulaznica"
            "JOIN u.izvodaci i " +  // Replaced "koncert" with "ulaznica"
            "WHERE ((:izvodacIme IS NULL OR TRIM(:izvodacIme) = '') OR i.imeIzvodaca = :izvodacIme) AND " +
            "((:izvodacPrezime IS NULL OR TRIM(:izvodacPrezime) = '') OR i.prezimeIzvodaca = :izvodacPrezime)")
    List<Oglas> findOglasiByFilter(@Param("izvodacIme") String izvodacIme,
                                   @Param("izvodacPrezime") String izvodacPrezime);

    @Query("SELECT DISTINCT o FROM Oglas o " +
            "JOIN o.ulaznica u " +  // Replaced "koncert" with "ulaznica"
            "JOIN u.izvodaci i " +  // Replaced "koncert" with "ulaznica"
            "JOIN i.zanrIzvodaca z " +
            "JOIN z.korisnici korisnik " +
            "WHERE korisnik.idKorisnika = :idKorisnika")
    List<Oglas> findOglasiByKorisnikPreference(@Param("idKorisnika") Long idKorisnika);

    @Query("SELECT i FROM Izvodac i " +
            "JOIN i.ulaznice u " +  // Replaced "koncerti" with "ulaznice"
            "WHERE u.idUlaznice = :ulaznicaId")  // Replaced "koncertId" with "ulaznicaId"
    List<Izvodac> findIzvodaciByUlaznicaId(@Param("ulaznicaId") Long ulaznicaId);
}

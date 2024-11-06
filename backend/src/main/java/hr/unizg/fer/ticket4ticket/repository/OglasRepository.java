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
            "JOIN o.koncert k " +
            "JOIN k.izvodaci i " +
            "WHERE i.idIzvodaca = :izvodacId")
    Set<Oglas> findByIzvodacId(@Param("izvodacId") Long izvodacId);


    @Query("SELECT o FROM Oglas o " +
            "JOIN o.koncert k " +
            "JOIN k.izvodaci i " +
            "WHERE ((:izvodacIme IS NULL OR TRIM(:izvodacIme) = '') OR i.imeIzvodaca = :izvodacIme) AND " +
            "((:izvodacPrezime IS NULL OR TRIM(:izvodacPrezime) = '') OR i.prezimeIzvodaca = :izvodacPrezime)")
    List<Oglas> findOglasiByFilter(@Param("izvodacIme") String izvodacIme,
                                   @Param("izvodacPrezime") String izvodacPrezime);


    @Query("SELECT DISTINCT o FROM Oglas o " +
            "JOIN o.koncert k " +
            "JOIN k.izvodaci i " +
            "JOIN i.zanrIzvodaca z " + // Make sure 'zanrIzvodaca' is the correct field
            "JOIN z.korisnici korisnik " +
            "WHERE korisnik.idKorisnika = :idKorisnika")
    List<Oglas> findOglasiByKorisnikPreference(@Param("idKorisnika") Long idKorisnika);


    @Query("SELECT i FROM Izvodac i " +
            "JOIN i.koncerti k " +           // Join with the concerts that this Izvodac is part of
            "WHERE k.idKoncerta = :koncertId")  // Find Izvodaci for a specific concert by concert ID
    List<Izvodac> findIzvodaciByKoncertId(@Param("koncertId") Long koncertId);
}

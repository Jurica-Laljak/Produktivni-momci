package com.ticket4ticket.backend.repository;


import com.ticket4ticket.backend.entity.Oglas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface OglasRepository extends JpaRepository<Oglas,Long> {

    @Query("SELECT o FROM Oglas o " +
            "JOIN o.koncert k " +
            "JOIN k.izvodaci i " +
            "WHERE i.idIzvodaca = :izvodacId")
    Set<Oglas> findByIzvodacId(@Param("izvodacId") Long izvodacId);
}

package com.ticket4ticket.backend.repository;

import com.ticket4ticket.backend.entity.Izvodac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IzvodacRepository extends JpaRepository<Izvodac,Long> {
    // Custom method to find user by Google ID
    List<Izvodac> findByZanrIzvodaca(String zanrIzvodaca);
}

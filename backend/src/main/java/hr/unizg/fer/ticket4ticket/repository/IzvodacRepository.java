package hr.unizg.fer.ticket4ticket.repository;

import hr.unizg.fer.ticket4ticket.entity.Izvodac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IzvodacRepository extends JpaRepository<Izvodac,Long> {
    // Custom method to find user by Google ID
    List<Izvodac> findByZanrIzvodaca(String zanrIzvodaca);
}

package hr.unizg.fer.ticket4ticket.repository;

import hr.unizg.fer.ticket4ticket.entity.Koncert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KoncertRepository  extends JpaRepository<Koncert,Long> {
}

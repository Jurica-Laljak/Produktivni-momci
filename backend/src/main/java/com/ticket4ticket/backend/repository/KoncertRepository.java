package com.ticket4ticket.backend.repository;

import com.ticket4ticket.backend.entity.Koncert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KoncertRepository  extends JpaRepository<Koncert,Long> {
}

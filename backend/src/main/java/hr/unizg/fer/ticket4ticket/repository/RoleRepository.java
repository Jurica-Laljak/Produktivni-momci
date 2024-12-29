package hr.unizg.fer.ticket4ticket.repository;

import hr.unizg.fer.ticket4ticket.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
}

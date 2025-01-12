package hr.unizg.fer.ticket4ticket.controller;

import hr.unizg.fer.ticket4ticket.entity.Role;
import hr.unizg.fer.ticket4ticket.service.RoleService;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/role")
@CrossOrigin
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RolesAllowed("ADMIN")
    @GetMapping("/{id}")
    public ResponseEntity<Role> getRole(@PathVariable Long id) {
        Role role = roleService.getRoleById(id);
        if (role == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(role);
    }
}

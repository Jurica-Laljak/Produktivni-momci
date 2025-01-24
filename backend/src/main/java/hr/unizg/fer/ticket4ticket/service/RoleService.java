package hr.unizg.fer.ticket4ticket.service;

import hr.unizg.fer.ticket4ticket.entity.Role;

import java.util.List;

public interface RoleService {

    Role getRoleByName(String roleName);

    Role getRoleById(Long id);

    List<Role> getAllRoles();
}

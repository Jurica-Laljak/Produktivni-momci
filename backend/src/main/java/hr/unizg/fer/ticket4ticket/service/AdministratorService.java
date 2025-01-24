package hr.unizg.fer.ticket4ticket.service;

import hr.unizg.fer.ticket4ticket.entity.Administrator;

public interface AdministratorService {
    Administrator getAdministratorByEmail(String email);
}

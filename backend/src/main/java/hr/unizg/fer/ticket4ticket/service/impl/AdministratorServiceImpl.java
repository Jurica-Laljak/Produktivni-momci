package hr.unizg.fer.ticket4ticket.service.impl;

import hr.unizg.fer.ticket4ticket.entity.Administrator;
import hr.unizg.fer.ticket4ticket.repository.AdministratorRepository;
import hr.unizg.fer.ticket4ticket.service.AdministratorService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdministratorServiceImpl implements AdministratorService {

    @Autowired
    private AdministratorRepository administratorRepository;

    @Override
    public Administrator getAdministratorByEmail(String email) {
        return administratorRepository.findByEmailAdmin(email);
    }
}

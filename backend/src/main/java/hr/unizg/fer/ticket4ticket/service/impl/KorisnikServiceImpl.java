package hr.unizg.fer.ticket4ticket.service.impl;

import hr.unizg.fer.ticket4ticket.dto.KorisnikDto;
import hr.unizg.fer.ticket4ticket.entity.Korisnik;
import hr.unizg.fer.ticket4ticket.entity.Role;
import hr.unizg.fer.ticket4ticket.exception.ResourceNotFoundException;
import hr.unizg.fer.ticket4ticket.mapper.KorisnikMapper;
import hr.unizg.fer.ticket4ticket.repository.KorisnikRepository;
import hr.unizg.fer.ticket4ticket.service.KorisnikService;
import hr.unizg.fer.ticket4ticket.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class KorisnikServiceImpl implements KorisnikService {

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private RoleService roleService;

    @Override
    public KorisnikDto createKorisnik(KorisnikDto korisnikDto) {

        Korisnik korisnik = KorisnikMapper.mapToKorisnik(korisnikDto);
        Korisnik savedKorisnik = korisnikRepository.save(korisnik);

        return KorisnikMapper.mapToKorisnikDto(savedKorisnik);
    }

    @Override
    public KorisnikDto getKorisnikById(Long korisnikId) {
        Korisnik korisnik = korisnikRepository.findById(korisnikId)
                .orElseThrow(()-> new ResourceNotFoundException("Korisnik sa tim id-om ne postoji: " + korisnikId));

        return KorisnikMapper.mapToKorisnikDto(korisnik);
    }


    @Override
    public List<KorisnikDto> getAllKorisnici () {
        List<Korisnik> korisnici = korisnikRepository.findAll();


        return korisnici.stream().map(KorisnikMapper::mapToKorisnikDto)
                .collect(Collectors.toList());
    }

    //Returns the
    @Override
    @Transactional
    public KorisnikDto findKorisnikByGoogleId(String googleId) {
        // Check if user with the Google ID already exists
        Korisnik existingKorisnik = korisnikRepository.findByGoogleId(googleId);

        if (existingKorisnik != null) {
            // User exists, return the found user as DTO
            return KorisnikMapper.mapToKorisnikDto(existingKorisnik);
        }

        // User does not exist, return null
        return null;
    }

    @Override
    public KorisnikDto assignAdminByGoogleId(String googleId) {
        Korisnik korisnik = korisnikRepository.findByGoogleId(googleId);

        Role admin = roleService.getRoleByName("ROLE_ADMIN");
        Set<Role> roles = korisnik.getRoles();
        roles.add(admin);

        korisnik.setRoles(roles);

        Korisnik savedKorisnik = korisnikRepository.save(korisnik);

        return KorisnikMapper.mapToKorisnikDto(savedKorisnik);
    }
}

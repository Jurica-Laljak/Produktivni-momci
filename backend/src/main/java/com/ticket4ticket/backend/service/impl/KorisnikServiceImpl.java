package com.ticket4ticket.backend.service.impl;

import com.ticket4ticket.backend.dto.KorisnikDto;
import com.ticket4ticket.backend.entity.Korisnik;
import com.ticket4ticket.backend.exception.ResourceNotFoundException;
import com.ticket4ticket.backend.mapper.KorisnikMapper;
import com.ticket4ticket.backend.repository.KorisnikRepository;
import com.ticket4ticket.backend.service.KorisnikService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class KorisnikServiceImpl implements KorisnikService {

    private KorisnikRepository korisnikRepository;

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


        return korisnici.stream().map((korisnik) -> KorisnikMapper.mapToKorisnikDto(korisnik))
                .collect(Collectors.toList());
    }

    // New method for Google OAuth
    @Override
    @Transactional
    public KorisnikDto findOrCreateKorisnikByGoogleId(String googleId, KorisnikDto korisnikDto) {
        // Check if user with the Google ID already exists
        Korisnik existingKorisnik = korisnikRepository.findByGoogleId(googleId);

        if (existingKorisnik != null) {
            // User exists, return the found user as DTO
            return KorisnikMapper.mapToKorisnikDto(existingKorisnik);
        }

        // User does not exist, create a new one
        Korisnik newKorisnik = KorisnikMapper.mapToKorisnik(korisnikDto);
        newKorisnik.setGoogleId(googleId);  // Set the Google ID


        Korisnik savedKorisnik = korisnikRepository.save(newKorisnik);

        return KorisnikMapper.mapToKorisnikDto(savedKorisnik);
    }





}

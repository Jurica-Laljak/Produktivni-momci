package com.ticket4ticket.backend.service;


import com.ticket4ticket.backend.dto.KorisnikDto;

import java.util.List;


public interface KorisnikService {
    KorisnikDto createKorisnik(KorisnikDto korisnikDto);

    KorisnikDto getKorisnikById(Long korisnikId);

    List<KorisnikDto> getAllKorisnici();

    KorisnikDto findOrCreateKorisnikByGoogleId(String googleId, KorisnikDto korisnikDto);



}


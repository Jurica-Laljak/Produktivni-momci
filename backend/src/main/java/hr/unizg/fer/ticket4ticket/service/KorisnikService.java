package hr.unizg.fer.ticket4ticket.service;

import hr.unizg.fer.ticket4ticket.dto.KorisnikDto;

import java.util.List;

public interface KorisnikService {

    KorisnikDto createKorisnik(KorisnikDto korisnikDto);

    KorisnikDto getKorisnikById(Long korisnikId);

    List<KorisnikDto> getAllKorisnici();

    KorisnikDto findKorisnikByGoogleId(String googleId, KorisnikDto korisnikDto);


}


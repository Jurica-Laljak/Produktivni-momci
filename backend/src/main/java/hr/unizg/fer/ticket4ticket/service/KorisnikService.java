package hr.unizg.fer.ticket4ticket.service;

import hr.unizg.fer.ticket4ticket.dto.KorisnikDto;
import hr.unizg.fer.ticket4ticket.dto.KorisnikUpdateDto;

import java.util.List;

public interface KorisnikService {

    KorisnikDto createKorisnik(KorisnikDto korisnikDto);

    KorisnikDto getKorisnikById(Long korisnikId);

    List<KorisnikDto> getAllKorisnici();

    KorisnikDto findKorisnikByGoogleId(String googleId);

    KorisnikDto assignAdminByGoogleId(String googleId);

    KorisnikDto updateKorisnikFields(Long id, KorisnikUpdateDto updateDto);

    void deleteKorisnikById(Long id);

    List<KorisnikDto> getKorisniciByZanr(Long zanrId);
}


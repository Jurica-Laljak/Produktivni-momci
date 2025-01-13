package hr.unizg.fer.ticket4ticket.service;

import hr.unizg.fer.ticket4ticket.dto.UlaznicaDto;

import java.util.List;

public interface UlaznicaService {

    UlaznicaDto createUlaznica(UlaznicaDto ulaznicaDto);

    UlaznicaDto getUlaznicaById(Long ulaznicaId);

    List<UlaznicaDto> getAllUlaznice();

    List<UlaznicaDto> getUlazniceByIdKorisnika(Long idKorisnika);


    UlaznicaDto assignUserToUlaznica(Long idUlaznice, Long korisnikId);

    UlaznicaDto getUlaznicaByOglasId(Long oglasId);

    List<UlaznicaDto> getUlazniceWithoutOglas(Long idKorisnika);
}

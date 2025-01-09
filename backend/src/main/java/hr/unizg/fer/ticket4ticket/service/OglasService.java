package hr.unizg.fer.ticket4ticket.service;

import hr.unizg.fer.ticket4ticket.dto.OglasDto;
import hr.unizg.fer.ticket4ticket.dto.IzvodacDto;
import hr.unizg.fer.ticket4ticket.dto.OglasFilterDto;
import java.util.List;

public interface OglasService {

    OglasDto createOglas(OglasDto oglasDto);

    OglasDto getOglasById(Long oglasId);

    List<OglasDto> getAllOglasi();

    List<OglasDto> getRandomOglasi(int brojRandomOglasa);

    List<OglasDto> getOglasiByFilter(OglasFilterDto filterDto);

    List<OglasDto> getOglasiByKorisnikPreference(Long idKorisnika);

    List<OglasDto> findActiveOglasesByKorisnikId(Long korisnikId);

    List<IzvodacDto> getIzvodaciForOglas(Long oglasId);

    public void deleteAllOglasiByKorisnikId(Long idKorisnika);
}

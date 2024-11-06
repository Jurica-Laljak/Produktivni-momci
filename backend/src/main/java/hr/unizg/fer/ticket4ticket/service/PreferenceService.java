package hr.unizg.fer.ticket4ticket.service;


import hr.unizg.fer.ticket4ticket.dto.IzvodacDto;
import hr.unizg.fer.ticket4ticket.dto.OglasDto;


import  hr.unizg.fer.ticket4ticket.dto.KorisnikDto;

import  hr.unizg.fer.ticket4ticket.dto.OglasFilterDto;


import java.util.List;
import java.util.Set;

public interface PreferenceService {

    Set<IzvodacDto> getIzvodaciForKorisnik(Long korisnikId);

    List<OglasDto> getOglasiForKorisnik(Long korisnikId);

    List<OglasDto> getOglasiByFilter(OglasFilterDto filterDto);

    boolean updateUserGenrePreferences(KorisnikDto korisnikDto, Set<Long> zanrIds);
}

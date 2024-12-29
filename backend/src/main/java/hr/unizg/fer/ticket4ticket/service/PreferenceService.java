package hr.unizg.fer.ticket4ticket.service;

import hr.unizg.fer.ticket4ticket.dto.OglasDto;
import hr.unizg.fer.ticket4ticket.dto.KorisnikDto;
import hr.unizg.fer.ticket4ticket.dto.OglasFilterDto;
import hr.unizg.fer.ticket4ticket.dto.UlaznicaDto;

import java.util.List;
import java.util.Set;

public interface PreferenceService {

    List<OglasDto> getOglasiByFilter(OglasFilterDto filterDto);

    boolean updateUserGenrePreferences(KorisnikDto korisnikDto, Set<Long> zanrIds);


    UlaznicaDto changeUlaznicaStatusAndAssignUser(String sifraUlaznice, Long korisnikId);
}

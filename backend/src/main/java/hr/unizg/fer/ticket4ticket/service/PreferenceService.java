package hr.unizg.fer.ticket4ticket.service;

import hr.unizg.fer.ticket4ticket.dto.*;

import java.util.List;
import java.util.Set;

public interface PreferenceService {

    List<OglasInfoDto> getOglasiByFilter(OglasFilterDto filterDto);

    boolean updateUserGenrePreferences(KorisnikDto korisnikDto, Set<Long> zanrIds);


    UlaznicaDto changeUlaznicaStatusAndAssignUser(String sifraUlaznice, Long korisnikId);

    void resetUlazniceStatusAndClearUser(Long korisnikId);
}

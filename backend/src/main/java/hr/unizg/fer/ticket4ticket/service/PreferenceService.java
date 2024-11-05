package hr.unizg.fer.ticket4ticket.service;

import hr.unizg.fer.ticket4ticket.dto.IzvodacDto;
import hr.unizg.fer.ticket4ticket.dto.OglasDto;

import java.util.List;
import java.util.Set;

public interface PreferenceService {

    Set<IzvodacDto> getIzvodaciForKorisnik(Long korisnikId);

    public List<OglasDto> getOglasiForKorisnik(Long korisnikId);
}

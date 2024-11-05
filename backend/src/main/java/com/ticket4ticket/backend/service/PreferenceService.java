package com.ticket4ticket.backend.service;

import com.ticket4ticket.backend.dto.IzvodacDto;
import com.ticket4ticket.backend.dto.OglasDto;

import java.util.List;
import java.util.Set;

public interface PreferenceService {

    Set<IzvodacDto> getIzvodaciForKorisnik(Long korisnikId);

    public List<OglasDto> getOglasiForKorisnik(Long korisnikId);
}

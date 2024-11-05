package com.ticket4ticket.backend.service.impl;

import com.ticket4ticket.backend.dto.IzvodacDto;
import com.ticket4ticket.backend.dto.OglasDto;
import com.ticket4ticket.backend.entity.Korisnik;
import com.ticket4ticket.backend.exception.ResourceNotFoundException;
import com.ticket4ticket.backend.repository.KorisnikRepository;
import com.ticket4ticket.backend.service.IzvodacService;
import com.ticket4ticket.backend.service.OglasService;
import com.ticket4ticket.backend.service.PreferenceService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class PreferenceServiceImpl implements PreferenceService {
    @Autowired
    private KorisnikRepository korisnikRepository;
    @Autowired
    private IzvodacService izvodacService; // Declare IzvodacService

    @Autowired
    private OglasService oglasService; // Declare IzvodacService


    @Override
    public Set<IzvodacDto> getIzvodaciForKorisnik(Long korisnikId) {
        Korisnik korisnik = korisnikRepository.findById(korisnikId)
                .orElseThrow(() -> new ResourceNotFoundException("Korisnik not found"));

        Set<Long> izvodacIds = korisnik.getOmiljeniIzvodaciIds(); // Assuming this method returns Set<Long>

        return izvodacService.getIzvodaciByIds(izvodacIds);
    }


    @Override
    public List<OglasDto> getOglasiForKorisnik(Long korisnikId) {
        // Get preferred izvodaci for the user
        Set<IzvodacDto> preferredIzvodaci = getIzvodaciForKorisnik(korisnikId);

        List<OglasDto> allOglasi = new ArrayList<>(); // List to hold all oglasi

        // Loop through each preferred izvodac and fetch corresponding oglasi
        for (IzvodacDto izvodac : preferredIzvodaci) {
            List<OglasDto> oglasiForIzvodac = oglasService.getOglasiByIzvodacId(izvodac.getIdIzvodaca());
            allOglasi.addAll(oglasiForIzvodac); // Combine results
        }

        return allOglasi; // Return the combined list of oglasi
    }
}

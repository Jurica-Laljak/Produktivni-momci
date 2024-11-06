package hr.unizg.fer.ticket4ticket.service.impl;


import hr.unizg.fer.ticket4ticket.dto.IzvodacDto;
import hr.unizg.fer.ticket4ticket.dto.OglasDto;
import hr.unizg.fer.ticket4ticket.entity.Korisnik;
import hr.unizg.fer.ticket4ticket.exception.ResourceNotFoundException;
import hr.unizg.fer.ticket4ticket.repository.KorisnikRepository;
import hr.unizg.fer.ticket4ticket.service.IzvodacService;
import hr.unizg.fer.ticket4ticket.service.OglasService;
import hr.unizg.fer.ticket4ticket.service.PreferenceService;
import hr.unizg.fer.ticket4ticket.dto.KorisnikDto;
import hr.unizg.fer.ticket4ticket.dto.OglasFilterDto;
import hr.unizg.fer.ticket4ticket.entity.Zanr;
import hr.unizg.fer.ticket4ticket.repository.ZanrRepository;

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
    private ZanrRepository zanrRepository;

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

    @Override
    public List<OglasDto> getOglasiByFilter(OglasFilterDto filterDto) {
        return oglasService.getOglasiByFilter(filterDto);
    }

    @Override
    public boolean updateUserGenrePreferences(KorisnikDto korisnikDto, Set<Long> zanrIds) {
        // Fetch the user from the database
        Korisnik korisnik = korisnikRepository.findById(korisnikDto.getIdKorisnika())
                .orElse(null);

        if (korisnik == null) {
            return false; // User not found
        }

        // Fetch the genres by their IDs
        List<Zanr> likedZanrovi = zanrRepository.findAllById(zanrIds);

        // Check if the list of liked genres is empty or not
        if (!likedZanrovi.isEmpty()) {
            // Clear existing genre preferences (remove old associations in the many-to-many table)
            korisnik.getOmiljeniZanrovi().clear();

            // Add the new genre associations
            for (Zanr zanr : likedZanrovi) {
                korisnik.getOmiljeniZanrovi().add(zanr);  // This will add the genres to the user's set of preferred genres
            }

            korisnikRepository.save(korisnik);  // Save the updated user with new genres
            return true;
        }

        return false;  // If no genres were found, return false
    }
}

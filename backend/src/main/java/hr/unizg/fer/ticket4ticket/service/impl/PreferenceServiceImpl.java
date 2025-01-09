package hr.unizg.fer.ticket4ticket.service.impl;

import hr.unizg.fer.ticket4ticket.dto.OglasDto;
import hr.unizg.fer.ticket4ticket.dto.UlaznicaDto;
import hr.unizg.fer.ticket4ticket.entity.Korisnik;
import hr.unizg.fer.ticket4ticket.entity.Ulaznica;
import hr.unizg.fer.ticket4ticket.mapper.UlaznicaMapper;
import hr.unizg.fer.ticket4ticket.repository.KorisnikRepository;
import hr.unizg.fer.ticket4ticket.repository.UlaznicaRepository;
import hr.unizg.fer.ticket4ticket.service.OglasService;
import hr.unizg.fer.ticket4ticket.service.PreferenceService;
import hr.unizg.fer.ticket4ticket.dto.KorisnikDto;
import hr.unizg.fer.ticket4ticket.dto.OglasFilterDto;
import hr.unizg.fer.ticket4ticket.entity.Zanr;
import hr.unizg.fer.ticket4ticket.repository.ZanrRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class PreferenceServiceImpl implements PreferenceService {

    @Autowired
    private KorisnikRepository korisnikRepository;
    @Autowired
    private ZanrRepository zanrRepository;

    @Autowired
    private UlaznicaRepository ulaznicaRepository;

    @Autowired
    private OglasService oglasService;


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

    @Override
    public UlaznicaDto changeUlaznicaStatusAndAssignUser(String sifraUlaznice, Long korisnikId) {
        // Find the Ulaznica by its sifraUlaznice
        Ulaznica ulaznica = ulaznicaRepository.findBySifraUlaznice(sifraUlaznice);

        if (ulaznica == null) {
            throw new RuntimeException("Ulaznica not found");
        }

        // Check if the status is already "PREUZETA"
        if (ulaznica.getStatus() == Ulaznica.Status.PREUZETA) {
            throw new IllegalStateException("Ulaznica is already marked as PREUZETA");
        }

        // Change the status of the Ulaznica to PREUZETA
        ulaznica.setStatus(Ulaznica.Status.PREUZETA);

        // Find the Korisnik by ID
        Korisnik korisnik = korisnikRepository.findById(korisnikId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Associate the logged-in user with the Ulaznica
        ulaznica.setKorisnik(korisnik);

        // Save the updated Ulaznica entity
        Ulaznica updatedUlaznica = ulaznicaRepository.save(ulaznica);

        // Convert the updated Ulaznica entity to UlaznicaDto using the mapper
        return UlaznicaMapper.mapToUlaznicaDto(updatedUlaznica);
    }


    @Override
    public void resetUlazniceStatusAndClearUser(Long korisnikId) {
        // Find all Ulaznica entities associated with the given korisnikId
        List<Ulaznica> ulaznice = ulaznicaRepository.findAllByKorisnik_IdKorisnika(korisnikId); ;



        // Iterate through each Ulaznica and update the status and clear the user
        for (Ulaznica ulaznica : ulaznice) {
            // Set the status to NEPREUZETA
            ulaznica.setStatus(Ulaznica.Status.NEPREUZETA);

            // Set the idKorisnika (the user) to null
            ulaznica.setKorisnik(null);
        }

        // Save all the updated Ulaznica entities
        ulaznicaRepository.saveAll(ulaznice);
    }


}

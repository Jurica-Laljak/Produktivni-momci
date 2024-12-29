package hr.unizg.fer.ticket4ticket.controller;

import hr.unizg.fer.ticket4ticket.dto.*;
import hr.unizg.fer.ticket4ticket.entity.Transakcija;
import hr.unizg.fer.ticket4ticket.service.PreferenceService;
import hr.unizg.fer.ticket4ticket.service.KorisnikService;
import hr.unizg.fer.ticket4ticket.service.OglasService;
import hr.unizg.fer.ticket4ticket.service.TransakcijaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationToken;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/api/preference")
@CrossOrigin
public class PreferenceController {

    @Autowired
    private PreferenceService preferenceService;
    @Autowired
    private OglasService oglasService;
    @Autowired
    private KorisnikService korisnikService;
    @Autowired
    private TransakcijaService transakcijaService;

    @PostMapping("/oglasi/kreiraj")
    public ResponseEntity<OglasDto> createOglas(UsernamePasswordAuthenticationToken token, @RequestBody OglasDto oglasDto) {
        String googleId = token.getName();
        KorisnikDto korisnikDto = korisnikService.findKorisnikByGoogleId(googleId);

        if (korisnikDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        oglasDto.setKorisnikId(korisnikDto.getIdKorisnika());
        OglasDto createdOglas = oglasService.createOglas(oglasDto);

        return new ResponseEntity<>(createdOglas, HttpStatus.CREATED);
    }

    //Returns all Oglas resources that match up with the provided users search filter (currently by Name and Surname of Izvodac)
    @PostMapping("/oglasi/filter")
    public ResponseEntity<List<OglasDto>> getOglasiByFilter(@RequestBody OglasFilterDto filterDto) {
        List<OglasDto> filteredOglasi = preferenceService.getOglasiByFilter(filterDto);
        return ResponseEntity.ok(filteredOglasi);
    }

    @PostMapping("/zanrovi")
    public ResponseEntity<Void> updateUserGenrePreferences(UsernamePasswordAuthenticationToken token, @RequestBody Set<Long> zanrIds) {
        // Extract Google ID from OAuth2 token
        String googleId = token.getName();

        // Get authenticated user's information
        KorisnikDto korisnik = korisnikService.findKorisnikByGoogleId(googleId);

        // Update the user's genres with the provided list of Zanr IDs
        boolean isUpdated = preferenceService.updateUserGenrePreferences(korisnik, zanrIds);

        if (!isUpdated) {
            // Return error HTTP status
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Return success HTTP status
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // Returns the list of oglasi by authenticated user's preference (by genres he likes)
    @GetMapping("/oglasi")
    public ResponseEntity<List<OglasDto>> getOglasiByGoogleId(UsernamePasswordAuthenticationToken token, Model model) {
        // Extract the Google ID from the OAuth2 token
        String googleId = token.getName();

        // Returns Korisnik By GoogleId
        KorisnikDto korisnikDto = korisnikService.findKorisnikByGoogleId(googleId);

        // Fetch the 'Oglas' listings based on the user's preferences
        List<OglasDto> oglasi = oglasService.getOglasiByKorisnikPreference(korisnikDto.getIdKorisnika());

        return ResponseEntity.ok(oglasi);
    }

    @GetMapping("/aktivnioglasi")
    public ResponseEntity<List<OglasDto>> getActiveOglasiByUserPreferences(UsernamePasswordAuthenticationToken token, Model model) {
        // Extract the Google ID from the OAuth2 token
        String googleId = token.getName();

        // Find the Korisnik (User) by their Google ID
        KorisnikDto korisnikDto = korisnikService.findKorisnikByGoogleId(googleId);

        // Fetch the 'Oglas' listings based on the user's preferences (e.g., genres they like)
        List<OglasDto> activeOglasi = oglasService.findActiveOglasesByKorisnikId(korisnikDto.getIdKorisnika());

        // Return the response with the list of active Oglasi (ads)
        return ResponseEntity.ok(activeOglasi);
    }

    @GetMapping("/aktivnetransakcije")
    public ResponseEntity<List<TransakcijaDto>> getActiveTransakcijeByUserPreferences(UsernamePasswordAuthenticationToken token) {
        // Extract the Google ID from the OAuth2 token
        String googleId = token.getName();

        // Find the Korisnik (User) by their Google ID
        KorisnikDto korisnikDto = korisnikService.findKorisnikByGoogleId(googleId);

        // Fetch the active Transakcije for the user with status 'CEKA_ODGOVOR'
        List<TransakcijaDto> activeTransakcije = transakcijaService.getTransakcijeByKorisnikPonudaIdAndStatus(korisnikDto.getIdKorisnika(), Transakcija.StatusTransakcije.CEKA_POTVRDU);

        // Return the response with the list of active Transakcije
        return ResponseEntity.ok(activeTransakcije);
    }

    @GetMapping("/provedenetransakcije")
    public ResponseEntity<List<TransakcijaDto>> getInactiveTransakcijeByUserPreferences(UsernamePasswordAuthenticationToken token) {
        // Extract the Google ID from the OAuth2 token
        String googleId = token.getName();

        // Find the Korisnik (User) by their Google ID
        KorisnikDto korisnikDto = korisnikService.findKorisnikByGoogleId(googleId);

        // Fetch the 'PROVEDENA' Transakcije for the user
        List<TransakcijaDto> provedenaTransakcije = transakcijaService.getTransakcijeByKorisnikPonudaIdAndStatus(korisnikDto.getIdKorisnika(), Transakcija.StatusTransakcije.PROVEDENA);

        // Fetch the 'ODBIJENA' Transakcije for the user
        List<TransakcijaDto> odbijenaTransakcije = transakcijaService.getTransakcijeByKorisnikPonudaIdAndStatus(korisnikDto.getIdKorisnika(), Transakcija.StatusTransakcije.ODBIJENA);

        // Combine both lists (PROVEDENA + ODBIJENA)
        List<TransakcijaDto> allInactiveTransakcije = new ArrayList<>();
        allInactiveTransakcije.addAll(provedenaTransakcije);
        allInactiveTransakcije.addAll(odbijenaTransakcije);

        // Return the combined list of inactive Transakcije
        return ResponseEntity.ok(allInactiveTransakcije);
    }

    @PostMapping("/preuzmi")
    public ResponseEntity<UlaznicaDto> preuzmiUlaznicu(UsernamePasswordAuthenticationToken token,
                                                       @RequestBody PreuzmiUlaznicuDto preuzmiUlaznicuDto) {
        // Extract Google ID from OAuth2 token (or use other identifiers like email)
        String googleId = token.getName();

        // Fetch the currently authenticated user based on Google ID
        KorisnikDto korisnikDto = korisnikService.findKorisnikByGoogleId(googleId);
        if (korisnikDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // User not found
        }

        // Use korisnikDto to get the correct user ID
        Long korisnikId = korisnikDto.getIdKorisnika();

        // Call the service to update the Ulaznica status and assign the user
        try {
            UlaznicaDto updatedUlaznica = preferenceService.changeUlaznicaStatusAndAssignUser(preuzmiUlaznicuDto.getSifraUlaznice(), korisnikId);
            return new ResponseEntity<>(updatedUlaznica, HttpStatus.OK);
        } catch (Exception e) {
            // Handle any potential exceptions
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // e.g., Ulaznica not found or already PREUZETA
        }
    }




}
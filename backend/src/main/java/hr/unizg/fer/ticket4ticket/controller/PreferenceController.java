package hr.unizg.fer.ticket4ticket.controller;

import hr.unizg.fer.ticket4ticket.dto.OglasDto;
import hr.unizg.fer.ticket4ticket.service.PreferenceService;
import hr.unizg.fer.ticket4ticket.dto.KorisnikDto;
import hr.unizg.fer.ticket4ticket.dto.OglasFilterDto;
import hr.unizg.fer.ticket4ticket.service.KorisnikService;
import hr.unizg.fer.ticket4ticket.service.OglasService;
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
        KorisnikDto korisnik = korisnikService.findKorisnikByGoogleId(googleId, new KorisnikDto());

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
        KorisnikDto korisnikDto = korisnikService.findKorisnikByGoogleId(googleId, new KorisnikDto());

        // Fetch the 'Oglas' listings based on the user's preferences
        List<OglasDto> oglasi = oglasService.getOglasiByKorisnikPreference(korisnikDto.getIdKorisnika());

        return ResponseEntity.ok(oglasi);
    }

}

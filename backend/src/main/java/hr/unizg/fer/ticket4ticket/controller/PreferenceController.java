package hr.unizg.fer.ticket4ticket.controller;


import hr.unizg.fer.ticket4ticket.dto.IzvodacDto;
import hr.unizg.fer.ticket4ticket.dto.OglasDto;
import hr.unizg.fer.ticket4ticket.service.PreferenceService;


import hr.unizg.fer.ticket4ticket.dto.KorisnikDto;

import hr.unizg.fer.ticket4ticket.dto.OglasFilterDto;
import hr.unizg.fer.ticket4ticket.service.KorisnikService;
import hr.unizg.fer.ticket4ticket.service.OglasService;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/api/preference")
public class PreferenceController {

    @Autowired
    private PreferenceService preferenceService;

    @Autowired
    private OglasService oglasService;

    @Autowired
    private KorisnikService korisnikService;

    @GetMapping("/{korisnikId}/voliSlusati")
    public ResponseEntity<Set<IzvodacDto>> getIzvodaciForKorisnik(@PathVariable Long korisnikId) {
        Set<IzvodacDto> izvodaci = preferenceService.getIzvodaciForKorisnik(korisnikId);
        return ResponseEntity.ok(izvodaci);
    }

    @GetMapping("/{korisnikId}/oglasi")
    public ResponseEntity<List<OglasDto>> getOglasiForKorisnik(@PathVariable Long korisnikId) {
        List<OglasDto> oglasi = preferenceService.getOglasiForKorisnik(korisnikId);
        return ResponseEntity.ok(oglasi);
    }

    @PostMapping("/oglasi/filter")
    public ResponseEntity<List<OglasDto>> getOglasiByFilter(@RequestBody OglasFilterDto filterDto) {
        List<OglasDto> filteredOglasi = preferenceService.getOglasiByFilter(filterDto);
        return ResponseEntity.ok(filteredOglasi);
    }


    @PostMapping("/zanrovi")
    public ResponseEntity<String> updateUserGenrePreferences(OAuth2AuthenticationToken token, @RequestBody Set<Long> zanrIds) {
        // Extract Google ID from OAuth2 token
        String googleId = token.getPrincipal().getAttribute("sub"); // "sub" is typically used as a unique ID in OAuth2 //SA OAUTH

        // Use an existing method to find or create the user based on the Google ID
        KorisnikDto korisnik = korisnikService.findOrCreateKorisnikByGoogleId(googleId, new KorisnikDto()); //SA OAUTH



        // Update the user's genres with the provided list of Zanr IDs
        boolean isUpdated = preferenceService.updateUserGenrePreferences(korisnik, zanrIds);

        if (!isUpdated) {
            return ResponseEntity.status(400).body("Failed to update genres"); // Failed to update genres
        }

        // Return success response
        return ResponseEntity.ok("User genre preferences updated successfully");
    }





}

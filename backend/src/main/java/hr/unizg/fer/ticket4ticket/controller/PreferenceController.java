package hr.unizg.fer.ticket4ticket.controller;

import hr.unizg.fer.ticket4ticket.dto.*;
import hr.unizg.fer.ticket4ticket.entity.Transakcija;
import hr.unizg.fer.ticket4ticket.service.*;
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

    @Autowired
    private UlaznicaService ulaznicaService;



    private Long getUserIdFromToken(UsernamePasswordAuthenticationToken token) {
        // Extract Google ID from the authentication token
        String googleId = token.getName();

        // Find the user based on the Google ID
        KorisnikDto korisnikDto = korisnikService.findKorisnikByGoogleId(googleId);
        if (korisnikDto == null) {
            throw new IllegalArgumentException("User not found for Google ID: " + googleId);
        }

        // Return the user ID
        return korisnikDto.getIdKorisnika();
    }

    @PostMapping("/oglasi/kreiraj")
    public ResponseEntity<OglasDto> createOglas(UsernamePasswordAuthenticationToken token, @RequestBody OglasDto oglasDto) {
        try {
            Long korisnikId = getUserIdFromToken(token);
            oglasDto.setKorisnikId(korisnikId);
            OglasDto createdOglas = oglasService.createOglas(oglasDto);
            return new ResponseEntity<>(createdOglas, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/oglasi/filter")
    public ResponseEntity<List<OglasDto>> getOglasiByFilter(@RequestBody OglasFilterDto filterDto) {
        List<OglasDto> filteredOglasi = preferenceService.getOglasiByFilter(filterDto);
        return ResponseEntity.ok(filteredOglasi);
    }

    @PostMapping("/zanrovi")
    public ResponseEntity<Void> updateUserGenrePreferences(UsernamePasswordAuthenticationToken token, @RequestBody Set<Long> zanrIds) {
        try {
            Long korisnikId = getUserIdFromToken(token);
            KorisnikDto korisnik = korisnikService.findKorisnikByGoogleId(token.getName());
            boolean isUpdated = preferenceService.updateUserGenrePreferences(korisnik, zanrIds);

            if (!isUpdated) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/oglasi")
    public ResponseEntity<List<OglasDto>> getOglasiByGoogleId(UsernamePasswordAuthenticationToken token) {
        try {
            Long korisnikId = getUserIdFromToken(token);
            List<OglasDto> oglasi = oglasService.getOglasiByKorisnikPreference(korisnikId);
            return ResponseEntity.ok(oglasi);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/oglasi/aktivni")
    public ResponseEntity<List<OglasDto>> getActiveOglasiByUserPreferences(UsernamePasswordAuthenticationToken token) {
        try {
            Long korisnikId = getUserIdFromToken(token);
            List<OglasDto> activeOglasi = oglasService.findActiveOglasesByKorisnikId(korisnikId);
            return ResponseEntity.ok(activeOglasi);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/transakcije/poslane-ponude")
    public ResponseEntity<List<TransakcijaDto>> getReceivedTransakcijeByUserPreferences(UsernamePasswordAuthenticationToken token) {
        try {
            Long korisnikId = getUserIdFromToken(token);
            List<TransakcijaDto> activeTransakcije = transakcijaService.getTransakcijeByKorisnikPonudaIdAndStatus(korisnikId, Transakcija.StatusTransakcije.CEKA_POTVRDU);
            return ResponseEntity.ok(activeTransakcije);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/transakcije/za-potvrditi")
    public ResponseEntity<List<TransakcijaDto>> getStartedTransakcijeByUserPreferences(UsernamePasswordAuthenticationToken token) {
        try {
            Long korisnikId = getUserIdFromToken(token);
            List<TransakcijaDto> activeTransakcije = transakcijaService.getTransakcijeByKorisnikOglasIdAndStatus(korisnikId, Transakcija.StatusTransakcije.CEKA_POTVRDU);
            return ResponseEntity.ok(activeTransakcije);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/transakcije/provedene")
    public ResponseEntity<List<TransakcijaDto>> getInactiveTransakcijeByUserPreferences(UsernamePasswordAuthenticationToken token) {
        try {
            Long korisnikId = getUserIdFromToken(token);
            List<TransakcijaDto> provedenaTransakcije = transakcijaService.getTransakcijeByKorisnikPonudaIdAndStatus(korisnikId, Transakcija.StatusTransakcije.PROVEDENA);
            List<TransakcijaDto> odbijenaTransakcije = transakcijaService.getTransakcijeByKorisnikPonudaIdAndStatus(korisnikId, Transakcija.StatusTransakcije.ODBIJENA);

            List<TransakcijaDto> allInactiveTransakcije = new ArrayList<>();
            allInactiveTransakcije.addAll(provedenaTransakcije);
            allInactiveTransakcije.addAll(odbijenaTransakcije);

            return ResponseEntity.ok(allInactiveTransakcije);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/ulaznice/preuzmi")
    public ResponseEntity<UlaznicaDto> preuzmiUlaznicu(UsernamePasswordAuthenticationToken token, @RequestBody PreuzmiUlaznicuDto preuzmiUlaznicuDto) {
        try {
            Long korisnikId = getUserIdFromToken(token);
            UlaznicaDto updatedUlaznica = preferenceService.changeUlaznicaStatusAndAssignUser(preuzmiUlaznicuDto.getSifraUlaznice(), korisnikId);
            return new ResponseEntity<>(updatedUlaznica, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("korisnici/aktivni-id")
    public ResponseEntity<Long> getCurrentUserId(UsernamePasswordAuthenticationToken token) {
        try {
            Long korisnikId = getUserIdFromToken(token);
            return new ResponseEntity<>(korisnikId, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/korisnici/ulaznice")
    public ResponseEntity<List<UlaznicaDto>> getUlazniceForSignedInUser(UsernamePasswordAuthenticationToken token) {
        try {
            Long korisnikId = getUserIdFromToken(token);
            List<UlaznicaDto> ulaznice = ulaznicaService.getUlazniceByIdKorisnika(korisnikId);
            return new ResponseEntity<>(ulaznice, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/korisnici/update-info")
    public ResponseEntity<KorisnikDto> updateKorisnik(
            @RequestBody KorisnikUpdateDto updateDto,
            UsernamePasswordAuthenticationToken authenticationToken) {
        Long userId = getUserIdFromToken(authenticationToken); // Get the user ID from the token
        KorisnikDto updatedKorisnik = korisnikService.updateKorisnikFields(userId, updateDto);
        return ResponseEntity.ok(updatedKorisnik);
    }






}
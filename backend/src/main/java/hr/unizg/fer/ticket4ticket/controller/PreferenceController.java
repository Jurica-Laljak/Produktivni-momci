package hr.unizg.fer.ticket4ticket.controller;

import hr.unizg.fer.ticket4ticket.dto.*;
import hr.unizg.fer.ticket4ticket.entity.Obavijest;
import hr.unizg.fer.ticket4ticket.entity.Transakcija;
import hr.unizg.fer.ticket4ticket.service.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private ObavijestService obavijestService;



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
            Long korisnikId = getUserIdFromToken(token); // Get the authenticated user's ID
            oglasDto.setKorisnikId(korisnikId); // Set the user ID for the new ad
            OglasDto createdOglas = oglasService.createOglas(oglasDto); // Create the new ad

            // Fetch genres for the newly created ad as DTOs
            List<ZanrDto> zanrovi = oglasService.getZanrsForOglas(createdOglas.getIdOglasa());
            if (zanrovi.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            // Process each genre
            for (ZanrDto zanrDto : zanrovi) {
                Long zanrId = zanrDto.getIdZanra();

                // Fetch users interested in this genre
                List<KorisnikDto> korisnici = korisnikService.getKorisniciByZanr(zanrId);

                // Loop through users and create notifications for those other than the current user
                for (KorisnikDto korisnik : korisnici) {
                    if (!korisnik.getIdKorisnika().equals(korisnikId)) {
                        ObavijestDto obavijestDto = new ObavijestDto();
                        obavijestDto.setOglasId(createdOglas.getIdOglasa()); // Set the created ad's ID
                        obavijestDto.setKorisnikId(korisnik.getIdKorisnika()); // Set the recipient user ID
                        obavijestDto.setObavijestType(Obavijest.ObavijestTip.OGLAS); // Set notification type
                        obavijestDto.setZanrId(zanrId); // Set the genre ID

                        obavijestService.createObavijest(obavijestDto); // Save the notification
                    }
                }
            }

            return new ResponseEntity<>(createdOglas, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/oglasi/filter")
    public ResponseEntity<List<OglasInfoDto>> getOglasiByFilter(@RequestBody OglasFilterDto filterDto) {
        List<OglasInfoDto> filteredOglasi = preferenceService.getOglasiByFilter(filterDto);
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
    public ResponseEntity<List<OglasInfoDto>> getOglasiByGoogleId(UsernamePasswordAuthenticationToken token) {
        try {
            Long korisnikId = getUserIdFromToken(token);
            List<OglasInfoDto> oglasi = oglasService.getOglasiByKorisnikPreference(korisnikId);
            return ResponseEntity.ok(oglasi);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @GetMapping("/oglasi/kupi/{id}")
    @Transactional
    public ResponseEntity<String> BuyUlaznicaByOglasId(UsernamePasswordAuthenticationToken token, @PathVariable Long id) {

        Long korisnikId = getUserIdFromToken(token); // Get the authenticated user's ID

        // Dohvacanje oglasa
        OglasInfoDto oglas = oglasService.getOglasById(id);

        // Dohvacanje svih transakcija koje sadrze ulaznicu kao ponudu ili oglas sa statusom CEKA_POTVRDU
        List<TransakcijaDto> transakcijeOstale = transakcijaService.getTransakcijeWithMatchingUlaznica(oglas.getUlaznicaId(), oglas.getUlaznicaId());
        System.out.println("Found matching transakcije: " + transakcijeOstale.size());

        for (TransakcijaDto transakcijaDto : transakcijeOstale) {
            //Brisanje obavijesti vezanih uz te transakcije
            System.out.println("Deleting obavijest for transakcijaId: " + transakcijaDto.getIdTransakcije());
            obavijestService.getAndDeleteObavijestiByTransakcijaId(transakcijaDto.getIdTransakcije());
        }

        // Brisanje transakcija s ulaznicama
        transakcijaService.deleteTransakcijeWithMatchingUlaznica(oglas.getUlaznicaId(), oglas.getUlaznicaId());

        // Prebacivanje kupljene ulaznice na korisnika
        ulaznicaService.assignUserToUlaznica(oglas.getUlaznicaId(), korisnikId);

        // Brisanje obavijesti vezanih uz oglas
        obavijestService.getAndDeleteObavijestiByOglasId(id);

        // Brisanje oglasa
        oglasService.deleteOglasById(id);



        return ResponseEntity.ok("Ulaznica successfully bought and cleanup completed.");
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
            List<TransakcijaDto> provedenaTransakcijePonuda = transakcijaService.getTransakcijeByKorisnikPonudaIdAndStatus(korisnikId, Transakcija.StatusTransakcije.PROVEDENA);
            List<TransakcijaDto> odbijenaTransakcijePonuda = transakcijaService.getTransakcijeByKorisnikPonudaIdAndStatus(korisnikId, Transakcija.StatusTransakcije.ODBIJENA);

            List<TransakcijaDto> provedenaTransakcijeOglas = transakcijaService.getTransakcijeByKorisnikOglasIdAndStatus(korisnikId, Transakcija.StatusTransakcije.PROVEDENA);
            List<TransakcijaDto> odbijenaTransakcijeOglas = transakcijaService.getTransakcijeByKorisnikOglasIdAndStatus(korisnikId, Transakcija.StatusTransakcije.ODBIJENA);

            List<TransakcijaDto> allInactiveTransakcije = new ArrayList<>();
            allInactiveTransakcije.addAll( provedenaTransakcijePonuda);
            allInactiveTransakcije.addAll(odbijenaTransakcijePonuda);
            allInactiveTransakcije.addAll(provedenaTransakcijeOglas);
            allInactiveTransakcije.addAll(odbijenaTransakcijeOglas);

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

    @GetMapping("/korisnici/ulaznice/without-oglas")
    public ResponseEntity<List<UlaznicaDto>> getUlazniceWithoutOglasForSignedInUser(UsernamePasswordAuthenticationToken token) {
        try {
            Long korisnikId = getUserIdFromToken(token);
            List<UlaznicaDto> ulaznice = ulaznicaService.getUlazniceWithoutOglas(korisnikId);
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

    @Transactional
    @DeleteMapping("/korisnici/izbrisi")
    public ResponseEntity<Void> deleteKorisnik(UsernamePasswordAuthenticationToken token) {
        Long korisnikId = getUserIdFromToken(token);
        obavijestService.deleteObavijestiByKorisnikId(korisnikId);
        preferenceService.resetUlazniceStatusAndClearUser(korisnikId); //this will detach all tickets from user
        transakcijaService.deleteTransakcijeByKorisnikId(korisnikId);
        oglasService.deleteAllOglasiByKorisnikId(korisnikId);
        korisnikService.deleteKorisnikById(korisnikId);
        return ResponseEntity.noContent().build(); // Returns 204 No Content on successful deletion
    }

    @Transactional
    @DeleteMapping("/korisnici/izbrisi/{id}")
    public ResponseEntity<Void> deleteKorisnikByKorisnikId(@PathVariable("id") Long korisnikId) {
        obavijestService.deleteObavijestiByKorisnikId(korisnikId);
        preferenceService.resetUlazniceStatusAndClearUser(korisnikId); //this will detach all tickets from user
        transakcijaService.deleteTransakcijeByKorisnikId(korisnikId);
        oglasService.deleteAllOglasiByKorisnikId(korisnikId);
        korisnikService.deleteKorisnikById(korisnikId);
        return ResponseEntity.noContent().build(); // Returns 204 No Content on successful deletion
    }



    @GetMapping("/obavijesti")
    public ResponseEntity<List<ObavijestInfoDto>> getObavijestiByKorisnikId(UsernamePasswordAuthenticationToken token) {
        try {
            Long korisnikId = getUserIdFromToken(token);
            List<ObavijestInfoDto> obavijesti = obavijestService.getObavijestiByKorisnikId(korisnikId);
            return ResponseEntity.ok(obavijesti);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
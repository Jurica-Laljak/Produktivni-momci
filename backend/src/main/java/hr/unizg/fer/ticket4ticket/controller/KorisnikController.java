package hr.unizg.fer.ticket4ticket.controller;

import hr.unizg.fer.ticket4ticket.dto.KorisnikDto;
import hr.unizg.fer.ticket4ticket.service.KorisnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/korisnici")
public class KorisnikController {

    @Autowired
    private KorisnikService korisnikService;

    // Creates a new Korisnik resource with provided data in KorisnikDto
    @PostMapping
    public ResponseEntity<KorisnikDto> createKorisnik(@RequestBody KorisnikDto korisnikDto){
        KorisnikDto savedKorisnik = korisnikService.createKorisnik(korisnikDto);
        return new ResponseEntity<>(savedKorisnik, HttpStatus.CREATED);
    }

    // Returns the Korisnik by specified ID
    @GetMapping("/{id}")
    public ResponseEntity<KorisnikDto> getKorisnikById(@PathVariable("id") Long korisnikId){
        KorisnikDto korisnikDto= korisnikService.getKorisnikById(korisnikId);
        return ResponseEntity.ok(korisnikDto);
    }

    @GetMapping("/g/{googleId}")
    public ResponseEntity<KorisnikDto> getKorisnikByGoogleId(@PathVariable("googleId") String googleId){
        KorisnikDto korisnikDto = korisnikService.findKorisnikByGoogleId(googleId, new KorisnikDto());
        return ResponseEntity.ok(korisnikDto);
    }

    // Returns all Korisnik resources in the database
    @GetMapping
    public ResponseEntity<List<KorisnikDto>> getAllKorisnici(){
        List<KorisnikDto> korisnici = korisnikService.getAllKorisnici();
        return ResponseEntity.ok(korisnici);
    }
}

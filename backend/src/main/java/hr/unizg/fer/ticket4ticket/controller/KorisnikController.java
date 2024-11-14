package hr.unizg.fer.ticket4ticket.controller;

import hr.unizg.fer.ticket4ticket.dto.KorisnikDto;
import hr.unizg.fer.ticket4ticket.service.KorisnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@RequestMapping("/api/korisnici")
public class KorisnikController {

    @Value("${frontend.url}")
    private String frontendUrl;

    @Autowired
    private KorisnikService korisnikService;

    // Creates a new Korisnik resource with provided data in KorisnikDto
    @PostMapping
    public ResponseEntity<KorisnikDto> createKorisnik(@RequestBody KorisnikDto korisnikDto){
        KorisnikDto savedKorisnik = korisnikService.createKorisnik(korisnikDto);
        return new ResponseEntity<>(savedKorisnik, HttpStatus.CREATED);
    }

    // Returns the Korisnik by specified ID
    @GetMapping("{id}")
    public ResponseEntity<KorisnikDto> getKorisnikById(@PathVariable("id") Long korisnikId){
        KorisnikDto korisnikDto= korisnikService.getKorisnikById(korisnikId);
        return ResponseEntity.ok(korisnikDto);
    }

    // Returns all Korisnik resources in the database
    @GetMapping
    public ResponseEntity<List<KorisnikDto>> getAllKorisnici(){
        List<KorisnikDto> korisnici = korisnikService.getAllKorisnici();
        return ResponseEntity.ok(korisnici);
    }


    // User is redirected to /profileSetupCheck after a successful Google oauth login
    @GetMapping("/profileSetupCheck")
    public RedirectView profile(OAuth2AuthenticationToken token, Model model) {


        // Retrieve user details from OAuth token
        String googleId = token.getPrincipal().getAttribute("sub");
        String name = token.getPrincipal().getAttribute("given_name");
        String surname = token.getPrincipal().getAttribute("family_name");
        String email = token.getPrincipal().getAttribute("email");
        String photo = token.getPrincipal().getAttribute("picture");


        // Check if the Korisnik exists, and if not returns an empty KorisnikDto
        KorisnikDto existingUser = korisnikService.findKorisnikByGoogleId(googleId, new KorisnikDto());

        if (existingUser.getIdKorisnika() != null) {
            // User exists, redirect to UserHome
            return new RedirectView(frontendUrl + "/UserHome");
        }

        // User does not exist, populate KorisnikDto with user information
        KorisnikDto newUserDto = new KorisnikDto();
        newUserDto.setImeKorisnika(name);
        newUserDto.setPrezimeKorisnika(surname);
        newUserDto.setEmailKorisnika(email);
        newUserDto.setFotoKorisnika(photo);
        newUserDto.setGoogleId(googleId);

        // Create a new user using createKorisnik method
        korisnikService.createKorisnik(newUserDto);

        // Redirect to ChooseGenres after successful creation of the new user
        return new RedirectView(frontendUrl + "/ChooseGenres");
    }

}

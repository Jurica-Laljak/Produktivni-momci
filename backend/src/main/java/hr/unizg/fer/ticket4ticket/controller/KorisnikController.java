package hr.unizg.fer.ticket4ticket.controller;

import hr.unizg.fer.ticket4ticket.dto.KorisnikDto;
import hr.unizg.fer.ticket4ticket.service.KorisnikService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/korisnici")
public class KorisnikController {

    @Autowired
    private KorisnikService korisnikService;



    //Add korisnik REST API
    @PostMapping
    public ResponseEntity<KorisnikDto> createKorisnik(@RequestBody KorisnikDto korisnikDto){
        KorisnikDto savedKorisnik = korisnikService.createKorisnik(korisnikDto);
        return new ResponseEntity<>(savedKorisnik, HttpStatus.CREATED);
    }

    //Get korisnik REST API
    @GetMapping("{id}")
    public ResponseEntity<KorisnikDto> getKorisnikById(@PathVariable("id") Long korisnikId){
        KorisnikDto korisnikDto= korisnikService.getKorisnikById(korisnikId);
        return ResponseEntity.ok(korisnikDto);
    }

    //Get all korisnik REST API
    @GetMapping
    public ResponseEntity<List<KorisnikDto>> getAllKorisnici(){
        List<KorisnikDto> korisnici = korisnikService.getAllKorisnici();
        return ResponseEntity.ok(korisnici);
    }

    @GetMapping("/profileSetupCheck")
    public RedirectView profile(OAuth2AuthenticationToken token, Model model) {


        // Retrieve user details from OAuth token
        String googleId = token.getPrincipal().getAttribute("sub");
        String name = token.getPrincipal().getAttribute("given_name");
        String surname = token.getPrincipal().getAttribute("family_name");
        String email = token.getPrincipal().getAttribute("email");
        String photo = token.getPrincipal().getAttribute("picture");

        // Create KorisnikDto with user details
        KorisnikDto korisnikDto = new KorisnikDto();
        // Call the service method to check or create user
        KorisnikDto result = korisnikService.findOrCreateKorisnikByGoogleId(googleId, korisnikDto);

        // Redirect based on whether the user is new or existing
        if (result.getIdKorisnika() == null) {
            // Fill in the new korisnik information
            korisnikDto.setImeKorisnika(name);
            korisnikDto.setPrezimeKorisnika(surname);
            korisnikDto.setEmailKorisnika(email);
            korisnikDto.setFotoKorisnika(photo);
            korisnikDto.setGoogleId(googleId);
            // Redirect to setup if this is a newly created user
            return new RedirectView("http://localhost:5173/ChooseGenres");
        } else {
            // Redirect to search if the user already exists
            return new RedirectView("http://localhost:5173/UserHome");
        }
    }






    @GetMapping("/login")
    public String login() {
        return "loginPage";
    }



}

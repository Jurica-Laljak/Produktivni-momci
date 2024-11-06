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

    @GetMapping("/profile")
    public String profile(OAuth2AuthenticationToken token, Model model) {
        // Retrieve user details from OAuth token
        String googleId = token.getPrincipal().getAttribute("sub");
        String name = token.getPrincipal().getAttribute("given_name");
        String surname = token.getPrincipal().getAttribute("family_name");
        String email = token.getPrincipal().getAttribute("email");
        String photo = token.getPrincipal().getAttribute("picture");


        // Create KorisnikDto with user details
        KorisnikDto korisnikDto = new KorisnikDto();
        korisnikDto.setImeKorisnika(name);
        korisnikDto.setPrezimeKorisnika(surname);
        korisnikDto.setEmailKorisnika(email);
        korisnikDto.setFotoKorisnika(photo);
        korisnikDto.setGoogleId(googleId);

        // Call the service method to check or create user
        korisnikService.findOrCreateKorisnikByGoogleId(googleId, korisnikDto);

        // Add user details to the model for the view
        model.addAttribute("name", name);
        model.addAttribute("email", email);
        model.addAttribute("photo", photo);

        // Return the view name
        return "userProfile";
    }

    @GetMapping("/login")
    public String login() {
        return "loginPage";
    }



}

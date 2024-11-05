package hr.unizg.fer.ticket4ticket.controller;

import hr.unizg.fer.ticket4ticket.dto.IzvodacDto;
import hr.unizg.fer.ticket4ticket.dto.OglasDto;
import hr.unizg.fer.ticket4ticket.service.PreferenceService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/api/preference")
public class PreferenceController {

    @Autowired
    private PreferenceService preferenceService;

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


}

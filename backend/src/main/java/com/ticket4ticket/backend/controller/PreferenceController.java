package com.ticket4ticket.backend.controller;

import com.ticket4ticket.backend.dto.IzvodacDto;
import com.ticket4ticket.backend.dto.OglasDto;
import com.ticket4ticket.backend.service.PreferenceService;
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

package hr.unizg.fer.ticket4ticket.controller;

import hr.unizg.fer.ticket4ticket.dto.ObavijestDto;
import hr.unizg.fer.ticket4ticket.dto.TransakcijaCreateDto;
import hr.unizg.fer.ticket4ticket.dto.TransakcijaDto;
import hr.unizg.fer.ticket4ticket.entity.Obavijest;
import hr.unizg.fer.ticket4ticket.entity.Transakcija;
import hr.unizg.fer.ticket4ticket.service.ObavijestService;
import hr.unizg.fer.ticket4ticket.service.TransakcijaService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transakcije")
public class TransakcijaController {

    @Autowired
    private TransakcijaService transakcijaService;

    @Autowired
    private ObavijestService obavijestService;

    @RolesAllowed("ADMIN")
    @GetMapping
    public ResponseEntity<List<TransakcijaDto>> getTransakcije() {
        List<TransakcijaDto> transakcije = transakcijaService.getAllTransakcije();
        return new ResponseEntity<>(transakcije, HttpStatus.OK);
    }

    // Add delete endpoint
    @DeleteMapping("izbrisi/{id}")
    public ResponseEntity<Void> deleteTransakcija(@PathVariable("id") Long transakcijaId) {
        try {
            transakcijaService.deleteTransakcijaById(transakcijaId);
            return ResponseEntity.noContent().build(); // 204 No Content for successful deletion
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found if the entity does not exist
        }
    }

    @PostMapping("/kreiraj")
    public ResponseEntity<TransakcijaDto> createTransakcija(@RequestBody TransakcijaCreateDto request) {
        TransakcijaDto createdTransakcija = transakcijaService.createTransakcija(
                request.getUlaznicaPonudaId(),
                request.getUlaznicaOglasId(),
                request.getOglasId()
        );

        //create obavijest for sent ponuda
        ObavijestDto obavijestDto = new ObavijestDto();
        obavijestDto.setTransakcijaId(createdTransakcija.getIdTransakcije());
        obavijestDto.setKorisnikId(createdTransakcija.getIdKorisnikOglas());
        obavijestDto.setObavijestType(Obavijest.ObavijestTip.PONUDIO);
        obavijestService.createObavijest(obavijestDto);
        //END obavijest

        return ResponseEntity.ok(createdTransakcija);
    }

    // Existing endpoints...

    @PutMapping("/{id}/prihvati")
    public ResponseEntity<TransakcijaDto> setTransakcijaToProvedena(@PathVariable("id") Long transakcijaId) {
        TransakcijaDto updatedTransakcija = transakcijaService.updateStatusTransakcije(transakcijaId, Transakcija.StatusTransakcije.PROVEDENA);

        //create obavijest for prihvacena ponuda
        ObavijestDto obavijestDto = new ObavijestDto();
        obavijestDto.setTransakcijaId(updatedTransakcija.getIdTransakcije());
        obavijestDto.setKorisnikId(updatedTransakcija.getIdKorisnikPonuda());
        obavijestDto.setObavijestType(Obavijest.ObavijestTip.PRIHVATIO);
        obavijestDto.setOglasId(updatedTransakcija.getIdOglas());
        obavijestService.createObavijest(obavijestDto);
        //END obavijest

        return ResponseEntity.ok(updatedTransakcija);
    }

    @PutMapping("/{id}/odbij")
    public ResponseEntity<TransakcijaDto> setTransakcijaToOdbijena(@PathVariable("id") Long transakcijaId) {
        TransakcijaDto updatedTransakcija = transakcijaService.updateStatusTransakcije(transakcijaId, Transakcija.StatusTransakcije.ODBIJENA);

        //create obavijest for odbijena ponuda
        ObavijestDto obavijestDto = new ObavijestDto();
        obavijestDto.setTransakcijaId(updatedTransakcija.getIdTransakcije());
        obavijestDto.setKorisnikId(updatedTransakcija.getIdKorisnikPonuda());
        obavijestDto.setObavijestType(Obavijest.ObavijestTip.ODBIO);
        obavijestDto.setOglasId(updatedTransakcija.getIdOglas());
        obavijestService.createObavijest(obavijestDto);
        //END obavijest

        return ResponseEntity.ok(updatedTransakcija);
    }


}
package hr.unizg.fer.ticket4ticket.controller;

import hr.unizg.fer.ticket4ticket.dto.TransakcijaCreateDto;
import hr.unizg.fer.ticket4ticket.dto.TransakcijaDto;
import hr.unizg.fer.ticket4ticket.entity.Transakcija;
import hr.unizg.fer.ticket4ticket.service.TransakcijaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transakcije")
public class TransakcijaController {

    private final TransakcijaService transakcijaService;

    public TransakcijaController(TransakcijaService transakcijaService) {
        this.transakcijaService = transakcijaService;
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
        return ResponseEntity.ok(createdTransakcija);
    }

    // Existing endpoints...

    @PutMapping("/{id}/prihvati")
    public ResponseEntity<TransakcijaDto> setTransakcijaToProvedena(@PathVariable("id") Long transakcijaId) {
        TransakcijaDto updatedTransakcija = transakcijaService.updateStatusTransakcije(transakcijaId, Transakcija.StatusTransakcije.PROVEDENA);
        return ResponseEntity.ok(updatedTransakcija);
    }

    @PutMapping("/{id}/odbij")
    public ResponseEntity<TransakcijaDto> setTransakcijaToOdbijena(@PathVariable("id") Long transakcijaId) {
        TransakcijaDto updatedTransakcija = transakcijaService.updateStatusTransakcije(transakcijaId, Transakcija.StatusTransakcije.ODBIJENA);
        return ResponseEntity.ok(updatedTransakcija);
    }


}
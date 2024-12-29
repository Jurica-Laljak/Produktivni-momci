package hr.unizg.fer.ticket4ticket.controller;

import hr.unizg.fer.ticket4ticket.dto.TransakcijaDto;
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

    @PostMapping("/kreiraj")
    public ResponseEntity<TransakcijaDto> createTransakcija(@RequestParam Long ulaznicaPonudaId,
                                                            @RequestParam Long ulaznicaOglasId,
                                                            @RequestParam Long oglasId) {
        TransakcijaDto createdTransakcija = transakcijaService.createTransakcija(ulaznicaPonudaId, ulaznicaOglasId, oglasId);
        return ResponseEntity.ok(createdTransakcija);
    }


}
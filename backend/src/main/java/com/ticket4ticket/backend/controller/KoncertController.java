package com.ticket4ticket.backend.controller;

import com.ticket4ticket.backend.dto.KoncertDto;
import com.ticket4ticket.backend.service.KoncertService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/koncerti")
public class KoncertController {

    private final KoncertService koncertService;

    @PostMapping
    public ResponseEntity<KoncertDto> createKoncert(@RequestBody KoncertDto koncertDto) {
        KoncertDto createdKoncert = koncertService.createKoncert(koncertDto);
        return new ResponseEntity<>(createdKoncert, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<KoncertDto> getKoncertById(@PathVariable Long id) {
        KoncertDto koncertDto = koncertService.getKoncertById(id);
        return new ResponseEntity<>(koncertDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<KoncertDto>> getAllKoncerti() {
        List<KoncertDto> koncerti = koncertService.getAllKoncerti();
        return new ResponseEntity<>(koncerti, HttpStatus.OK);
    }
}

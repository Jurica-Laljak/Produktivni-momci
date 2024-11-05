package com.ticket4ticket.backend.controller;

import com.ticket4ticket.backend.dto.OglasDto;
import com.ticket4ticket.backend.service.OglasService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/oglasi")
public class OglasController {

    private final OglasService oglasService;

    @PostMapping
    public ResponseEntity<OglasDto> createOglas(@RequestBody OglasDto oglasDto) {
        OglasDto createdOglas = oglasService.createOglas(oglasDto);
        return new ResponseEntity<>(createdOglas, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OglasDto> getOglasById(@PathVariable Long id) {
        OglasDto oglasDto = oglasService.getOglasById(id);
        return new ResponseEntity<>(oglasDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<OglasDto>> getAllOglasi() {
        List<OglasDto> oglasi = oglasService.getAllOglasi();
        return new ResponseEntity<>(oglasi, HttpStatus.OK);
    }

}

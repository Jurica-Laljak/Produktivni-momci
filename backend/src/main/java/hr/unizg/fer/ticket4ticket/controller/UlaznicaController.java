package hr.unizg.fer.ticket4ticket.controller;

import hr.unizg.fer.ticket4ticket.dto.UlaznicaDto;
import hr.unizg.fer.ticket4ticket.service.UlaznicaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/ulaznice")
@CrossOrigin
public class UlaznicaController {

    private final UlaznicaService koncertService;

    // Creates a new Ulaznica resource with provided data in UlaznicaDto
    @PostMapping
    public ResponseEntity<UlaznicaDto> createUlaznica(@RequestBody UlaznicaDto ulaznicaDto) {
        UlaznicaDto createdUlaznica = koncertService.createUlaznica(ulaznicaDto);
        return new ResponseEntity<>(createdUlaznica, HttpStatus.CREATED);
    }

    // Returns the Ulaznica by specified ID
    @GetMapping("/{id}")
    public ResponseEntity<UlaznicaDto> getUlaznicaById(@PathVariable Long id) {
        UlaznicaDto ulaznicaDto = koncertService.getUlaznicaById(id);
        return new ResponseEntity<>(ulaznicaDto, HttpStatus.OK);
    }

    // Returns all Ulaznica resources in the database
    @GetMapping
    public ResponseEntity<List<UlaznicaDto>> getAllUlaznice() {
        List<UlaznicaDto> ulaznice = koncertService.getAllUlaznice();
        return new ResponseEntity<>(ulaznice, HttpStatus.OK);
    }

    // Returns the Ulaznica by specified ID
    @GetMapping("/oglas-id/{id}")
    public ResponseEntity<UlaznicaDto> getUlaznicaByOglasId(@PathVariable Long id) {
        UlaznicaDto ulaznicaDto = koncertService.getUlaznicaByOglasId(id);
        return new ResponseEntity<>(ulaznicaDto, HttpStatus.OK);
    }
}

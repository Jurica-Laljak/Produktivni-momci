package hr.unizg.fer.ticket4ticket.controller;

import hr.unizg.fer.ticket4ticket.dto.IzvodacDto;
import hr.unizg.fer.ticket4ticket.dto.OglasDto;
import hr.unizg.fer.ticket4ticket.service.OglasService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/oglasi")
public class OglasController {

    private final OglasService oglasService;

    // Creates a new Oglas resource with provided data in OglasDto
    @PostMapping
    public ResponseEntity<OglasDto> createOglas(@RequestBody OglasDto oglasDto) {
        OglasDto createdOglas = oglasService.createOglas(oglasDto);
        return new ResponseEntity<>(createdOglas, HttpStatus.CREATED);
    }

    // Returns the Oglas by specified ID
    @GetMapping("/{id}")
    public ResponseEntity<OglasDto> getOglasById(@PathVariable Long id) {
        OglasDto oglasDto = oglasService.getOglasById(id);
        return new ResponseEntity<>(oglasDto, HttpStatus.OK);
    }


    // Returns all Oglas resources in the database
    @GetMapping
    public ResponseEntity<List<OglasDto>> getAllOglasi() {
        List<OglasDto> oglasi = oglasService.getAllOglasi();
        return new ResponseEntity<>(oglasi, HttpStatus.OK);
    }


    // Returns a specified amount of random Oglas from the database
    @GetMapping("/list/{broj_random_oglasa}")
    public ResponseEntity<List<OglasDto>> getRandomOglasi(@PathVariable int broj_random_oglasa) {

        List<OglasDto> randomOglasi = oglasService.getRandomOglasi(broj_random_oglasa);
        return new ResponseEntity<>(randomOglasi, HttpStatus.OK);

    }

    // Returns all Izvodac resources that are related to Ulaznica the oglas is referencing (Izvodac resources that are performing at that concert)
    @GetMapping("/{oglas_id}/izvodaci")
    public ResponseEntity<List<IzvodacDto>> getIzvodaciForOglas(@PathVariable("oglas_id") Long oglasId) {

        List<IzvodacDto> izvodaci = oglasService.getIzvodaciForOglas(oglasId); //We ask the oglasService for all the IzvodacDto-s that related to the oglas

        if (izvodaci.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }

        return ResponseEntity.ok(izvodaci);
    }

}

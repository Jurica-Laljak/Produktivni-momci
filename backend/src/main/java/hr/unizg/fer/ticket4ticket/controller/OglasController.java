package hr.unizg.fer.ticket4ticket.controller;

import hr.unizg.fer.ticket4ticket.dto.OglasDto;
import hr.unizg.fer.ticket4ticket.dto.OglasInfoDto;
import hr.unizg.fer.ticket4ticket.dto.TransakcijaDto;
import hr.unizg.fer.ticket4ticket.service.ObavijestService;
import hr.unizg.fer.ticket4ticket.service.OglasService;
import hr.unizg.fer.ticket4ticket.dto.IzvodacDto;
import hr.unizg.fer.ticket4ticket.service.TransakcijaService;
import jakarta.persistence.EntityNotFoundException;
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

    private final ObavijestService obavijestService;

    private final TransakcijaService transakcijaService;

    // Creates a new Oglas resource with provided data in OglasDto
    @PostMapping
    public ResponseEntity<OglasDto> createOglas(@RequestBody OglasDto oglasDto) {
        OglasDto createdOglas = oglasService.createOglas(oglasDto);
        return new ResponseEntity<>(createdOglas, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OglasInfoDto> getOglasById(@PathVariable Long id) {
        OglasInfoDto oglasDto = oglasService.getOglasById(id);
        return new ResponseEntity<>(oglasDto, HttpStatus.OK);
    }


    // Returns all Oglas resources in the database
    @GetMapping
    public ResponseEntity<List<OglasInfoDto>> getAllOglasi() {
        List<OglasInfoDto> oglasi = oglasService.getAllOglasi();
        return new ResponseEntity<>(oglasi, HttpStatus.OK);
    }


    // Returns a specified amount of random Oglas from the database
    @GetMapping("/list/{broj_random_oglasa}")
    public ResponseEntity<List<OglasInfoDto>> getRandomOglasi(@PathVariable int broj_random_oglasa) {

        List<OglasInfoDto> randomOglasi = oglasService.getRandomOglasi(broj_random_oglasa);
        return new ResponseEntity<>(randomOglasi, HttpStatus.OK);

    }



    // Returns a specified amount of random Oglas from the database
    @GetMapping("/list/random-max")
    public ResponseEntity<List<OglasInfoDto>> getRandomOglasiMax() {

        List<OglasInfoDto> randomOglasi = oglasService.getRandomOglasiMax();
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


    // Deletes an Oglas resource by ID
    @DeleteMapping("/izbrisi/{id}")
    public ResponseEntity<Void> deleteOglasById(@PathVariable("id") Long id) {
        try {


            List<TransakcijaDto> transakcije = transakcijaService.getTransakcijeByOglasId(id); //Get all transakcije for oglasId

            for (TransakcijaDto transakcijaDto : transakcije) {
                obavijestService.getAndDeleteObavijestiByTransakcijaId(transakcijaDto.getIdTransakcije());
            } //delete all obavijest connected to the transaction


            obavijestService.getAndDeleteObavijestiByOglasId(id); //delete all obavijest that have the OglasId
            transakcijaService.deleteTransakcijaByOglasId(id);
            oglasService.deleteOglasById(id); //deletes the oglas



            return ResponseEntity.noContent().build(); // Return 204 No Content if successful
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 if not found
        }
    }

}

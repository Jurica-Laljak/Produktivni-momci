package hr.unizg.fer.ticket4ticket.controller;

import hr.unizg.fer.ticket4ticket.dto.ObavijestDto;
import hr.unizg.fer.ticket4ticket.dto.ObavijestInfoDto;
import hr.unizg.fer.ticket4ticket.service.ObavijestService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/obavijesti")
public class ObavijestController {

    private final ObavijestService obavijestService;

    @PostMapping
    public ResponseEntity<ObavijestDto> createObavijest(@RequestBody ObavijestDto obavijestDto) {
        ObavijestDto createdObavijest = obavijestService.createObavijest(obavijestDto);
        return new ResponseEntity<>(createdObavijest, HttpStatus.CREATED);
    }

    @GetMapping("/korisnik/{id}")
    public ResponseEntity<List<ObavijestInfoDto>> getObavijestByKorisnikId(@PathVariable("id") Long id) {
        List<ObavijestInfoDto> obavijesti = obavijestService.getObavijestiByKorisnikId(id);
        return new ResponseEntity<>(obavijesti, HttpStatus.OK);
    }

    @GetMapping("/zanr/{id}")
    public ResponseEntity<List<ObavijestDto>> getObavijestByZanrId(@PathVariable("id") Long id) {
        List<ObavijestDto> obavijesti = obavijestService.getObavijestiByZanrId(id);
        return new ResponseEntity<>(obavijesti, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ObavijestDto>> getAllObavijesti() {
        List<ObavijestDto> obavijesti = obavijestService.getAllObavijesti();
        return new ResponseEntity<>(obavijesti, HttpStatus.OK);
    }

    @GetMapping("/oglas/{id}")
    public ResponseEntity<List<ObavijestDto>> getObavijestByOglasId(@PathVariable("id") Long id) {
        List<ObavijestDto> obavijesti = obavijestService.getObavijestiByOglasId(id);
        return new ResponseEntity<>(obavijesti, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObavijestDto> getObavijestById(@PathVariable("id") Long id) {
        ObavijestDto obavijestDto = obavijestService.getObavijestiById(id);
        return new ResponseEntity<>(obavijestDto, HttpStatus.OK);
    }

    @GetMapping("/izbrisi/{id}")
    public ResponseEntity<Boolean> deleteObavijestById(@PathVariable("id") Long id) {
        Boolean deleted = obavijestService.removeObavijest(id);

        if(!deleted)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }
}

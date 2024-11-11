package  hr.unizg.fer.ticket4ticket.controller;

import hr.unizg.fer.ticket4ticket.dto.ZanrDto;
import hr.unizg.fer.ticket4ticket.service.ZanrService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/zanrovi")
public class ZanrController {

    private final ZanrService zanrService;


    // Returns a single Zanr resource by its ID
    @GetMapping("/{id}")
    public ResponseEntity<ZanrDto> getZanrById(@PathVariable("id") Long zanrId) {
        ZanrDto zanrDto = zanrService.getZanrById(zanrId);
        return new ResponseEntity<>(zanrDto, HttpStatus.OK);
    }

    // Returns all Zanrovi resources in the database
    @GetMapping
    public ResponseEntity<List<ZanrDto>> getAllZanrovi() {
        List<ZanrDto> zanrovi = zanrService.getAllZanrovi();
        return new ResponseEntity<>(zanrovi, HttpStatus.OK);
    }
}

package hr.unizg.fer.ticket4ticket.controller;

import hr.unizg.fer.ticket4ticket.dto.IzvodacDto;
import hr.unizg.fer.ticket4ticket.service.IzvodacService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/izvodaci")
public class IzvodacController {

    private final IzvodacService izvodacService;

    @PostMapping
    public ResponseEntity<IzvodacDto> createIzvodac(@RequestBody IzvodacDto izvodacDto) {
        IzvodacDto createdIzvodac = izvodacService.createIzvodac(izvodacDto);
        return new ResponseEntity<>(createdIzvodac, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IzvodacDto> getIzvodacById(@PathVariable Long id) {
        IzvodacDto izvodacDto = izvodacService.getIzvodacById(id);
        return new ResponseEntity<>(izvodacDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<IzvodacDto>> getAllIzvodaci() {
        List<IzvodacDto> izvodaci = izvodacService.getAllIzvodaci();
        return new ResponseEntity<>(izvodaci, HttpStatus.OK);
    }


}
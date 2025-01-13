package hr.unizg.fer.ticket4ticket.controller;

import hr.unizg.fer.ticket4ticket.dto.ObavijestDto;
import hr.unizg.fer.ticket4ticket.dto.OglasDto;
import hr.unizg.fer.ticket4ticket.dto.TransakcijaCreateDto;
import hr.unizg.fer.ticket4ticket.dto.TransakcijaDto;
import hr.unizg.fer.ticket4ticket.entity.Obavijest;
import hr.unizg.fer.ticket4ticket.entity.Transakcija;
import hr.unizg.fer.ticket4ticket.service.ObavijestService;
import hr.unizg.fer.ticket4ticket.service.OglasService;
import hr.unizg.fer.ticket4ticket.service.TransakcijaService;
import hr.unizg.fer.ticket4ticket.service.UlaznicaService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transakcije")
public class TransakcijaController {

    @Autowired
    private TransakcijaService transakcijaService;

    @Autowired
    private ObavijestService obavijestService;

    @Autowired
    private UlaznicaService ulaznicaService;

    @Autowired
    private OglasService oglasService;

    @RolesAllowed("ADMIN")
    @GetMapping
    public ResponseEntity<List<TransakcijaDto>> getTransakcije() {
        List<TransakcijaDto> transakcije = transakcijaService.getAllTransakcije();
        return new ResponseEntity<>(transakcije, HttpStatus.OK);
    }

    // Add delete endpoint
    @DeleteMapping("izbrisi/{id}")
    public ResponseEntity<Void> deleteTransakcija(@PathVariable("id") Long transakcijaId) {
        try {
            transakcijaService.deleteTransakcijaById(transakcijaId);
            obavijestService.getAndDeleteObavijestiByTransakcijaId(transakcijaId);
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
        //create obavijest for sent ponuda
        ObavijestDto obavijestDto = new ObavijestDto();
        obavijestDto.setTransakcijaId(createdTransakcija.getIdTransakcije());
        obavijestDto.setKorisnikId(createdTransakcija.getIdKorisnikOglas());
        obavijestDto.setObavijestType(Obavijest.ObavijestTip.PONUDIO);
        obavijestService.createObavijest(obavijestDto);
        //END obavijest

        return ResponseEntity.ok(createdTransakcija);
    }

    @Transactional
    @PutMapping("/{id}/prihvati")
    public ResponseEntity<TransakcijaDto> setTransakcijaToProvedena(@PathVariable("id") Long transakcijaId) {

        // Set the transakcija as PROVEDENA
        System.out.println("Updating transakcija to PROVEDENA for transakcijaId: " + transakcijaId);
        TransakcijaDto updatedTransakcija = transakcijaService.updateStatusTransakcije(transakcijaId, Transakcija.StatusTransakcije.PROVEDENA);
        System.out.println("Updated Transakcija: " + updatedTransakcija);

        // Get the transakcija info
        TransakcijaDto transakcija = transakcijaService.getTransakcijaById(transakcijaId);
        System.out.println("Fetched Transakcija details: " + transakcija);

        // Trade the ulaznice between users
        Long idPonudaUlaznica = transakcija.getIdUlaznicaPonuda();
        Long idOglasUlaznica = transakcija.getIdUlaznicaOglas();
        Long idKorisnikPonuda = transakcija.getIdKorisnikPonuda();
        Long idKorisnikOglas = transakcija.getIdKorisnikOglas();

        System.out.println("Exchanging ulaznice between users:");
        System.out.println("idPonudaUlaznica: " + idPonudaUlaznica);
        System.out.println("idOglasUlaznica: " + idOglasUlaznica);
        System.out.println("idKorisnikPonuda: " + idKorisnikPonuda);
        System.out.println("idKorisnikOglas: " + idKorisnikOglas);

        // Switch the ulaznica between users
        System.out.println("Assigning user to ulaznica (Ponuda -> Oglas)");
        ulaznicaService.assignUserToUlaznica(idPonudaUlaznica, idKorisnikOglas);
        System.out.println("Assigning user to ulaznica (Oglas -> Ponuda)");
        ulaznicaService.assignUserToUlaznica(idOglasUlaznica, idKorisnikPonuda);

        // Get the transakcije with matching ulaznicas with status CEKA_POTVRDU
        List<TransakcijaDto> transakcije = transakcijaService.getTransakcijeWithMatchingUlaznica(idOglasUlaznica, idPonudaUlaznica);
        System.out.println("Found matching transakcije: " + transakcije.size());


        //This will delete all obavijesti with the provedena transakcijaId
        obavijestService.getAndDeleteObavijestiByTransakcijaId(updatedTransakcija.getIdTransakcije());

        for (TransakcijaDto transakcijaDto : transakcije) {
            System.out.println("Deleting obavijest for transakcijaId: " + transakcijaDto.getIdTransakcije());
            obavijestService.getAndDeleteObavijestiByTransakcijaId(transakcijaDto.getIdTransakcije());
        }

        // Delete matching transakcije
        System.out.println("Deleting transakcije with matching ulaznica");
        transakcijaService.deleteTransakcijeWithMatchingUlaznica(idOglasUlaznica, idPonudaUlaznica);

        // Remove the Oglas from Transakcija (set oglasId to NULL)
        System.out.println("Removing oglasId from transakcija: " + transakcija.getIdOglas());
        transakcijaService.removeOglasFromTransakcijaByOglasId(transakcija.getIdOglas());

        // Fetch Oglas ids for deletion
        Long oglasOglasId = oglasService.getOglasByUlaznicaId(idOglasUlaznica).getIdOglasa();
        Long oglasPonudaId = oglasService.getOglasByUlaznicaId(idPonudaUlaznica).getIdOglasa();
        System.out.println("Deleting oglas for Oglas IDs: " + oglasOglasId + ", " + oglasPonudaId);

        // Delete obavijesti for oglas
        System.out.println("Deleting obavijesti for Oglas ID: " + oglasOglasId);
        obavijestService.getAndDeleteObavijestiByOglasId(oglasOglasId);
        System.out.println("Deleting obavijesti for Oglas ID: " + oglasPonudaId);
        obavijestService.getAndDeleteObavijestiByOglasId(oglasPonudaId);

        // Delete Oglas
        System.out.println("Deleting Oglas with ID: " + oglasOglasId);
        oglasService.deleteOglasById(oglasOglasId);
        System.out.println("Deleting Oglas with ID: " + oglasPonudaId);
        oglasService.deleteOglasById(oglasPonudaId);




        // Create obavijest for prihvacena ponuda
        System.out.println("Creating obavijest for prihvacena ponuda");
        ObavijestDto obavijestDto = new ObavijestDto();
        obavijestDto.setTransakcijaId(updatedTransakcija.getIdTransakcije());
        obavijestDto.setKorisnikId(updatedTransakcija.getIdKorisnikPonuda());
        obavijestDto.setObavijestType(Obavijest.ObavijestTip.PRIHVATIO);
        obavijestDto.setOglasId(null);
        obavijestService.createObavijest(obavijestDto);

        System.out.println("Obavijest created successfully for transakcijaId: " + updatedTransakcija.getIdTransakcije());

        return ResponseEntity.ok(updatedTransakcija);
    }


    @Transactional
    @PutMapping("/{id}/odbij")
    public ResponseEntity<TransakcijaDto> setTransakcijaToOdbijena(@PathVariable("id") Long transakcijaId) {

        //delete obavijest of transakcija
        obavijestService.getAndDeleteObavijestiByTransakcijaId(transakcijaId);

        TransakcijaDto updatedTransakcija = transakcijaService.updateStatusTransakcije(transakcijaId, Transakcija.StatusTransakcije.ODBIJENA);

        //create obavijest for odbijena ponuda
        ObavijestDto obavijestDto = new ObavijestDto();
        obavijestDto.setTransakcijaId(updatedTransakcija.getIdTransakcije());
        obavijestDto.setKorisnikId(updatedTransakcija.getIdKorisnikPonuda());
        obavijestDto.setObavijestType(Obavijest.ObavijestTip.ODBIO);
        obavijestDto.setOglasId(updatedTransakcija.getIdOglas());
        obavijestService.createObavijest(obavijestDto);
        //END obavijest

        return ResponseEntity.ok(updatedTransakcija);
    }


}
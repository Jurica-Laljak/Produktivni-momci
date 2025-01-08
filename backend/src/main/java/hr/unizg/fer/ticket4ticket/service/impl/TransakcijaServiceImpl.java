package hr.unizg.fer.ticket4ticket.service.impl;

import hr.unizg.fer.ticket4ticket.dto.TransakcijaDto;
import hr.unizg.fer.ticket4ticket.entity.Korisnik;
import hr.unizg.fer.ticket4ticket.entity.Oglas;
import hr.unizg.fer.ticket4ticket.entity.Transakcija;
import hr.unizg.fer.ticket4ticket.entity.Ulaznica;
import hr.unizg.fer.ticket4ticket.mapper.TransakcijaMapper;
import hr.unizg.fer.ticket4ticket.repository.OglasRepository;
import hr.unizg.fer.ticket4ticket.repository.TransakcijaRepository;
import hr.unizg.fer.ticket4ticket.repository.UlaznicaRepository;
import hr.unizg.fer.ticket4ticket.service.TransakcijaService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransakcijaServiceImpl implements TransakcijaService {

    private final TransakcijaRepository transakcijaRepository;
    private final UlaznicaRepository ulaznicaRepository;
    private final OglasRepository oglasRepository;

    public TransakcijaServiceImpl(TransakcijaRepository transakcijaRepository, UlaznicaRepository ulaznicaRepository, OglasRepository oglasRepository) {
        this.transakcijaRepository = transakcijaRepository;
        this.ulaznicaRepository = ulaznicaRepository;
        this.oglasRepository = oglasRepository;
    }

    @Override
    public TransakcijaDto createTransakcija(Long ulaznicaPonudaId, Long ulaznicaOglasId, Long oglasId) {
        // Fetch the Ulaznica entities based on the provided IDs
        Ulaznica ulaznicaPonuda = ulaznicaRepository.findById(ulaznicaPonudaId)
                .orElseThrow(() -> new IllegalArgumentException("Ulaznica Ponuda not found"));
        Ulaznica ulaznicaOglas = ulaznicaRepository.findById(ulaznicaOglasId)
                .orElseThrow(() -> new IllegalArgumentException("Ulaznica Oglas not found"));

        // Fetch the Oglas entity based on the provided ID
        Oglas oglas = oglasRepository.findById(oglasId)
                .orElseThrow(() -> new IllegalArgumentException("Oglas not found"));

        // Fetch the Korisnik objects from Ulaznica entities
        Korisnik korisnikPonuda = ulaznicaPonuda.getKorisnik();
        Korisnik korisnikOglas = ulaznicaOglas.getKorisnik();

        if (korisnikPonuda == null || korisnikOglas == null) {
            throw new IllegalStateException("Korisnik not associated with Ulaznica");
        }

        // Create a new Transakcija instance and set its properties
        Transakcija transakcija = new Transakcija();
        transakcija.setUlaznicaPonuda(ulaznicaPonuda);
        transakcija.setUlaznicaOglas(ulaznicaOglas);
        transakcija.setKorisnikPonuda(korisnikPonuda); // Set korisnikPonuda
        transakcija.setKorisnikOglas(korisnikOglas);   // Set korisnikOglas
        transakcija.setOglas(oglas);
        transakcija.setStatusTransakcije(Transakcija.StatusTransakcije.CEKA_POTVRDU);
        transakcija.setDatumTransakcije(LocalDateTime.now());

        // Save the Transakcija entity
        transakcija = transakcijaRepository.save(transakcija);

        // Return the DTO containing the created Transakcija's data
        return new TransakcijaDto(
                transakcija.getIdTransakcije(),
                transakcija.getUlaznicaPonuda().getIdUlaznice(),
                transakcija.getUlaznicaOglas().getIdUlaznice(),
                korisnikPonuda.getIdKorisnika(), // Fetch directly from korisnikPonuda
                korisnikOglas.getIdKorisnika(), // Fetch directly from korisnikOglas
                transakcija.getStatusTransakcije().name(),
                transakcija.getOglas().getIdOglasa(),
                transakcija.getDatumTransakcije()
        );
    }


    @Override
    public void deleteTransakcijaById(Long transakcijaId) {
        // Check if the Transakcija exists before attempting to delete
        Transakcija transakcija = transakcijaRepository.findById(transakcijaId)
                .orElseThrow(() -> new IllegalArgumentException("Transakcija not found"));

        // Delete the Transakcija entity
        transakcijaRepository.delete(transakcija);
    }

    @Override
    public List<TransakcijaDto> getTransakcijeByKorisnikPonudaIdAndStatus(Long korisnikPonudaId, Transakcija.StatusTransakcije status) {
        // Fetch all Transakcija entities by korisnikPonudaId and status
        List<Transakcija> transakcije = transakcijaRepository.findByKorisnikPonuda_IdKorisnikaAndStatusTransakcije(korisnikPonudaId, status);

        // Map to DTOs and return the list
        return transakcije.stream()
                .map(TransakcijaMapper::mapToTransakcijaDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransakcijaDto> getTransakcijeByKorisnikOglasIdAndStatus(Long korisnikOglasId, Transakcija.StatusTransakcije status) {
        // Fetch all Transakcija entities by korisnikPonudaId and status
        List<Transakcija> transakcije = transakcijaRepository.findByKorisnikOglas_IdKorisnikaAndStatusTransakcije(korisnikOglasId, status);

        // Map to DTOs and return the list
        return transakcije.stream()
                .map(TransakcijaMapper::mapToTransakcijaDto)
                .collect(Collectors.toList());
    }

    @Override
    public TransakcijaDto updateStatusTransakcije(Long transakcijaId, Transakcija.StatusTransakcije newStatus) {
        // Fetch the Transakcija entity
        Transakcija transakcija = transakcijaRepository.findById(transakcijaId)
                .orElseThrow(() -> new IllegalArgumentException("Transakcija not found"));

        // Update the status
        transakcija.setStatusTransakcije(newStatus);

        // Save the updated Transakcija
        Transakcija updatedTransakcija = transakcijaRepository.save(transakcija);

        // Convert to DTO and return
        return new TransakcijaDto(
                updatedTransakcija.getIdTransakcije(),
                updatedTransakcija.getUlaznicaPonuda().getIdUlaznice(),
                updatedTransakcija.getUlaznicaOglas().getIdUlaznice(),
                updatedTransakcija.getKorisnikPonuda().getIdKorisnika(),
                updatedTransakcija.getKorisnikOglas().getIdKorisnika(),
                updatedTransakcija.getStatusTransakcije().name(),
                updatedTransakcija.getOglas().getIdOglasa(),
                updatedTransakcija.getDatumTransakcije()
        );
    }
}

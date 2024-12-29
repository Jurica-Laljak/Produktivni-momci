package hr.unizg.fer.ticket4ticket.service.impl;

import hr.unizg.fer.ticket4ticket.dto.TransakcijaDto;
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

        // Create a new Transakcija instance and set its properties
        Transakcija transakcija = new Transakcija();
        transakcija.setUlaznicaPonuda(ulaznicaPonuda);
        transakcija.setUlaznicaOglas(ulaznicaOglas);
        transakcija.setOglas(oglas);
        transakcija.setStatusTransakcije(Transakcija.StatusTransakcije.CEKA_POTVRDU);
        transakcija.setDatumTransakcije(LocalDateTime.now());

        // Save the Transakcija entity
        transakcija = transakcijaRepository.save(transakcija);

        // Fetch the Korisnik IDs from the Ulaznica entities
        Long idKorisnikPonuda = (ulaznicaPonuda.getKorisnik() != null) ? ulaznicaPonuda.getKorisnik().getIdKorisnika() : null;
        Long idKorisnikOglas = (ulaznicaOglas.getKorisnik() != null) ? ulaznicaOglas.getKorisnik().getIdKorisnika() : null;

        // Return the DTO containing the created Transakcija's data
        return new TransakcijaDto(
                transakcija.getIdTransakcije(),
                transakcija.getUlaznicaPonuda().getIdUlaznice(),
                transakcija.getUlaznicaOglas().getIdUlaznice(),
                idKorisnikPonuda,
                idKorisnikOglas,
                transakcija.getStatusTransakcije().name(),
                transakcija.getOglas().getIdOglasa(),
                transakcija.getDatumTransakcije()

        );
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
}

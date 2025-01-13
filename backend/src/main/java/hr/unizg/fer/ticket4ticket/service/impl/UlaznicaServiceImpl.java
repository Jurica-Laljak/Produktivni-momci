package hr.unizg.fer.ticket4ticket.service.impl;

import hr.unizg.fer.ticket4ticket.entity.Korisnik;
import hr.unizg.fer.ticket4ticket.entity.Oglas;
import hr.unizg.fer.ticket4ticket.entity.Ulaznica;
import hr.unizg.fer.ticket4ticket.dto.UlaznicaDto;
import hr.unizg.fer.ticket4ticket.exception.ResourceNotFoundException;
import hr.unizg.fer.ticket4ticket.mapper.UlaznicaMapper;
import hr.unizg.fer.ticket4ticket.repository.OglasRepository;
import hr.unizg.fer.ticket4ticket.repository.UlaznicaRepository;
import hr.unizg.fer.ticket4ticket.repository.KorisnikRepository;
import hr.unizg.fer.ticket4ticket.service.UlaznicaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UlaznicaServiceImpl implements UlaznicaService {

    @Autowired
    private UlaznicaRepository ulaznicaRepository;

    @Autowired
    private final OglasRepository oglasRepository;

    @Autowired
    private final KorisnikRepository korisnikRepository;

    @Override
    public UlaznicaDto getUlaznicaByOglasId(Long oglasId) {
        // Fetch the Oglas by its ID
        Oglas oglas = oglasRepository.findById(oglasId)
                .orElseThrow(() -> new ResourceNotFoundException("Oglas with ID " + oglasId + " not found."));

        // Get the associated Ulaznica
        Ulaznica ulaznica = oglas.getUlaznica();

        if (ulaznica == null) {
            throw new ResourceNotFoundException("No Ulaznica associated with Oglas ID " + oglasId);
        }

        // Map the Ulaznica to UlaznicaDto
        return UlaznicaMapper.mapToUlaznicaDto(ulaznica);
    }

    public UlaznicaDto assignUserToUlaznica(Long idUlaznice, Long korisnikId) {
        // Find the Ulaznica by its idUlaznice
        Ulaznica ulaznica = ulaznicaRepository.findById(idUlaznice)
                .orElseThrow(() -> new RuntimeException("Ulaznica not found"));

        // Find the Korisnik by ID
        Korisnik korisnik = korisnikRepository.findById(korisnikId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Associate the new user with the Ulaznica
        ulaznica.setKorisnik(korisnik);

        // Save the updated Ulaznica entity
        Ulaznica updatedUlaznica = ulaznicaRepository.save(ulaznica);

        // Convert the updated Ulaznica entity to UlaznicaDto using the mapper
        return UlaznicaMapper.mapToUlaznicaDto(updatedUlaznica);
    }

    @Override
    public UlaznicaDto createUlaznica(UlaznicaDto ulaznicaDto) {
        // Convert DTO to entity
        Ulaznica ulaznica = UlaznicaMapper.mapToUlaznica(ulaznicaDto);

        // Save entity to the repository
        Ulaznica savedUlaznica = ulaznicaRepository.save(ulaznica);

        // Convert the saved entity back to DTO
        return UlaznicaMapper.mapToUlaznicaDto(savedUlaznica);
    }

    @Override
    public UlaznicaDto getUlaznicaById(Long ulaznicaId) {
        // Retrieve the ulaznica by ID, or throw an exception if not found
        Ulaznica ulaznica =ulaznicaRepository.findById(ulaznicaId)
                .orElseThrow(() -> new ResourceNotFoundException("Ulaznica with ID " + ulaznicaId + " does not exist."));

        // Convert entity to DTO
        return UlaznicaMapper.mapToUlaznicaDto(ulaznica);
    }

    @Override
    public List<UlaznicaDto> getAllUlaznice() {
        // Retrieve all concerts
        List<Ulaznica> ulaznice = ulaznicaRepository.findAll();

        // Convert list of entities to list of DTOs
        return ulaznice.stream()
                .map(UlaznicaMapper::mapToUlaznicaDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UlaznicaDto> getUlazniceByIdKorisnika(Long idKorisnika) {
        // Fetch all Ulaznica by idKorisnika
        List<Ulaznica> ulaznice = ulaznicaRepository.findAllByKorisnik_IdKorisnika(idKorisnika);

        // Convert the list of entities to a list of DTOs
        return ulaznice.stream()
                .map(UlaznicaMapper::mapToUlaznicaDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UlaznicaDto> getUlazniceWithoutOglas(Long idKorisnika) {
        // Fetch all Ulaznice by idKorisnika
        List<UlaznicaDto> ulaznice = getUlazniceByIdKorisnika(idKorisnika);

        // Filter out the Ulaznice that do not have an associated Oglas
        List<UlaznicaDto> ulazniceWithoutOglas = ulaznice.stream()
                .filter(ulaznicaDto -> ulaznicaDto.getOglasiIds() == null || ulaznicaDto.getOglasiIds().isEmpty())
                .collect(Collectors.toList());

        return ulazniceWithoutOglas;
    }
}

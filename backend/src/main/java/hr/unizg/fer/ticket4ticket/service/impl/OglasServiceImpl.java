package hr.unizg.fer.ticket4ticket.service.impl;

import hr.unizg.fer.ticket4ticket.dto.OglasDto;
import hr.unizg.fer.ticket4ticket.entity.Oglas;
import hr.unizg.fer.ticket4ticket.mapper.OglasMapper;
import hr.unizg.fer.ticket4ticket.repository.KoncertRepository;
import hr.unizg.fer.ticket4ticket.repository.OglasRepository;
import hr.unizg.fer.ticket4ticket.service.OglasService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import hr.unizg.fer.ticket4ticket.exception.ResourceNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OglasServiceImpl implements OglasService {

    private OglasRepository oglasRepository;


    private final KoncertRepository koncertRepository; // Dependency injection of Koncert repository


    @Override
    public OglasDto createOglas(OglasDto oglasDto) {
        // Convert DTO to entity
        Oglas oglas = OglasMapper.mapToOglas(oglasDto);

        // Save entity to the repository
        Oglas savedOglas = oglasRepository.save(oglas);

        // Convert the saved entity back to DTO
        return OglasMapper.mapToOglasDto(savedOglas);
    }

    @Override
    public OglasDto getOglasById(Long oglasId) {
        // Retrieve the ad by ID, or throw an exception if not found
        Oglas oglas = oglasRepository.findById(oglasId)
                .orElseThrow(() -> new ResourceNotFoundException("Oglas with ID " + oglasId + " does not exist."));

        // Convert entity to DTO
        return OglasMapper.mapToOglasDto(oglas);
    }

    @Override
    public List<OglasDto> getAllOglasi() {
        // Retrieve all ads
        List<Oglas> oglasi = oglasRepository.findAll();

        // Convert list of entities to list of DTOs
        return oglasi.stream()
                .map(OglasMapper::mapToOglasDto)
                .collect(Collectors.toList());
    }


    public List<OglasDto> getOglasiByIzvodacId(Long izvodacId) {
        // Find all oglasi associated with the specified izvodacId
        Set<Oglas> oglasi = oglasRepository.findByIzvodacId(izvodacId);

        // Map Oglas entities to OglasDto using the mapper
        return oglasi.stream()
                .map(OglasMapper::mapToOglasDto) // Use the mapper to convert entities to DTOs
                .collect(Collectors.toList());
    }


}

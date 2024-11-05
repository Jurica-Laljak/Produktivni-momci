package com.ticket4ticket.backend.service.impl;

import com.ticket4ticket.backend.dto.OglasDto;
import com.ticket4ticket.backend.entity.Oglas;
import com.ticket4ticket.backend.mapper.OglasMapper;
import com.ticket4ticket.backend.repository.KoncertRepository;
import com.ticket4ticket.backend.repository.OglasRepository;
import com.ticket4ticket.backend.service.OglasService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.ticket4ticket.backend.exception.ResourceNotFoundException;
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

package hr.unizg.fer.ticket4ticket.service.impl;
import hr.unizg.fer.ticket4ticket.entity.Ulaznica;
import hr.unizg.fer.ticket4ticket.dto.UlaznicaDto;
import hr.unizg.fer.ticket4ticket.exception.ResourceNotFoundException;
import hr.unizg.fer.ticket4ticket.mapper.UlaznicaMapper;
import hr.unizg.fer.ticket4ticket.repository.UlaznicaRepository;
import hr.unizg.fer.ticket4ticket.service.UlaznicaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UlaznicaServiceImpl implements UlaznicaService {

    private final UlaznicaRepository ulaznicaRepository;

    @Override
    public UlaznicaDto createUlaznica(UlaznicaDto ulaznicaDto) {
        // Convert DTO to entity
        Ulaznica koncert = UlaznicaMapper.mapToUlaznica(ulaznicaDto);

        // Save entity to the repository
        Ulaznica savedUlaznica = ulaznicaRepository.save(koncert);

        // Convert the saved entity back to DTO
        return UlaznicaMapper.mapToUlaznicaDto(savedUlaznica);
    }

    @Override
    public UlaznicaDto getUlaznicaById(Long ulaznicaId) {
        // Retrieve the concert by ID, or throw an exception if not found
        Ulaznica koncert =ulaznicaRepository.findById(ulaznicaId)
                .orElseThrow(() -> new ResourceNotFoundException("Ulaznica with ID " + ulaznicaId + " does not exist."));

        // Convert entity to DTO
        return UlaznicaMapper.mapToUlaznicaDto(koncert);
    }

    @Override
    public List<UlaznicaDto> getAllUlaznice() {
        // Retrieve all concerts
        List<Ulaznica> koncerti = ulaznicaRepository.findAll();

        // Convert list of entities to list of DTOs
        return koncerti.stream()
                .map(UlaznicaMapper::mapToUlaznicaDto)
                .collect(Collectors.toList());
    }
}

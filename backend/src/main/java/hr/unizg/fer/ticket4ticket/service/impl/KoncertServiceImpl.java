package hr.unizg.fer.ticket4ticket.service.impl;
import hr.unizg.fer.ticket4ticket.entity.Koncert;
import hr.unizg.fer.ticket4ticket.dto.KoncertDto;
import hr.unizg.fer.ticket4ticket.exception.ResourceNotFoundException;
import hr.unizg.fer.ticket4ticket.mapper.KoncertMapper;
import hr.unizg.fer.ticket4ticket.repository.KoncertRepository;
import hr.unizg.fer.ticket4ticket.service.KoncertService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class KoncertServiceImpl implements KoncertService {

    private final KoncertRepository koncertRepository;

    @Override
    public KoncertDto createKoncert(KoncertDto koncertDto) {
        // Convert DTO to entity
        Koncert koncert = KoncertMapper.mapToKoncert(koncertDto);

        // Save entity to the repository
        Koncert savedKoncert = koncertRepository.save(koncert);

        // Convert the saved entity back to DTO
        return KoncertMapper.mapToKoncertDto(savedKoncert);
    }

    @Override
    public KoncertDto getKoncertById(Long koncertId) {
        // Retrieve the concert by ID, or throw an exception if not found
        Koncert koncert = koncertRepository.findById(koncertId)
                .orElseThrow(() -> new ResourceNotFoundException("Koncert with ID " + koncertId + " does not exist."));

        // Convert entity to DTO
        return KoncertMapper.mapToKoncertDto(koncert);
    }

    @Override
    public List<KoncertDto> getAllKoncerti() {
        // Retrieve all concerts
        List<Koncert> koncerti = koncertRepository.findAll();

        // Convert list of entities to list of DTOs
        return koncerti.stream()
                .map(KoncertMapper::mapToKoncertDto)
                .collect(Collectors.toList());
    }
}

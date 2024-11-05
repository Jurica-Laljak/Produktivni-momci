package com.ticket4ticket.backend.service.impl;
import com.ticket4ticket.backend.entity.Koncert;
import com.ticket4ticket.backend.dto.KoncertDto;
import com.ticket4ticket.backend.exception.ResourceNotFoundException;
import com.ticket4ticket.backend.mapper.KoncertMapper;
import com.ticket4ticket.backend.repository.KoncertRepository;
import com.ticket4ticket.backend.service.KoncertService;
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

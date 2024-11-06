package hr.unizg.fer.ticket4ticket.service.impl;

import hr.unizg.fer.ticket4ticket.dto.ZanrDto;
import hr.unizg.fer.ticket4ticket.mapper.ZanrMapper;
import hr.unizg.fer.ticket4ticket.repository.ZanrRepository;
import hr.unizg.fer.ticket4ticket.service.ZanrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ZanrServiceImpl implements ZanrService {

    @Autowired
    private ZanrRepository zanrRepository;

    @Override
    public List<ZanrDto> getAllZanrovi() {
        // Retrieve all Zanr entities and map them to ZanrDto
        return zanrRepository.findAll()
                .stream()
                .map(ZanrMapper::mapToZanrDto)
                .collect(Collectors.toList());
    }

    @Override
    public Set<ZanrDto> getZanroviByIds(Set<Long> zanrIds) {
        // Retrieve Zanr entities by IDs and map them to ZanrDto
        return zanrRepository.findAllById(zanrIds)
                .stream()
                .map(ZanrMapper::mapToZanrDto)
                .collect(Collectors.toSet());
    }
}

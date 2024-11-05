package com.ticket4ticket.backend.service.impl;


import com.ticket4ticket.backend.dto.IzvodacDto;
import com.ticket4ticket.backend.entity.Izvodac;
import com.ticket4ticket.backend.exception.ResourceNotFoundException;
import com.ticket4ticket.backend.mapper.IzvodacMapper;
import com.ticket4ticket.backend.repository.IzvodacRepository;
import com.ticket4ticket.backend.service.IzvodacService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class IzvodacServiceImpl implements IzvodacService {

    private IzvodacRepository izvodacRepository;

    @Override
    public IzvodacDto createIzvodac(IzvodacDto izvodacDto) {

        Izvodac izvodac = IzvodacMapper.mapToIzvodac(izvodacDto);
        Izvodac savedIzvodac = izvodacRepository.save(izvodac);

        return IzvodacMapper.mapToIzvodacDto(savedIzvodac);
    }

    @Override
    public IzvodacDto getIzvodacById(Long izvodacId) {
        Izvodac izvodac = izvodacRepository.findById(izvodacId)
                .orElseThrow(()-> new ResourceNotFoundException("Korisnik sa tim id-om ne postoji: " + izvodacId));

        return IzvodacMapper.mapToIzvodacDto(izvodac);
    }

    @Override
    public List<IzvodacDto> getAllIzvodaci() {
        List<Izvodac> izvodaci = izvodacRepository.findAll();


        return izvodaci.stream().map((izvodac) -> IzvodacMapper.mapToIzvodacDto(izvodac))
                .collect(Collectors.toList());
    }

    @Override
    public List<IzvodacDto> getIzvodaciByZanr(String zanrIzvodaca) {
        List<Izvodac> izvodaci = izvodacRepository.findByZanrIzvodaca(zanrIzvodaca);


        return izvodaci.stream().map((izvodac) -> IzvodacMapper.mapToIzvodacDto(izvodac))
                .collect(Collectors.toList());
    }

    public Set<IzvodacDto> getIzvodaciByIds(Set<Long> ids) {
        return izvodacRepository.findAllById(ids).stream()
                .map(IzvodacMapper::mapToIzvodacDto)
                .collect(Collectors.toSet());
    }
}

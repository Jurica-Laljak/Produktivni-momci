package hr.unizg.fer.ticket4ticket.service.impl;


import hr.unizg.fer.ticket4ticket.dto.IzvodacDto;
import hr.unizg.fer.ticket4ticket.entity.Izvodac;
import hr.unizg.fer.ticket4ticket.exception.ResourceNotFoundException;
import hr.unizg.fer.ticket4ticket.mapper.IzvodacMapper;
import hr.unizg.fer.ticket4ticket.repository.IzvodacRepository;
import hr.unizg.fer.ticket4ticket.service.IzvodacService;
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



    public Set<IzvodacDto> getIzvodaciByIds(Set<Long> ids) {
        return izvodacRepository.findAllById(ids).stream()
                .map(IzvodacMapper::mapToIzvodacDto)
                .collect(Collectors.toSet());
    }
}

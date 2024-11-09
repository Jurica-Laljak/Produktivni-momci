package hr.unizg.fer.ticket4ticket.service;

import hr.unizg.fer.ticket4ticket.dto.UlaznicaDto;

import java.util.List;

public interface UlaznicaService {

    UlaznicaDto createUlaznica(UlaznicaDto ulaznicaDto);

    UlaznicaDto getUlaznicaById(Long ulaznicaId);

    List<UlaznicaDto> getAllUlaznice();
}

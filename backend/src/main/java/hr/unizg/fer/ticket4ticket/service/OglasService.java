package hr.unizg.fer.ticket4ticket.service;

import hr.unizg.fer.ticket4ticket.dto.OglasDto;

import java.util.List;

public interface OglasService {

    OglasDto createOglas(OglasDto oglasDto);

    OglasDto getOglasById(Long oglasId);

    List<OglasDto> getAllOglasi();

    List<OglasDto> getOglasiByIzvodacId(Long izvodacId);
}

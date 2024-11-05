package com.ticket4ticket.backend.service;

import com.ticket4ticket.backend.dto.OglasDto;

import java.util.List;

public interface OglasService {

    OglasDto createOglas(OglasDto oglasDto);

    OglasDto getOglasById(Long oglasId);

    List<OglasDto> getAllOglasi();

    List<OglasDto> getOglasiByIzvodacId(Long izvodacId);
}

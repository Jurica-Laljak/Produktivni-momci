package com.ticket4ticket.backend.service;

import com.ticket4ticket.backend.dto.KoncertDto;

import java.util.List;

public interface KoncertService {
    KoncertDto createKoncert(KoncertDto koncertDto);

    KoncertDto  getKoncertById(Long koncertId);

    List<KoncertDto> getAllKoncerti();
}

package hr.unizg.fer.ticket4ticket.service;

import hr.unizg.fer.ticket4ticket.dto.KoncertDto;

import java.util.List;

public interface KoncertService {
    KoncertDto createKoncert(KoncertDto koncertDto);

    KoncertDto  getKoncertById(Long koncertId);

    List<KoncertDto> getAllKoncerti();
}

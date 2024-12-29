package hr.unizg.fer.ticket4ticket.service;

import hr.unizg.fer.ticket4ticket.dto.TransakcijaDto;
import hr.unizg.fer.ticket4ticket.entity.Transakcija;

import java.util.List;

public interface TransakcijaService {
    TransakcijaDto createTransakcija(Long ulaznicaPonudaId, Long ulaznicaOglasId, Long oglasId);
    List<TransakcijaDto> getTransakcijeByKorisnikPonudaIdAndStatus(Long korisnikPonudaId, Transakcija.StatusTransakcije status);
}

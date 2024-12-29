package hr.unizg.fer.ticket4ticket.service;

import hr.unizg.fer.ticket4ticket.dto.ObavijestDto;

import java.util.List;

public interface ObavijestService {

    ObavijestDto createObavijest(ObavijestDto obavijestDto);

    List<ObavijestDto> getObavijestiByKorisnikId(Long korisnikId);

    List<ObavijestDto> getObavijestiByZanrId(Long zanrId);

    List<ObavijestDto> getAllObavijesti();

    List<ObavijestDto> getObavijestiByOglasId(Long oglasId);

//    List<ObavijestDto> getObavijestiByTransakcijaId(Long transakcijaId);

    ObavijestDto getObavijestiById(Long obavijestId);

    Boolean removeObavijest(Long obavijestId);

    Long clearObavijestiOnTtl();
}

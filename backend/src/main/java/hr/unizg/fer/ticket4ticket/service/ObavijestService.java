package hr.unizg.fer.ticket4ticket.service;

import hr.unizg.fer.ticket4ticket.dto.ObavijestDto;
import hr.unizg.fer.ticket4ticket.dto.ObavijestInfoDto;

import java.util.List;

public interface ObavijestService {

    ObavijestDto createObavijest(ObavijestDto obavijestDto);

    List<ObavijestInfoDto>  getObavijestiByKorisnikId(Long korisnikId);

    List<ObavijestDto> getObavijestiByZanrId(Long zanrId);

    List<ObavijestDto> getAllObavijesti();

    List<ObavijestDto> getObavijestiByOglasId(Long oglasId);

    List<ObavijestDto> getObavijestiByTransakcijaId(Long transakcijaId);

    void getAndDeleteObavijestiByOglasId(Long oglasId);

    void getAndDeleteObavijestiByTransakcijaId(Long transakcijaId);

    ObavijestDto getObavijestiById(Long obavijestId);

    Boolean removeObavijest(Long obavijestId);

    void deleteObavijestiByKorisnikId(Long korisnikId);


}

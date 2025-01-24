package hr.unizg.fer.ticket4ticket.service;

import hr.unizg.fer.ticket4ticket.dto.*;
import hr.unizg.fer.ticket4ticket.entity.Zanr;

import java.util.List;

public interface OglasService {

    OglasDto createOglas(OglasDto oglasDto);

    OglasInfoDto getOglasById(Long oglasId);

    List<OglasInfoDto> getAllOglasi();

    List<OglasInfoDto> getRandomOglasi(int brojRandomOglasa);

    List<OglasInfoDto> getOglasiByFilter(OglasFilterDto filterDto);

    List<OglasInfoDto> getOglasiByKorisnikPreference(Long idKorisnika);

    List<OglasInfoDto> getRandomOglasiMax();

    void deleteOglasById(Long oglasId);

    List<OglasDto> findActiveOglasesByKorisnikId(Long korisnikId);

    List<IzvodacDto> getIzvodaciForOglas(Long oglasId);

    public void deleteAllOglasiByKorisnikId(Long idKorisnika);

    List<ZanrDto> getZanrsForOglas(Long oglasId);

    void deleteOglasByUlaznicaId(Long ulaznicaId);

    List<OglasInfoDto> getRandomOglasiWithoutKorisnik(int brojRandomOglasa, Long korisnikId);

    void setStatusToNeaktivan(Long idOglasa);

    OglasDto getOglasByUlaznicaId(Long ulaznicaId);
}

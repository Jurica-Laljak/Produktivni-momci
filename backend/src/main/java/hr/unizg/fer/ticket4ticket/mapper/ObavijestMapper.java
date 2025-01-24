package hr.unizg.fer.ticket4ticket.mapper;

import hr.unizg.fer.ticket4ticket.dto.ObavijestDto;
import hr.unizg.fer.ticket4ticket.entity.Obavijest;
import hr.unizg.fer.ticket4ticket.entity.Oglas;
import hr.unizg.fer.ticket4ticket.entity.Zanr;
import hr.unizg.fer.ticket4ticket.entity.Transakcija;

public class ObavijestMapper {

    public static ObavijestDto mapToObavijestDto(Obavijest obavijest) {
        ObavijestDto dto = new ObavijestDto();

        dto.setIdObavijesti(obavijest.getIdObavijesti());

        // Map ObavijestTip enum from entity to DTO
        dto.setObavijestType(obavijest.getObavijestTip());

        if (obavijest.getZanr() != null) {
            dto.setZanrId(obavijest.getZanr().getIdZanra());
        }
        if (obavijest.getOglas() != null) {
            dto.setOglasId(obavijest.getOglas().getIdOglasa());
        }
        if (obavijest.getTransakcija() != null) {
            dto.setTransakcijaId(obavijest.getTransakcija().getIdTransakcije());
        }
        // Map korisnikId
        dto.setKorisnikId(obavijest.getKorisnikId());

        return dto;
    }

    public static Obavijest mapToObavijest(ObavijestDto obavijestDto) {
        Obavijest obavijest = new Obavijest();

        obavijest.setIdObavijesti(obavijestDto.getIdObavijesti());

        // Set the ObavijestTip enum from DTO to entity
        obavijest.setObavijestTip(obavijestDto.getObavijestType());

        if (obavijestDto.getZanrId() != null) {
            Zanr zanr = new Zanr();
            zanr.setIdZanra(obavijestDto.getZanrId());
            obavijest.setZanr(zanr);
        }
        if (obavijestDto.getOglasId() != null) {
            Oglas oglas = new Oglas();
            oglas.setIdOglasa(obavijestDto.getOglasId());
            obavijest.setOglas(oglas);
        }
        if (obavijestDto.getTransakcijaId() != null) {
            Transakcija transakcija = new Transakcija();
            transakcija.setIdTransakcije(obavijestDto.getTransakcijaId());
            obavijest.setTransakcija(transakcija);
        }

        // Set korisnikId
        obavijest.setKorisnikId(obavijestDto.getKorisnikId());

        return obavijest;
    }
}
package hr.unizg.fer.ticket4ticket.mapper;

import hr.unizg.fer.ticket4ticket.dto.ObavijestDto;
import hr.unizg.fer.ticket4ticket.entity.Obavijest;
import hr.unizg.fer.ticket4ticket.entity.Oglas;
import hr.unizg.fer.ticket4ticket.entity.Zanr;

import java.time.Duration;

public class ObavijestMapper {

    public static ObavijestDto mapToObavijestDto(Obavijest obavijest) {
        ObavijestDto dto = new ObavijestDto();

        dto.setIdObavijesti(obavijest.getIdObavijesti());
        dto.setTtl(obavijest.getTimeToLive().toMinutes());
        dto.setObavijest(obavijest.getObavijest());

        if (obavijest.getZanr() != null) {
            dto.setZanrId(obavijest.getZanr().getIdZanra());
        }
        if (obavijest.getOglas() != null) {
            dto.setOglasId(obavijest.getOglas().getIdOglasa());
        }

        return dto;
    }

    public static Obavijest mapToObavijest(ObavijestDto obavijestDto) {
        Obavijest obavijest = new Obavijest();

        obavijest.setIdObavijesti(obavijestDto.getIdObavijesti());
        obavijest.setTimeToLive(Duration.ofMinutes(obavijestDto.getTtl()));
        obavijest.setObavijest(obavijestDto.getObavijest());

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

        return obavijest;
    }
}

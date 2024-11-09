package hr.unizg.fer.ticket4ticket.mapper;

import hr.unizg.fer.ticket4ticket.dto.ZanrDto;
import hr.unizg.fer.ticket4ticket.entity.Izvodac;
import hr.unizg.fer.ticket4ticket.entity.Korisnik;
import hr.unizg.fer.ticket4ticket.entity.Zanr;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ZanrMapper {

    // Map from Zanr entity to ZanrDto
    public static ZanrDto mapToZanrDto(Zanr zanr) {
        ZanrDto dto = new ZanrDto();
        dto.setIdZanra(zanr.getIdZanra());
        dto.setImeZanra(zanr.getImeZanra());
        dto.setSlikaZanra(zanr.getSlikaZanra());

        Set<Long> korisniciKojiSlusajuIds = zanr.getKorisnici()
                .stream()
                .map(Korisnik::getIdKorisnika)
                .collect(Collectors.toSet());

        dto.setKorisniciKojiSlusajuIds(korisniciKojiSlusajuIds);

        Set<Long> izvodaciIds = zanr.getIzvodaci()
                .stream()
                .map(Izvodac::getIdIzvodaca)
                .collect(Collectors.toSet());

        dto.setIzvodaciIds(izvodaciIds);
        return dto;
    }

    // Map from ZanrDto to Zanr entity
    public static Zanr mapToZanr(ZanrDto zanrDto) {
        Zanr zanr = new Zanr();
        zanr.setIdZanra(zanrDto.getIdZanra());
        zanr.setImeZanra(zanrDto.getImeZanra());
        zanr.setSlikaZanra(zanrDto.getSlikaZanra());


        Set<Korisnik> korisnici = new HashSet<>();
        if (zanrDto.getKorisniciKojiSlusajuIds() != null) {
            // Create Korisnik entities based on the IDs in the DTO
            for (Long id : zanrDto.getKorisniciKojiSlusajuIds()) {
                Korisnik korisnik = new Korisnik();
                korisnik.setIdKorisnika(id);
                korisnici.add(korisnik);
            }
        }
        zanr.setKorisnici(korisnici);

        Set<Izvodac> izvodaci = new HashSet<>();
        if (zanrDto.getIzvodaciIds() != null) {
            // Create Izvodac entities based on the IDs in the DTO
            for (Long id : zanrDto.getIzvodaciIds()) {
                Izvodac izvodac = new Izvodac();
                izvodac.setIdIzvodaca(id);
                izvodaci.add(izvodac);
            }
        }
        zanr.setIzvodaci(izvodaci);
        return zanr;
    }
}
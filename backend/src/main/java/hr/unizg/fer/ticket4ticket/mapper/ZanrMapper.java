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

        // Convert the Set<Korisnik> to Set<Long> (IDs)
        Set<Long> korisniciKojiSlusajuIds = zanr.getKorisnici()
                .stream()
                .map(Korisnik::getIdKorisnika) // Assuming Korisnik has a method getIdKorisnika()
                .collect(Collectors.toSet());

        dto.setKorisniciKojiSlusajuIds(korisniciKojiSlusajuIds);

        // Convert the Set<Izvodac> to Set<Long> (IDs)
        Set<Long> izvodaciIds = zanr.getIzvodaci() // Assuming this is the set of Izvodac entities
                .stream()
                .map(Izvodac::getIdIzvodaca) // Assuming Izvodac has a method getIdIzvodaca()
                .collect(Collectors.toSet());

        dto.setIzvodaciIds(izvodaciIds); // Set the Izvodac IDs in the DTO
        return dto;
    }

    // Map from ZanrDto to Zanr entity
    public static Zanr mapToZanr(ZanrDto zanrDto) {
        Zanr zanr = new Zanr();
        zanr.setIdZanra(zanrDto.getIdZanra());
        zanr.setImeZanra(zanrDto.getImeZanra());
        zanr.setSlikaZanra(zanrDto.getSlikaZanra());

        // Initialize korisnici to an empty Set
        Set<Korisnik> korisnici = new HashSet<>();
        if (zanrDto.getKorisniciKojiSlusajuIds() != null) {
            // Create Korisnik entities based on the IDs in the DTO
            for (Long id : zanrDto.getKorisniciKojiSlusajuIds()) {
                Korisnik korisnik = new Korisnik(); // Assuming Korisnik has a default constructor
                korisnik.setIdKorisnika(id); // Set the ID
                korisnici.add(korisnik);
            }
        }
        zanr.setKorisnici(korisnici);

        // Initialize izvodaci to an empty Set
        Set<Izvodac> izvodaci = new HashSet<>();
        if (zanrDto.getIzvodaciIds() != null) {
            // Create Izvodac entities based on the IDs in the DTO
            for (Long id : zanrDto.getIzvodaciIds()) {
                Izvodac izvodac = new Izvodac(); // Assuming Izvodac has a default constructor
                izvodac.setIdIzvodaca(id); // Set the ID
                izvodaci.add(izvodac);
            }
        }
        zanr.setIzvodaci(izvodaci); // Assuming the Zanr class has a setIzvodaci method
        return zanr;
    }
}
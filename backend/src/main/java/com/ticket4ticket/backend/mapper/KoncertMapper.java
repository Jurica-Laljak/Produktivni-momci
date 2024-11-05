package com.ticket4ticket.backend.mapper;

import com.ticket4ticket.backend.dto.KoncertDto;
import com.ticket4ticket.backend.entity.Izvodac;
import com.ticket4ticket.backend.entity.Oglas;
import com.ticket4ticket.backend.entity.Koncert;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class KoncertMapper {

    // Map from Koncert entity to KoncertDto
    public static KoncertDto mapToKoncertDto(Koncert koncert) {
        KoncertDto dto = new KoncertDto();
        dto.setIdKoncerta(koncert.getIdKoncerta());
        dto.setDatumKoncerta(koncert.getDatumKoncerta());
        dto.setLokacijaKoncerta(koncert.getLokacijaKoncerta());

        // Convert the Set<Izvodac> to Set<Long> (IDs)
        Set<Long> izvodaciIds = koncert.getIzvodaci()
                .stream()
                .map(Izvodac::getIdIzvodaca) // Assuming Izvodac has a method getIdIzvodaca()
                .collect(Collectors.toSet());

        dto.setIzvodaciIds(izvodaciIds); // Assuming this setter exists in the DTO


        // Convert the Set<Oglas> to Set<Long> (IDs)
        Set<Long> oglasiIds = koncert.getOglasi()
                .stream()
                .map(Oglas::getIdOglasa) // Assuming Oglas has a method getIdOglasa()
                .collect(Collectors.toSet());

        dto.setOglasiIds(oglasiIds); // Assuming this setter exists in the DTO

        return dto;
    }

    // Map from KoncertDto to Koncert entity
    public static Koncert mapToKoncert(KoncertDto koncertDto) {
        Koncert koncert = new Koncert();
        koncert.setIdKoncerta(koncertDto.getIdKoncerta());
        koncert.setDatumKoncerta(koncertDto.getDatumKoncerta());
        koncert.setLokacijaKoncerta(koncertDto.getLokacijaKoncerta());

        // Initialize izvodaci to an empty Set
        Set<Izvodac> izvodaci = new HashSet<>();

        if (koncertDto.getIzvodaciIds() != null) {
            // Create Izvodac entities based on the IDs in the DTO
            for (Long id : koncertDto.getIzvodaciIds()) {
                Izvodac izvodac = new Izvodac(); // Assuming Izvodac has a default constructor
                izvodac.setIdIzvodaca(id); // Set the ID
                izvodaci.add(izvodac);
            }
        }

        koncert.setIzvodaci(izvodaci);

        // Initialize oglasi to an empty Set (if needed for later use)
        // We may want to handle the mapping of Oglas differently if we need to populate it based on IDs.
        Set<Oglas> oglasi = new HashSet<>();
        if (koncertDto.getOglasiIds() != null) {
            for (Long id : koncertDto.getOglasiIds()) {
                Oglas oglas = new Oglas(); // Assuming Oglas has a default constructor
                oglas.setIdOglasa(id); // Set the ID
                oglasi.add(oglas);
            }
        }
        koncert.setOglasi(oglasi);

        return koncert;
    }
}
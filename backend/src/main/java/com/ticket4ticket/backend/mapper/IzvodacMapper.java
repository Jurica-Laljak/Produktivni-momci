package com.ticket4ticket.backend.mapper;

import com.ticket4ticket.backend.dto.IzvodacDto;
import com.ticket4ticket.backend.entity.Izvodac;
import com.ticket4ticket.backend.entity.Korisnik;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class IzvodacMapper {

    // Map from Izvodac entity to IzvodacDto
    public static IzvodacDto mapToIzvodacDto(Izvodac izvodac) {
        IzvodacDto dto = new IzvodacDto();
        dto.setIdIzvodaca(izvodac.getIdIzvodaca());
        dto.setImeIzvodaca(izvodac.getImeIzvodaca());
        dto.setPrezimeIzvodaca(izvodac.getPrezimeIzvodaca());
        dto.setStarostIzvodaca(izvodac.getStarostIzvodaca());
        dto.setZanrIzvodaca(izvodac.getZanrIzvodaca());
        dto.setFotoIzvodaca(izvodac.getFotoIzvodaca());

        // Convert the Set<Korisnik> to Set<Long> (IDs)
        Set<Long> korisniciKojiSlusajuIds = izvodac.getKorisniciKojiSlusaju() // Assuming this is the set of Korisnik entities
                .stream()
                .map(korisnik -> korisnik.getIdKorisnika()) // Assuming Korisnik has a method getIdKorisnika()
                .collect(Collectors.toSet());

        dto.setKorisniciKojiSlusajuIds(korisniciKojiSlusajuIds);
        return dto;
    }

    // Map from IzvodacDto to Izvodac entity
    public static Izvodac mapToIzvodac(IzvodacDto izvodacDto) {
        Izvodac izvodac = new Izvodac();
        izvodac.setIdIzvodaca(izvodacDto.getIdIzvodaca());
        izvodac.setImeIzvodaca(izvodacDto.getImeIzvodaca());
        izvodac.setPrezimeIzvodaca(izvodacDto.getPrezimeIzvodaca());
        izvodac.setStarostIzvodaca(izvodacDto.getStarostIzvodaca());
        izvodac.setZanrIzvodaca(izvodacDto.getZanrIzvodaca());
        izvodac.setFotoIzvodaca(izvodacDto.getFotoIzvodaca());

        // Initialize korisniciKojiSlusaju to an empty Set
        Set<Korisnik> korisniciKojiSlusaju = new HashSet<>();

        if (izvodacDto.getKorisniciKojiSlusajuIds() != null) {
            // Create Korisnik entities based on the IDs in the DTO
            for (Long id : izvodacDto.getKorisniciKojiSlusajuIds()) {
                Korisnik korisnik = new Korisnik(); // Assuming Korisnik has a default constructor
                korisnik.setIdKorisnika(id); // Set the ID
                korisniciKojiSlusaju.add(korisnik);
            }
        }

        izvodac.setKorisniciKojiSlusaju(korisniciKojiSlusaju);
        return izvodac;
    }
}
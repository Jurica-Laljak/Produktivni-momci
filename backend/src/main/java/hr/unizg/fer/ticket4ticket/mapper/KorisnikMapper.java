package hr.unizg.fer.ticket4ticket.mapper;

import hr.unizg.fer.ticket4ticket.dto.KorisnikDto;
import hr.unizg.fer.ticket4ticket.entity.Izvodac;
import hr.unizg.fer.ticket4ticket.entity.Oglas;
import hr.unizg.fer.ticket4ticket.entity.Korisnik;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class KorisnikMapper {

    // Map from Korisnik entity to KorisnikDto
    public static KorisnikDto mapToKorisnikDto(Korisnik korisnik) {
        KorisnikDto dto = new KorisnikDto();
        dto.setIdKorisnika(korisnik.getIdKorisnika());
        dto.setImeKorisnika(korisnik.getImeKorisnika());
        dto.setPrezimeKorisnika(korisnik.getPrezimeKorisnika());
        dto.setEmailKorisnika(korisnik.getEmailKorisnika());
        dto.setBrMobKorisnika(korisnik.getBrMobKorisnika());
        dto.setOibKorisnika(korisnik.getOibKorisnika());
        dto.setFotoKorisnika(korisnik.getFotoKorisnika());
        dto.setGoogleId(korisnik.getGoogleId());

        // Convert the Set<Izvodac> to Set<Long> (IDs)
        Set<Long> omiljeniIzvodaciIds = korisnik.getOmiljeniIzvodaci()
                .stream()
                .map(Izvodac::getIdIzvodaca) // Assuming Izvodac has a method getId()
                .collect(Collectors.toSet());

        dto.setOmiljeniIzvodaciIds(omiljeniIzvodaciIds);

        // Convert the Set<Oglas> to Set<Long> (IDs)
        Set<Long> oglasiIds = korisnik.getOglasi() // Assuming this is the set of Oglas entities
                .stream()
                .map(Oglas::getIdOglasa) // Assuming Oglas has a method getIdOglasa()
                .collect(Collectors.toSet());

        dto.setOglasiIds(oglasiIds);

        return dto;
    }

    // Map from KorisnikDto to Korisnik entity
    public static Korisnik mapToKorisnik(KorisnikDto korisnikDto) {
        Korisnik korisnik = new Korisnik();
        korisnik.setIdKorisnika(korisnikDto.getIdKorisnika());
        korisnik.setImeKorisnika(korisnikDto.getImeKorisnika());
        korisnik.setPrezimeKorisnika(korisnikDto.getPrezimeKorisnika());
        korisnik.setEmailKorisnika(korisnikDto.getEmailKorisnika());
        korisnik.setBrMobKorisnika(korisnikDto.getBrMobKorisnika());
        korisnik.setOibKorisnika(korisnikDto.getOibKorisnika());
        korisnik.setFotoKorisnika(korisnikDto.getFotoKorisnika());
        korisnik.setGoogleId(korisnikDto.getGoogleId());

        // Initialize omiljeniIzvodaci to an empty Set
        Set<Izvodac> omiljeniIzvodaci = new HashSet<>();

        if (korisnikDto.getOmiljeniIzvodaciIds() != null) {
            // Create Izvodac entities based on the IDs in the DTO
            for (Long id : korisnikDto.getOmiljeniIzvodaciIds()) {
                Izvodac izvodac = new Izvodac(); // Assuming Izvodac has a default constructor
                izvodac.setIdIzvodaca(id); // Set the ID
                omiljeniIzvodaci.add(izvodac);
            }
        }

        korisnik.setOmiljeniIzvodaci(omiljeniIzvodaci);

        // Initialize oglasi to an empty Set
        Set<Oglas> oglasi = new HashSet<>();

        if (korisnikDto.getOglasiIds() != null) {
            // Create Oglas entities based on the IDs in the DTO
            for (Long id : korisnikDto.getOglasiIds()) {
                Oglas oglas = new Oglas(); // Assuming Oglas has a default constructor
                oglas.setIdOglasa(id); // Set the ID
                oglasi.add(oglas);
            }
        }

        korisnik.setOglasi(oglasi);

        return korisnik;

    }
}
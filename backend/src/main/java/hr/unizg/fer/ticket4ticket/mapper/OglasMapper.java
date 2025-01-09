package hr.unizg.fer.ticket4ticket.mapper;

import hr.unizg.fer.ticket4ticket.dto.OglasDto;
import hr.unizg.fer.ticket4ticket.entity.Oglas;
import hr.unizg.fer.ticket4ticket.entity.Korisnik;
import hr.unizg.fer.ticket4ticket.entity.Ulaznica;
import hr.unizg.fer.ticket4ticket.entity.Transakcija;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class OglasMapper {

    // Map from Oglas entity to OglasDto
    public static OglasDto mapToOglasDto(Oglas oglas) {
        OglasDto dto = new OglasDto();
        dto.setIdOglasa(oglas.getIdOglasa());

        // Convert Oglas.Status to String
        if (oglas.getStatus() != null) {
            dto.setStatus(oglas.getStatus().name());
        }

        // Map the IDs of related entities
        if (oglas.getKorisnik() != null) {
            dto.setKorisnikId(oglas.getKorisnik().getIdKorisnika());
        }
        if (oglas.getUlaznica() != null) {
            dto.setUlaznicaId(oglas.getUlaznica().getIdUlaznice());
        }

        // Map the transaction IDs
        if (oglas.getTransakcije() != null) {
            Set<Long> transakcijeIds = oglas.getTransakcije()
                    .stream()
                    .map(Transakcija::getIdTransakcije)
                    .collect(Collectors.toSet());
            dto.setTransakcijeIds(transakcijeIds);
        }

        return dto;
    }

    // Map from OglasDto to Oglas entity
    public static Oglas mapToOglas(OglasDto oglasDto) {
        Oglas oglas = new Oglas();
        oglas.setIdOglasa(oglasDto.getIdOglasa());

        // Convert String back to Oglas.Status (only if it is a valid status string)
        if (oglasDto.getStatus() != null) {
            oglas.setStatus(Oglas.Status.valueOf(oglasDto.getStatus()));
        }

        // Set the related entities based on their IDs
        if (oglasDto.getKorisnikId() != null) {
            Korisnik korisnik = new Korisnik();
            korisnik.setIdKorisnika(oglasDto.getKorisnikId());
            oglas.setKorisnik(korisnik);
        }
        if (oglasDto.getUlaznicaId() != null) {
            Ulaznica ulaznica = new Ulaznica();
            ulaznica.setIdUlaznice(oglasDto.getUlaznicaId());
            oglas.setUlaznica(ulaznica);
        }

        // Set the related transactions based on their IDs
        if (oglasDto.getTransakcijeIds() != null) {
            Set<Transakcija> transakcije = new HashSet<>();
            for (Long id : oglasDto.getTransakcijeIds()) {
                Transakcija transakcija = new Transakcija();
                transakcija.setIdTransakcije(id);
                transakcije.add(transakcija);
            }
            oglas.setTransakcije(transakcije);
        }

        return oglas;
    }
}
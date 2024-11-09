package hr.unizg.fer.ticket4ticket.mapper;

import hr.unizg.fer.ticket4ticket.dto.OglasDto;
import hr.unizg.fer.ticket4ticket.entity.Oglas;
import hr.unizg.fer.ticket4ticket.entity.Korisnik;
import hr.unizg.fer.ticket4ticket.entity.Ulaznica;

public class OglasMapper {

    // Map from Oglas entity to OglasDto
    public static OglasDto mapToOglasDto(Oglas oglas) {
        OglasDto dto = new OglasDto();
        dto.setIdOglasa(oglas.getIdOglasa());
        dto.setStatus(oglas.getStatus());

        // Map the IDs of related entities
        if (oglas.getKorisnik() != null) {
            dto.setKorisnikId(oglas.getKorisnik().getIdKorisnika());
        }
        if (oglas.getUlaznica() != null) {
            dto.setUlaznicaId(oglas.getUlaznica().getIdUlaznice());
        }

        return dto;
    }

    // Map from OglasDto to Oglas entity
    public static Oglas mapToOglas(OglasDto oglasDto) {
        Oglas oglas = new Oglas();
        oglas.setIdOglasa(oglasDto.getIdOglasa());
        oglas.setStatus(oglasDto.getStatus());

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

        return oglas;
    }
}
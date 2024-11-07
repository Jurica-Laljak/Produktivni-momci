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
            dto.setKorisnikId(oglas.getKorisnik().getIdKorisnika()); // Get the ID of the user
        }
        if (oglas.getUlaznica() != null) {
            dto.setUlaznicaId(oglas.getUlaznica().getIdUlaznice()); // Get the ID of the concert
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
            Korisnik korisnik = new Korisnik(); // Create a new instance of Korisnik
            korisnik.setIdKorisnika(oglasDto.getKorisnikId()); // Set the ID
            oglas.setKorisnik(korisnik); // Assign to Oglas
        }
        if (oglasDto.getUlaznicaId() != null) {
            Ulaznica koncert = new Ulaznica(); // Create a new instance of Koncert
            koncert.setIdUlaznice(oglasDto.getUlaznicaId()); // Set the ID
            oglas.setUlaznica(koncert); // Assign to Oglas
        }

        return oglas;
    }
}
package com.ticket4ticket.backend.mapper;

import com.ticket4ticket.backend.dto.OglasDto;
import com.ticket4ticket.backend.entity.Oglas;
import com.ticket4ticket.backend.entity.Korisnik;
import com.ticket4ticket.backend.entity.Koncert;

public class OglasMapper {

    // Map from Oglas entity to OglasDto
    public static OglasDto mapToOglasDto(Oglas oglas) {
        OglasDto dto = new OglasDto();
        dto.setIdOglasa(oglas.getIdOglasa());
        dto.setOdabranaZona(oglas.getOdabranaZona());
        dto.setVrstaUlaznice(oglas.getVrstaUlaznice());
        dto.setStatus(oglas.getStatus());

        // Map the IDs of related entities
        if (oglas.getKorisnik() != null) {
            dto.setKorisnikId(oglas.getKorisnik().getIdKorisnika()); // Get the ID of the user
        }
        if (oglas.getKoncert() != null) {
            dto.setKoncertId(oglas.getKoncert().getIdKoncerta()); // Get the ID of the concert
        }

        return dto;
    }

    // Map from OglasDto to Oglas entity
    public static Oglas mapToOglas(OglasDto oglasDto) {
        Oglas oglas = new Oglas();
        oglas.setIdOglasa(oglasDto.getIdOglasa());
        oglas.setOdabranaZona(oglasDto.getOdabranaZona());
        oglas.setVrstaUlaznice(oglasDto.getVrstaUlaznice());
        oglas.setStatus(oglasDto.getStatus());

        // Set the related entities based on their IDs
        if (oglasDto.getKorisnikId() != null) {
            Korisnik korisnik = new Korisnik(); // Create a new instance of Korisnik
            korisnik.setIdKorisnika(oglasDto.getKorisnikId()); // Set the ID
            oglas.setKorisnik(korisnik); // Assign to Oglas
        }
        if (oglasDto.getKoncertId() != null) {
            Koncert koncert = new Koncert(); // Create a new instance of Koncert
            koncert.setIdKoncerta(oglasDto.getKoncertId()); // Set the ID
            oglas.setKoncert(koncert); // Assign to Oglas
        }

        return oglas;
    }
}
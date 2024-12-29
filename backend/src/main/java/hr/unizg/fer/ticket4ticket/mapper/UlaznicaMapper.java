package hr.unizg.fer.ticket4ticket.mapper;

import hr.unizg.fer.ticket4ticket.dto.UlaznicaDto;
import hr.unizg.fer.ticket4ticket.entity.Izvodac;
import hr.unizg.fer.ticket4ticket.entity.Korisnik;
import hr.unizg.fer.ticket4ticket.entity.Oglas;
import hr.unizg.fer.ticket4ticket.entity.Ulaznica;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UlaznicaMapper {

    // Map from Ulaznica entity to UlaznicaDto
    public static UlaznicaDto mapToUlaznicaDto(Ulaznica ulaznica) {
        UlaznicaDto dto = new UlaznicaDto();
        dto.setIdUlaznice(ulaznica.getIdUlaznice());
        dto.setDatumKoncerta(ulaznica.getDatumKoncerta());
        dto.setLokacijaKoncerta(ulaznica.getLokacijaKoncerta());
        dto.setOdabranaZona(ulaznica.getOdabranaZona().name());  // Convert Enum to String
        dto.setVrstaUlaznice(ulaznica.getVrstaUlaznice().name()); // Convert Enum to String
        dto.setUrlSlika(ulaznica.getUrlSlika());
        dto.setUrlInfo(ulaznica.getUrlInfo());
        dto.setStatus(ulaznica.getStatus().name()); // Convert Enum to String
        dto.setIdKorisnika(ulaznica.getKorisnik() != null ? ulaznica.getKorisnik().getIdKorisnika() : null);

        // Map sifraUlaznice
        dto.setSifraUlaznice(ulaznica.getSifraUlaznice());

        // Map related Izvodaci and Oglasi
        dto.setIzvodaciIds(ulaznica.getIzvodaciIds());
        dto.setOglasiIds(ulaznica.getOglasiIds());

        return dto;
    }

    // Map from UlaznicaDto to Ulaznica entity
    public static Ulaznica mapToUlaznica(UlaznicaDto ulaznicaDto) {
        Ulaznica ulaznica = new Ulaznica();
        ulaznica.setIdUlaznice(ulaznicaDto.getIdUlaznice());
        ulaznica.setDatumKoncerta(ulaznicaDto.getDatumKoncerta());
        ulaznica.setLokacijaKoncerta(ulaznicaDto.getLokacijaKoncerta());

        // Map OdabranaZona and VrstaUlaznice from String to Enum
        ulaznica.setOdabranaZona(Ulaznica.OdabranaZona.valueOf(ulaznicaDto.getOdabranaZona()));
        ulaznica.setVrstaUlaznice(Ulaznica.VrstaUlaznice.valueOf(ulaznicaDto.getVrstaUlaznice()));

        ulaznica.setUrlSlika(ulaznicaDto.getUrlSlika());
        ulaznica.setUrlInfo(ulaznicaDto.getUrlInfo());

        // Map status from String to Enum
        ulaznica.setStatus(Ulaznica.Status.valueOf(ulaznicaDto.getStatus()));

        // Map sifraUlaznice
        ulaznica.setSifraUlaznice(ulaznicaDto.getSifraUlaznice());

        // Map korisnik if idKorisnika is provided
        if (ulaznicaDto.getIdKorisnika() != null) {
            Korisnik korisnik = new Korisnik();
            korisnik.setIdKorisnika(ulaznicaDto.getIdKorisnika());
            ulaznica.setKorisnik(korisnik);
        }

        // Map Izvodaci
        Set<Izvodac> izvodaci = new HashSet<>();
        if (ulaznicaDto.getIzvodaciIds() != null) {
            for (Long id : ulaznicaDto.getIzvodaciIds()) {
                Izvodac izvodac = new Izvodac();
                izvodac.setIdIzvodaca(id);
                izvodaci.add(izvodac);
            }
        }
        ulaznica.setIzvodaci(izvodaci);

        // Map Oglasi
        Set<Oglas> oglasi = new HashSet<>();
        if (ulaznicaDto.getOglasiIds() != null) {
            for (Long id : ulaznicaDto.getOglasiIds()) {
                Oglas oglas = new Oglas();
                oglas.setIdOglasa(id);
                oglasi.add(oglas);
            }
        }
        ulaznica.setOglasi(oglasi);

        return ulaznica;
    }
}
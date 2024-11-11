package hr.unizg.fer.ticket4ticket.mapper;

import hr.unizg.fer.ticket4ticket.dto.UlaznicaDto;
import hr.unizg.fer.ticket4ticket.entity.Izvodac;
import hr.unizg.fer.ticket4ticket.entity.Oglas;
import hr.unizg.fer.ticket4ticket.entity.Ulaznica;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UlaznicaMapper {

    // Map from Ulaznica entity to UlaznicaDto
    public static UlaznicaDto mapToUlaznicaDto(Ulaznica ulaznica) {
        UlaznicaDto dto = new UlaznicaDto();
        dto.setIdKoncerta(ulaznica.getIdUlaznice());
        dto.setDatumKoncerta(ulaznica.getDatumKoncerta());
        dto.setLokacijaKoncerta(ulaznica.getLokacijaKoncerta());
        dto.setOdabranaZona(ulaznica.getOdabranaZona());
        dto.setVrstaUlaznice(ulaznica.getVrstaUlaznice());

        Set<Long> izvodaciIds = ulaznica.getIzvodaci()
                .stream()
                .map(Izvodac::getIdIzvodaca) // Assuming Izvodac has a method getIdIzvodaca()
                .collect(Collectors.toSet());

        dto.setIzvodaciIds(izvodaciIds); // Assuming this setter exists in the DTO

        // Convert the Set<Oglas> to Set<Long> (IDs)
        Set<Long> oglasiIds = ulaznica.getOglasi()
                .stream()
                .map(Oglas::getIdOglasa) // Assuming Oglas has a method getIdOglasa()
                .collect(Collectors.toSet());

        dto.setOglasiIds(oglasiIds); // Assuming this setter exists in the DTO

        return dto;
    }

    // Map from UlaznicaDto to Ulaznica entity
    public static Ulaznica mapToUlaznica(UlaznicaDto ulaznicaDto) {
        Ulaznica ulaznica = new Ulaznica();
        ulaznica.setIdUlaznice(ulaznicaDto.getIdKoncerta());
        ulaznica.setDatumKoncerta(ulaznicaDto.getDatumKoncerta());
        ulaznica.setLokacijaKoncerta(ulaznicaDto.getLokacijaKoncerta());
        ulaznica.setOdabranaZona(ulaznicaDto.getOdabranaZona());
        ulaznica.setVrstaUlaznice(ulaznicaDto.getVrstaUlaznice());

        Set<Izvodac> izvodaci = new HashSet<>();
        if (ulaznicaDto.getIzvodaciIds() != null) {
            // Create Izvodac entities based on the IDs in the DTO
            for (Long id : ulaznicaDto.getIzvodaciIds()) {
                Izvodac izvodac = new Izvodac();
                izvodac.setIdIzvodaca(id);
                izvodaci.add(izvodac);
            }
        }
        ulaznica.setIzvodaci(izvodaci);

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
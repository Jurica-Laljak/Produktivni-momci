package hr.unizg.fer.ticket4ticket.mapper;

import hr.unizg.fer.ticket4ticket.dto.TransakcijaDto;
import hr.unizg.fer.ticket4ticket.entity.Transakcija;
import hr.unizg.fer.ticket4ticket.entity.Ulaznica;
import hr.unizg.fer.ticket4ticket.entity.Oglas;
import hr.unizg.fer.ticket4ticket.entity.Korisnik;

public class TransakcijaMapper {

    public static TransakcijaDto mapToTransakcijaDto(Transakcija transakcija) {
        TransakcijaDto dto = new TransakcijaDto();
        dto.setIdTransakcije(transakcija.getIdTransakcije());
        dto.setIdUlaznicaPonuda(
                transakcija.getUlaznicaPonuda() != null ? transakcija.getUlaznicaPonuda().getIdUlaznice() : null
        );
        dto.setIdUlaznicaOglas(
                transakcija.getUlaznicaOglas() != null ? transakcija.getUlaznicaOglas().getIdUlaznice() : null
        );
        dto.setIdOglas(
                transakcija.getOglas() != null ? transakcija.getOglas().getIdOglasa() : null
        );
        dto.setStatusTransakcije(transakcija.getStatusTransakcije().name());
        dto.setDatumTransakcije(transakcija.getDatumTransakcije());

        // Map the Korisnik (user) foreign keys to DTO
        dto.setIdKorisnikPonuda(
                transakcija.getKorisnikPonuda() != null ? transakcija.getKorisnikPonuda().getIdKorisnika() : null
        );
        dto.setIdKorisnikOglas(
                transakcija.getKorisnikOglas() != null ? transakcija.getKorisnikOglas().getIdKorisnika() : null
        );

        return dto;
    }

    public static Transakcija mapToTransakcija(TransakcijaDto dto) {
        Transakcija transakcija = new Transakcija();
        transakcija.setIdTransakcije(dto.getIdTransakcije());

        if (dto.getIdUlaznicaPonuda() != null) {
            Ulaznica ulaznicaPonuda = new Ulaznica();
            ulaznicaPonuda.setIdUlaznice(dto.getIdUlaznicaPonuda());
            transakcija.setUlaznicaPonuda(ulaznicaPonuda);
        }

        if (dto.getIdUlaznicaOglas() != null) {
            Ulaznica ulaznicaOglas = new Ulaznica();
            ulaznicaOglas.setIdUlaznice(dto.getIdUlaznicaOglas());
            transakcija.setUlaznicaOglas(ulaznicaOglas);
        }

        if (dto.getIdOglas() != null) {
            Oglas oglas = new Oglas();
            oglas.setIdOglasa(dto.getIdOglas());
            transakcija.setOglas(oglas);
        }

        // Map the Korisnik (user) foreign keys from DTO to entity
        if (dto.getIdKorisnikPonuda() != null) {
            Korisnik korisnikPonuda = new Korisnik();
            korisnikPonuda.setIdKorisnika(dto.getIdKorisnikPonuda());
            transakcija.setKorisnikPonuda(korisnikPonuda);
        }

        if (dto.getIdKorisnikOglas() != null) {
            Korisnik korisnikOglas = new Korisnik();
            korisnikOglas.setIdKorisnika(dto.getIdKorisnikOglas());
            transakcija.setKorisnikOglas(korisnikOglas);
        }

        transakcija.setStatusTransakcije(
                Transakcija.StatusTransakcije.valueOf(dto.getStatusTransakcije())
        );
        transakcija.setDatumTransakcije(dto.getDatumTransakcije());
        return transakcija;
    }
}
package hr.unizg.fer.ticket4ticket.mapper;

import hr.unizg.fer.ticket4ticket.dto.KorisnikDto;
import hr.unizg.fer.ticket4ticket.entity.*;

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
        dto.setFotoKorisnika(korisnik.getFotoKorisnika());
        dto.setGoogleId(korisnik.getGoogleId());
        dto.setPrikazujObavijesti(korisnik.isPrikazujObavijesti()); // New field mapping

        // Convert the Set<Izvodac> to Set<Long> (IDs)
        Set<Long> omiljeniIzvodaciIds = korisnik.getOmiljeniIzvodaci()
                .stream()
                .map(Izvodac::getIdIzvodaca)
                .collect(Collectors.toSet());

        dto.setOmiljeniIzvodaciIds(omiljeniIzvodaciIds);

        Set<Long> oglasiIds = korisnik.getOglasi()
                .stream()
                .map(Oglas::getIdOglasa)
                .collect(Collectors.toSet());

        dto.setOglasiIds(oglasiIds);

        // Convert the Set<Zanr> to Set<Long> (IDs)
        Set<Long> omiljeniZanroviIds = korisnik.getOmiljeniZanrovi()
                .stream()
                .map(Zanr::getIdZanra)
                .collect(Collectors.toSet());

        dto.setOmiljeniZanroviIds(omiljeniZanroviIds);

        Set<Long> roleIds = korisnik.getRoles()
                .stream()
                .map(Role::getIdRole)
                .collect(Collectors.toSet());

        dto.setRoleIds(roleIds);

        // Map transaction IDs (Ponuda)
        Set<Long> transakcijePonudaIds = korisnik.getTransakcijePonuda()
                .stream()
                .map(Transakcija::getIdTransakcije)
                .collect(Collectors.toSet());

        dto.setTransakcijePonudaIds(transakcijePonudaIds);

        // Map transaction IDs (Oglas)
        Set<Long> transakcijeOglasIds = korisnik.getTransakcijeOglas()
                .stream()
                .map(Transakcija::getIdTransakcije)
                .collect(Collectors.toSet());

        dto.setTransakcijeOglasIds(transakcijeOglasIds);

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
        korisnik.setFotoKorisnika(korisnikDto.getFotoKorisnika());
        korisnik.setGoogleId(korisnikDto.getGoogleId());
        korisnik.setPrikazujObavijesti(korisnikDto.isPrikazujObavijesti()); // New field mapping

        // Initialize omiljeniIzvodaci to an empty Set
        Set<Izvodac> omiljeniIzvodaci = new HashSet<>();
        if (korisnikDto.getOmiljeniIzvodaciIds() != null) {
            for (Long id : korisnikDto.getOmiljeniIzvodaciIds()) {
                Izvodac izvodac = new Izvodac();
                izvodac.setIdIzvodaca(id);
                omiljeniIzvodaci.add(izvodac);
            }
        }
        korisnik.setOmiljeniIzvodaci(omiljeniIzvodaci);

        // Initialize oglasi to an empty Set
        Set<Oglas> oglasi = new HashSet<>();
        if (korisnikDto.getOglasiIds() != null) {
            for (Long id : korisnikDto.getOglasiIds()) {
                Oglas oglas = new Oglas();
                oglas.setIdOglasa(id);
                oglasi.add(oglas);
            }
        }
        korisnik.setOglasi(oglasi);

        // Initialize omiljeniZanrovi to an empty Set
        Set<Zanr> omiljeniZanrovi = new HashSet<>();
        if (korisnikDto.getOmiljeniZanroviIds() != null) {
            for (Long id : korisnikDto.getOmiljeniZanroviIds()) {
                Zanr zanr = new Zanr();
                zanr.setIdZanra(id);
                omiljeniZanrovi.add(zanr);
            }
        }
        korisnik.setOmiljeniZanrovi(omiljeniZanrovi);

        // Initialize roles to an empty Set
        Set<Role> roles = new HashSet<>();
        if (korisnikDto.getRoleIds() != null) {
            for (Long id : korisnikDto.getRoleIds()) {
                Role role = new Role();
                role.setIdRole(id);
                roles.add(role);
            }
        }
        korisnik.setRoles(roles);

        // Initialize transakcijePonuda to an empty Set
        Set<Transakcija> transakcijePonuda = new HashSet<>();
        if (korisnikDto.getTransakcijePonudaIds() != null) {
            for (Long id : korisnikDto.getTransakcijePonudaIds()) {
                Transakcija transakcija = new Transakcija();
                transakcija.setIdTransakcije(id);
                transakcijePonuda.add(transakcija);
            }
        }
        korisnik.setTransakcijePonuda(transakcijePonuda);

        // Initialize transakcijeOglas to an empty Set
        Set<Transakcija> transakcijeOglas = new HashSet<>();
        if (korisnikDto.getTransakcijeOglasIds() != null) {
            for (Long id : korisnikDto.getTransakcijeOglasIds()) {
                Transakcija transakcija = new Transakcija();
                transakcija.setIdTransakcije(id);
                transakcijeOglas.add(transakcija);
            }
        }
        korisnik.setTransakcijeOglas(transakcijeOglas);

        return korisnik;
    }
}

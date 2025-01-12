package hr.unizg.fer.ticket4ticket.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "KORISNIK")
public class Korisnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idKorisnika", nullable = false)
    private Long idKorisnika;

    @NotBlank
    @Column(name = "imeKorisnika", nullable = false, length = 50)
    private String imeKorisnika;

    @NotBlank
    @Column(name = "prezimeKorisnika", nullable = false, length = 50)
    private String prezimeKorisnika;

    @Email
    @NotBlank
    @Column(name = "emailKorisnika", nullable = false, unique = true, length = 50)
    private String emailKorisnika;

    @Column(name = "brMobKorisnika", nullable = true, unique = true)
    private String brMobKorisnika;

    @NotBlank
    @Size(max = 2048)
    @Column(name = "fotoKorisnika", nullable = false)
    private String fotoKorisnika;

    @Column(name = "googleId", unique = true)
    private String googleId;

    @Column(name = "prikazujObavijesti", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean prikazujObavijesti = true;

    // Many-to-Many with Zanr (genre)
    @ManyToMany
    @JoinTable(
            name = "korisnik_zanr",
            joinColumns = @JoinColumn(name = "IDKorisnika"),
            inverseJoinColumns = @JoinColumn(name = "IDZanra")
    )
    private Set<Zanr> omiljeniZanrovi = new HashSet<>();

    // Many-to-Many with Izvodac (artist)
    @ManyToMany
    @JoinTable(
            name = "voliSlusati",
            joinColumns = @JoinColumn(name = "IDKorisnika"),
            inverseJoinColumns = @JoinColumn(name = "IDIzvodaca")
    )
    private Set<Izvodac> omiljeniIzvodaci = new HashSet<>();

    // One-to-Many with Oglas (advertisement)
    @OneToMany(mappedBy = "korisnik", fetch = FetchType.LAZY)
    private Set<Oglas> oglasi = new HashSet<>();

    // Many-to-Many with Role (user roles)
    @ManyToMany
    @JoinTable(
            name = "korisnikRoles",
            joinColumns = @JoinColumn(name = "IDKorisnika"),
            inverseJoinColumns = @JoinColumn(name = "IDRole")
    )
    private Set<Role> roles = new HashSet<>();

    // One-to-Many with Transakcija for Ponuda
    @OneToMany(mappedBy = "korisnikPonuda", fetch = FetchType.LAZY)
    private Set<Transakcija> transakcijePonuda = new HashSet<>();

    // One-to-Many with Transakcija for Oglas
    @OneToMany(mappedBy = "korisnikOglas", fetch = FetchType.LAZY)
    private Set<Transakcija> transakcijeOglas = new HashSet<>();

    // Helper method to get favorite artist IDs
    public Set<Long> getOmiljeniIzvodaciIds() {
        return omiljeniIzvodaci.stream()
                .map(Izvodac::getIdIzvodaca)
                .collect(Collectors.toSet());
    }

    // Helper method to get advertisement IDs
    public Set<Long> getOglasiIds() {
        return oglasi.stream()
                .map(Oglas::getIdOglasa)
                .collect(Collectors.toSet());
    }

    // Helper method to get favorite genre IDs
    public Set<Long> getOmiljeniZanroviIds() {
        return omiljeniZanrovi.stream()
                .map(Zanr::getIdZanra)
                .collect(Collectors.toSet());
    }

    // Helper method to get role IDs
    public Set<Long> getRoleIds() {
        return roles.stream()
                .map(Role::getIdRole)
                .collect(Collectors.toSet());
    }

    // Helper method to get Ponuda transaction IDs
    public Set<Long> getTransakcijePonudaIds() {
        return transakcijePonuda.stream()
                .map(Transakcija::getIdTransakcije)
                .collect(Collectors.toSet());
    }

    // Helper method to get Oglas transaction IDs
    public Set<Long> getTransakcijeOglasIds() {
        return transakcijeOglas.stream()
                .map(Transakcija::getIdTransakcije)
                .collect(Collectors.toSet());
    }
}

package hr.unizg.fer.ticket4ticket.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "ULAZNICA")
public class Ulaznica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUlaznice", nullable = false)
    private Long idUlaznice;

    @NotNull
    @Column(name = "datumKoncerta", nullable = false)
    private LocalDate datumKoncerta;

    @NotNull
    @Column(name = "lokacijaKoncerta", nullable = false, length = 50)
    private String lokacijaKoncerta;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "odabranaZona", nullable = false, length = 50)
    private OdabranaZona odabranaZona;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "vrstaUlaznice", nullable = false, length = 50)
    private VrstaUlaznice vrstaUlaznice;

    @Column(name = "urlSlika", length = 255)
    private String urlSlika;

    @Column(name = "urlInfo", length = 255)
    private String urlInfo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.NEPREUZETA;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idKorisnika", nullable = true)
    private Korisnik korisnik;

    // Many-to-Many relationship with Izvodac
    @ManyToMany(mappedBy = "ulaznice")
    private Set<Izvodac> izvodaci = new HashSet<>();

    // One-to-Many relationship with Oglas
    @OneToMany(mappedBy = "ulaznica", fetch = FetchType.LAZY)
    private Set<Oglas> oglasi = new HashSet<>();

    // Add a field for sifraUlaznice
    @Column(name = "sifraUlaznice", nullable = false, unique = true)
    private String sifraUlaznice;


    @OneToMany(mappedBy = "ulaznicaPonuda", fetch = FetchType.LAZY)
    private Set<Transakcija> transakcijePonuda = new HashSet<>();

    @OneToMany(mappedBy = "ulaznicaOglas", fetch = FetchType.LAZY)
    private Set<Transakcija> transakcijeOglas = new HashSet<>();

    // Enum for status
    public enum Status {
        NEPREUZETA,
        PREUZETA
    }

    // Method to get IDs of Izvodac
    public Set<Long> getIzvodaciIds() {
        return izvodaci.stream()
                .map(Izvodac::getIdIzvodaca)
                .collect(Collectors.toSet());
    }

    // Method to get IDs of Oglas
    public Set<Long> getOglasiIds() {
        return oglasi.stream()
                .map(Oglas::getIdOglasa)
                .collect(Collectors.toSet());
    }

    // Ensure status is not null before persisting
    @PrePersist
    private void ensureStatusIsNotNull() {
        if (this.status == null) {
            this.status = Status.NEPREUZETA;
        }
    }

    // Enumeration for VrstaUlaznice
    public enum VrstaUlaznice {
        VIP,
        PREMIUM,
        STANDARD,
        FAMILY,
        STUDENT
    }

    // Enumeration for OdabranaZona
    public enum OdabranaZona {
        VIP_LOZA,
        TRIBINA_A,
        TRIBINA_B,
        PARTER,
        GALERIJA
    }

    // Method to get IDs of Transakcija related to 'ponuda'
    public Set<Long> getTransakcijePonudaIds() {
        return transakcijePonuda.stream()
                .map(Transakcija::getIdTransakcije)  // Assuming Transakcija has a method getIdTransakcije
                .collect(Collectors.toSet());
    }

    // Method to get IDs of Transakcija related to 'oglas'
    public Set<Long> getTransakcijeOglasIds() {
        return transakcijeOglas.stream()
                .map(Transakcija::getIdTransakcije)  // Assuming Transakcija has a method getIdTransakcije
                .collect(Collectors.toSet());
    }
}


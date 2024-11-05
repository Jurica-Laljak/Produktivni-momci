package com.ticket4ticket.backend.entity;

import jakarta.persistence.*; // Change this import
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "KORISNIK")
public class Korisnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDKorisnika", nullable = false)
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

    //nullable should be false
    @Column(name = "brMobKorisnika", nullable = true, unique = true)
    private String brMobKorisnika;

    //nullable should be false
    @Column(name = "OIBKorisnika", nullable = true, unique = true)
    private String oibKorisnika;

    @NotBlank
    @Size(max = 2048)
    @Column(name = "fotoKorisnika", nullable = false)
    private String fotoKorisnika;


    @Column(name = "googleId", unique = true)
    private String googleId;

    // Many-to-Many relationship with Izvodac
    @ManyToMany
    @JoinTable(
            name = "voliSlusati",
            joinColumns = @JoinColumn(name = "IDKorisnika"),
            inverseJoinColumns = @JoinColumn(name = "IDIzvodaca")
    )
    private Set<Izvodac> omiljeniIzvodaci = new HashSet<>();

    // One-to-Many relationship with Oglas
    @OneToMany(mappedBy = "korisnik", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // Specifies that this is the inverse side of the relationship
    private Set<Oglas> oglasi = new HashSet<>();


    // Method to get IDs of omiljeniIzvodaci
    public Set<Long> getOmiljeniIzvodaciIds() {
        return omiljeniIzvodaci.stream()
                .map(Izvodac::getIdIzvodaca) //Izvodac has a getIdIzvodaca() method
                .collect(Collectors.toSet());
    }

    // Method to get IDs of oglasi
    public Set<Long> getOglasiIds() {
        return oglasi.stream()
                .map(Oglas::getIdOglasa) //Oglas has a getIdOglasa() method
                .collect(Collectors.toSet());
    }




}
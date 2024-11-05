package hr.unizg.fer.ticket4ticket.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "KONCERT")
public class Koncert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDKoncerta", nullable = false)
    private Long idKoncerta;

    @NotNull
    @Column(name = "datumKoncerta", nullable = false)
    private LocalDate datumKoncerta;

    @NotNull
    @Column(name = "lokacijaKoncerta", nullable = false, length = 50)
    private String lokacijaKoncerta;

    // Many-to-Many relationship with Izvodac
    @ManyToMany(mappedBy = "koncerti") // This side will not own the relationship
    private Set<Izvodac> izvodaci = new HashSet<>();

    // One-to-Many relationship with Oglas
    @OneToMany(mappedBy = "koncert", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Oglas> oglasi = new HashSet<>();

    // Method to get IDs of Izvodac
    public Set<Long> getIzvodaciIds() {
        return izvodaci.stream()
                .map(Izvodac::getIdIzvodaca) // Assuming Izvodac has a getIdIzvodaca() method
                .collect(Collectors.toSet());
    }

    // Method to get IDs of Oglas
    public Set<Long> getOglasiIds() {
        return oglasi.stream()
                .map(Oglas::getIdOglasa) // Assuming Oglas has a getIdOglasa() method
                .collect(Collectors.toSet());
    }
}

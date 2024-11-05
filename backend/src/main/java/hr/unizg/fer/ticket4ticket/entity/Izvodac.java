package hr.unizg.fer.ticket4ticket.entity;

import jakarta.persistence.*; // Change this import
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "IZVODAC") // This maps the class to the "IZVODAC" table
public class Izvodac {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment for ID
    @Column(name = "IDIzvodaca") // This maps to the "IDIzvodaca" column
    private Long idIzvodaca; // Use Integer to match INT type in SQL

    @NotBlank
    @Column(name = "imeIzvodaca", nullable = false, length = 50) // Maps to "imeIzvodaca"
    private String imeIzvodaca;

    @NotBlank
    @Column(name = "prezimeIzvodaca", nullable = false, length = 50) // Maps to "prezimeIzvodaca"
    private String prezimeIzvodaca;

    @NotNull
    @Column(name = "starostIzvodaca", nullable = false) // Maps to "starostIzvodaca"
    private Integer starostIzvodaca;

    @NotBlank
    @Column(name = "zanrIzvodaca", nullable = false, length = 50) // Maps to "zanrIzvodaca"
    private String zanrIzvodaca;

    @NotBlank
    @Column(name = "fotoIzvodaca", nullable = false, length = 2048) // Maps to "fotoIzvodaca"
    private String fotoIzvodaca;


    @ManyToMany(mappedBy = "omiljeniIzvodaci")
    private Set<Korisnik> korisniciKojiSlusaju = new HashSet<>();

    // Many-to-Many relationship with Koncert
    @ManyToMany
    @JoinTable(
            name = "odrzavaKoncert", // Join table name
            joinColumns = @JoinColumn(name = "IDIzvodaca"), // Column in join table that references Izvodac
            inverseJoinColumns = @JoinColumn(name = "IDKoncerta") // Column in join table that references Koncert
    )
    private Set<Koncert> koncerti = new HashSet<>();

    // Method to get IDs of omiljeniIzvodaci
    public Set<Long> getKoncertiIds() {
        return koncerti.stream()
                .map(Koncert::getIdKoncerta) // Assuming Izvodac has a getIdIzvodaca() method
                .collect(Collectors.toSet());
    }

}

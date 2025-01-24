package hr.unizg.fer.ticket4ticket.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "IZVODAC")
public class Izvodac {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idIzvodaca")
    private Long idIzvodaca;

    @NotBlank
    @Column(name = "imeIzvodaca", nullable = false, length = 50)
    private String imeIzvodaca;

    @NotBlank
    @Column(name = "prezimeIzvodaca", nullable = false, length = 50)
    private String prezimeIzvodaca;

    @NotNull
    @Column(name = "starostIzvodaca", nullable = false)
    private Integer starostIzvodaca;

    @NotBlank
    @Column(name = "fotoIzvodaca", nullable = false, length = 2048)
    private String fotoIzvodaca;

    @ManyToMany(mappedBy = "omiljeniIzvodaci")
    private Set<Korisnik> korisniciKojiSlusaju = new HashSet<>();


    // Many-to-One relationship with Zanr
    @ManyToOne
    @JoinColumn(name = "zanr_id") // Foreign key column in Izvodac table
    private Zanr zanrIzvodaca; // Reference to the Zanr entity

    @ManyToMany
    @JoinTable(
            name = "izvodac_ulaznica",
            joinColumns = @JoinColumn(name = "IDIzvodaca"),
            inverseJoinColumns = @JoinColumn(name = "IDUlaznice")
    )
    private Set<Ulaznica> ulaznice = new HashSet<>(); // Rename from koncerti to ulaznice


    public Set<Long> getUlazniceIds() {
        return ulaznice.stream()
                .map(Ulaznica::getIdUlaznice) // Update to new method name in Ulaznica
                .collect(Collectors.toSet());
    }

    // Getter for zanr ID
    public Long getZanrId() {
        return this.zanrIzvodaca != null ? this.zanrIzvodaca.getIdZanra() : null;
    }

    // Setter for zanr based on ID
    public void setZanrId(Long zanrId) {
        if (zanrId != null) {
            // Here you would typically look up the Zanr entity from the database
            // For example, you might call a service to fetch the Zanr by ID
            Zanr zanr = new Zanr(); // Create a new instance for now
            zanr.setIdZanra(zanrId); // Set the ID
            this.zanrIzvodaca = zanr; // Assign it to this instance
        } else {
            this.zanrIzvodaca = null; // If ID is null, clear the reference
        }
    }
}
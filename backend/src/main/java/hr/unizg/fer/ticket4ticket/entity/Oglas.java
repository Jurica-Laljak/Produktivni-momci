package hr.unizg.fer.ticket4ticket.entity;

import jakarta.persistence.*; // Import for JPA annotations
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "OGLAS") // Maps this class to the "OGLAS" table
@Builder
public class Oglas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment for ID
    @Column(name = "idOglasa", nullable = false)
    private Long idOglasa; // Use Long to match the SQL INT type

    @Enumerated(EnumType.STRING) // Map enum to its string representation in the database
    @Column(name = "status", nullable = false, length = 50)
    private Status status = Status.AKTIVAN; // Default to AKTIVAN

    // Many-to-One relationship with Ulaznica
    @ManyToOne
    @JoinColumn(name = "IDUlaznice", nullable = true, foreignKey = @ForeignKey(name = "fk_oglas_ulaznica"))
    private Ulaznica ulaznica;

    // Many-to-One relationship with Korisnik
    @ManyToOne
    @JoinColumn(name = "IDKorisnika", nullable = false, foreignKey = @ForeignKey(name = "fk_oglas_korisnik"))
    private Korisnik korisnik;

    @OneToMany(mappedBy = "oglas", fetch = FetchType.LAZY)
    private Set<Transakcija> transakcije = new HashSet<>();

    @OneToMany(mappedBy = "oglas", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Obavijest> obavijesti;


    @Column(name = "prodaja", nullable = false)
    private boolean prodaja = false; // Default value is false

    // Enum for status
    public enum Status {
        AKTIVAN,
        NEAKTIVAN
    }

    // Helper method to get transaction IDs
    public Set<Long> getTransakcijeIds() {
        return transakcije.stream()
                .map(Transakcija::getIdTransakcije)
                .collect(Collectors.toSet());
    }
}

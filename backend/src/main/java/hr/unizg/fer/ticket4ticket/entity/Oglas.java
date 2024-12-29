package hr.unizg.fer.ticket4ticket.entity;

import jakarta.persistence.*; // Import for JPA annotations
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "OGLAS") // Maps this class to the "OGLAS" table
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
    @JoinColumn(name = "IDUlaznice", nullable = false, foreignKey = @ForeignKey(name = "fk_oglas_ulaznica"))
    private Ulaznica ulaznica;

    // Many-to-One relationship with Korisnik
    @ManyToOne
    @JoinColumn(name = "IDKorisnika", nullable = false, foreignKey = @ForeignKey(name = "fk_oglas_korisnik"))
    private Korisnik korisnik;

    // Enum for status
    public enum Status {
        AKTIVAN,
        NEAKTIVAN
    }
}
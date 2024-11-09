package hr.unizg.fer.ticket4ticket.entity;

import jakarta.persistence.*; // Import for JPA annotations
import jakarta.validation.constraints.NotBlank;
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


    @NotBlank
    @Column(name = "status", nullable = false, length = 50)
    private String status;

    // Many-to-One relationship with Ulaznica
    @ManyToOne // Specifies that this is a many-to-one relationship
    @JoinColumn(name = "IDUlaznice", nullable = false, foreignKey = @ForeignKey(name = "fk_oglas_ulaznica")) // Foreign key column
    private Ulaznica ulaznica;

    // Many-to-One relationship with Korisnik
    @ManyToOne // Specifies that this is a many-to-one relationship
    @JoinColumn(name = "IDKorisnika", nullable = false, foreignKey = @ForeignKey(name = "fk_oglas_korisnik")) // Foreign key column
    private Korisnik korisnik;

}

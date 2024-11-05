package com.ticket4ticket.backend.entity;


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
    @Column(name = "IDOglasa", nullable = false)
    private Long idOglasa; // Use Long to match the SQL INT type

    @NotBlank
    @Column(name = "odabranaZona", nullable = false, length = 50)
    private String odabranaZona;

    @NotBlank
    @Column(name = "vrstaUlaznice", nullable = false, length = 50)
    private String vrstaUlaznice;

    @NotBlank
    @Column(name = "status", nullable = false, length = 50)
    private String status;

    // Many-to-One relationship with Koncert
    @ManyToOne // Specifies that this is a many-to-one relationship
    @JoinColumn(name = "IDKoncerta", nullable = false, foreignKey = @ForeignKey(name = "fk_oglas_koncert")) // Foreign key column
    private Koncert koncert;

    // Many-to-One relationship with Korisnik
    @ManyToOne // Specifies that this is a many-to-one relationship
    @JoinColumn(name = "IDKorisnika", nullable = false, foreignKey = @ForeignKey(name = "fk_oglas_korisnik")) // Foreign key column
    private Korisnik korisnik;

}

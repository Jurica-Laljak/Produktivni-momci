package  hr.unizg.fer.ticket4ticket.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "zanr")
public class Zanr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idZanra;

    @Column(name = "imeZanra", nullable = false)
    private String imeZanra;

    @Column(name = "slikaZanra")
    private String slikaZanra;

    // Many-to-Many relationship with Korisnik
    @ManyToMany(mappedBy = "omiljeniZanrovi")
    private Set<Korisnik> korisnici = new HashSet<>();

    // One-to-Many relationship with Izvodac
    @OneToMany(mappedBy = "zanrIzvodaca", cascade = CascadeType.ALL)
    private Set<Izvodac> izvodaci = new HashSet<>();
}
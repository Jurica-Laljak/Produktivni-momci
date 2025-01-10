package hr.unizg.fer.ticket4ticket.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TRANSAKCIJA")
public class Transakcija {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTransakcije", nullable = false)
    private Long idTransakcije;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUlaznicaPonuda", nullable = false)
    private Ulaznica ulaznicaPonuda;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUlaznicaOglas", nullable = false)
    private Ulaznica ulaznicaOglas;

    // Add the relationship to Korisnik for idKorisnikPonuda
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idKorisnikPonuda", nullable = false)
    private Korisnik korisnikPonuda;

    // Add the relationship to Korisnik for idKorisnikOglas
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idKorisnikOglas", nullable = false)
    private Korisnik korisnikOglas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idOglas", nullable = false)
    private Oglas oglas;

    @Enumerated(EnumType.STRING)
    @Column(name = "statusTransakcije", nullable = false)
    private StatusTransakcije statusTransakcije;

    @NotNull
    @Column(name = "datumTransakcije", nullable = false)
    private LocalDateTime datumTransakcije;

    @OneToMany(mappedBy = "transakcija", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Obavijest> obavijesti;

    public enum StatusTransakcije {
        CEKA_POTVRDU,
        PROVEDENA,
        ODBIJENA
    }
}

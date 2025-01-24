package hr.unizg.fer.ticket4ticket.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "OBAVIJEST")
public class Obavijest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idObavijesti", nullable = false)
    private Long idObavijesti;

    @ManyToOne
    @JoinColumn(name = "IDZanra", nullable = true, foreignKey = @ForeignKey(name = "fk_obavijest_zanr"))
    private Zanr zanr;

    @ManyToOne
    @JoinColumn(name = "IDOglasa", foreignKey = @ForeignKey(name = "fk_obavijest_oglas"))
    private Oglas oglas;

    @ManyToOne
    @JoinColumn(name = "IDTransakcije", foreignKey = @ForeignKey(name = "fk_obavijest_transakcija"))
    private Transakcija transakcija;

    @Enumerated(EnumType.STRING)
    @Column(name = "obavijestTip", nullable = false)
    private ObavijestTip obavijestTip;

    @Column(name = "korisnikId", nullable = false) // Added korisnikId column
    private Long korisnikId;

    public enum ObavijestTip {
        ODBIO,
        PRIHVATIO,
        PONUDIO,
        OGLAS
    }
}
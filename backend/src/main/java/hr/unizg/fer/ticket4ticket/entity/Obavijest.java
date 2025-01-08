
package hr.unizg.fer.ticket4ticket.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;

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

    @Column(name = "ttl", nullable = false)
    private Duration timeToLive;

    @ManyToOne
    @JoinColumn(name = "IDZanra", nullable = false, foreignKey = @ForeignKey(name = "fk_obavijest_zanr"))
    private Zanr zanr;

    @ManyToOne
    @JoinColumn(name = "IDOglasa", foreignKey = @ForeignKey(name = "fk_obavijest_oglas"))
    private Oglas oglas;

    @ManyToOne
    @JoinColumn(name = "IDTransakcije", foreignKey = @ForeignKey(name = "fk_obavijest_transakcija"))
    private Transakcija transakcija;

    @Column(name = "obavijest", nullable = false)
    private String obavijest;

    @Column(name = "obavijest_url")
    private String obavijest_url;
}

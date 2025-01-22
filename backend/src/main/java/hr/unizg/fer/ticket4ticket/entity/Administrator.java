package hr.unizg.fer.ticket4ticket.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ADMINISTRATOR")
public class Administrator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAdmin", nullable = false)
    private Long idAdmin;

    @Column(name = "emailAdmin", nullable = false, unique = true)
    private String emailAdmin;
}

package hr.unizg.fer.ticket4ticket.dto;

import hr.unizg.fer.ticket4ticket.entity.Obavijest;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ObavijestDto {

    private Long idObavijesti;
    private Long zanrId;
    private Long oglasId;
    private Long transakcijaId;
    private Long korisnikId;  // Added korisnikId

    private Obavijest.ObavijestTip obavijestType;  // Changed to the enum type
}
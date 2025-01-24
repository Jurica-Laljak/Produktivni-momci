package hr.unizg.fer.ticket4ticket.dto;

import lombok.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OglasDto {

    private Long idOglasa; // Use Long to match the SQL INT type
    private String status;
    private Long korisnikId;
    private Long ulaznicaId;
    private boolean prodaja;
    private Set<Long> transakcijeIds; // Set of transaction IDs
}

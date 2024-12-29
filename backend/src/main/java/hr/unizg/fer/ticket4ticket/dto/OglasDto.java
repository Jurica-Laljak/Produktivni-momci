package hr.unizg.fer.ticket4ticket.dto;

import lombok.*;

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
}

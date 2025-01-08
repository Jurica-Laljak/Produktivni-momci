package hr.unizg.fer.ticket4ticket.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ObavijestDto {

    private Long idObavijesti;
    private Long ttl; //
    private Long zanrId;
    private Long oglasId;
    private Long transakcijaId;
    private String obavijest_url;
    private String obavijest;
}

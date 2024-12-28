package  hr.unizg.fer.ticket4ticket.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ZanrDto {
    private Long idZanra;
    private String imeZanra;
    private String slikaZanra;
    private Set<Long> korisniciKojiSlusajuIds;
    private Set<Long> izvodaciIds;
}

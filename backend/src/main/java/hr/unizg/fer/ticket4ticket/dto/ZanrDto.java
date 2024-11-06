package  hr.unizg.fer.ticket4ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ZanrDto {
    private Long idZanra;
    private String imeZanra;
    private String slikaZanra;
    private Set<Long> korisniciKojiSlusajuIds;
    private Set<Long> izvodaciIds;
}

package hr.unizg.fer.ticket4ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KoncertDto {

    private Long idKoncerta;
    private LocalDate datumKoncerta;
    private String lokacijaKoncerta;
    private Set<Long> izvodaciIds;
    private Set<Long> oglasiIds;
}

package hr.unizg.fer.ticket4ticket.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
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
public class UlaznicaDto {

    private Long idKoncerta;
    private LocalDate datumKoncerta;
    private String lokacijaKoncerta;
    private String odabranaZona;
    private String vrstaUlaznice;
    private Set<Long> izvodaciIds;
    private Set<Long> oglasiIds;
}

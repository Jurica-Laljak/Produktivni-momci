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
public class UlaznicaDto {

    private Long idUlaznice;
    private LocalDate datumKoncerta;
    private String lokacijaKoncerta;
    private String odabranaZona;
    private String vrstaUlaznice;
    private String urlSlika;
    private String urlInfo;
    private String status; // Status as a String (NEPREUZETA, PREUZETA)
    private Long idKorisnika; // ID of the associated user (if claimed)
    private String sifraUlaznice;
    private Set<Long> izvodaciIds;
    private Set<Long> oglasiIds;
}


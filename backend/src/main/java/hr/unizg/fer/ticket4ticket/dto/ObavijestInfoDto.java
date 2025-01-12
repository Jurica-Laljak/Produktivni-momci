package hr.unizg.fer.ticket4ticket.dto;


import hr.unizg.fer.ticket4ticket.entity.Obavijest;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ObavijestInfoDto {

    private Long idObavijesti;
    private Long zanrIdObavijest;
    private Long oglasIdObavijest;
    private Long transakcijaIdObavijest;
    private Long korisnikIdObavijest;  // Added korisnikId
    private Obavijest.ObavijestTip obavijestType;


    //Stuff you get from transakcijaIdObavijest
    private String korisnikOglasIme;
    private String korisnikOglasPrezime;
    private String korisnikPonudaIme;
    private String korisnikPonudaPrezime;
    private UlaznicaDto ulaznicaPonuda;
    private UlaznicaDto ulaznicaOglas;

    //Stuff you get from oglasIdObavijest
    private String autorOglasIme;
    private String autorOglasPrezime;
    private UlaznicaDto ulaznicaPreporuka;

    private List<IzvodacDto> izvodaci;



}
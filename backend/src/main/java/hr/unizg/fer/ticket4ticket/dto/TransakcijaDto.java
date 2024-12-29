package hr.unizg.fer.ticket4ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransakcijaDto {

    private Long idTransakcije;
    private Long idUlaznicaPonuda;
    private Long idUlaznicaOglas;
    private Long idKorisnikPonuda;
    private Long idKorisnikOglas;
    private String statusTransakcije;
    private Long idOglas;
    private LocalDateTime datumTransakcije;
}
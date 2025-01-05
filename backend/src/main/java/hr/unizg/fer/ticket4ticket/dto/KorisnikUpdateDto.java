package hr.unizg.fer.ticket4ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KorisnikUpdateDto {
    private String imeKorisnika;
    private String prezimeKorisnika;
    private String brMobKorisnika;
}

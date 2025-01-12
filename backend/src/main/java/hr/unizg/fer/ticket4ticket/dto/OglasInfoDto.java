package hr.unizg.fer.ticket4ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OglasInfoDto {

    // Fields from OglasDto
    private Long idOglasa;
    private String status;
    private Long korisnikId;
    private Long ulaznicaId;

    // Fields from UlaznicaDto
    private LocalDate datumKoncerta;
    private String lokacijaKoncerta;
    private String odabranaZona;
    private String vrstaUlaznice;
    private String urlSlika;
    private String urlInfo;
    private String statusUlaznice; // Status of the ticket (e.g., NEPREUZETA, PREUZETA)
    private String sifraUlaznice;

    // Fields from KorisnikDto (subset)
    private Long idKorisnika;
    private String imeKorisnika;
    private String prezimeKorisnika;
    private String emailKorisnika;
    private String brMobKorisnika;

    // List of IzvodacDtos (without korisniciKojiSlusajuIds)
    private Set<IzvodacDto> izvodaci;
}
package hr.unizg.fer.ticket4ticket.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KorisnikDto {

    private Long idKorisnika;
    private String imeKorisnika; //x
    private String prezimeKorisnika; //x
    private String emailKorisnika;
    private String brMobKorisnika; //x
    private String fotoKorisnika;
    private String googleId;
    private Set<Long> omiljeniIzvodaciIds;
    private Set<Long> oglasiIds;
    private Set<Long> omiljeniZanroviIds;
    private Set<Long> roleIds;
}

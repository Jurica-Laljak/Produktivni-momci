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
    private String imeKorisnika;
    private String prezimeKorisnika;
    private String emailKorisnika;
    private String brMobKorisnika;
    private String fotoKorisnika;
    private String googleId;
    private boolean prikazujObavijesti; // New field
    private Set<Long> omiljeniIzvodaciIds;
    private Set<Long> oglasiIds;
    private Set<Long> omiljeniZanroviIds;
    private Set<Long> roleIds;
    private Set<Long> transakcijePonudaIds; // Add this if you need transaction IDs for "ponuda"
    private Set<Long> transakcijeOglasIds;
}
package com.ticket4ticket.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KorisnikDto {

    private Long idKorisnika;
    private String imeKorisnika;
    private String prezimeKorisnika;
    private String emailKorisnika;
    private String brMobKorisnika;
    private String oibKorisnika;
    private String fotoKorisnika;
    private String googleId;
    private Set<Long> omiljeniIzvodaciIds;
    private Set<Long> oglasiIds;
}

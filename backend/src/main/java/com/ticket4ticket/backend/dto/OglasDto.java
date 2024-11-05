package com.ticket4ticket.backend.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OglasDto {

    private Long idOglasa; // Use Long to match the SQL INT type
    private String odabranaZona;
    private String vrstaUlaznice;
    private String status;
    private Long korisnikId;
    private Long koncertId;
}

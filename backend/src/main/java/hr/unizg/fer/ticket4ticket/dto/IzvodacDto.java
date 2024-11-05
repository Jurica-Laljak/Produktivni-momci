package hr.unizg.fer.ticket4ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IzvodacDto {

    private Long idIzvodaca; // Use Integer to match INT type in SQL
    private String imeIzvodaca;
    private String prezimeIzvodaca;
    private Integer starostIzvodaca;
    private String zanrIzvodaca;
    private String fotoIzvodaca;
    private Set<Long> korisniciKojiSlusajuIds;
}

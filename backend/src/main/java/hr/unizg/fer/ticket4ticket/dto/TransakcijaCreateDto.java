package hr.unizg.fer.ticket4ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransakcijaCreateDto {

    private Long ulaznicaPonudaId;
    private Long ulaznicaOglasId;
    private Long oglasId;
}

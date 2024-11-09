package  hr.unizg.fer.ticket4ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OglasFilterDto {
    private String imeIzvodaca;        // Filter by performer's first name
    private String prezimeIzvodaca;    // Filter by performer's last name
}
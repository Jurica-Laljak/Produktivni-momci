package hr.unizg.fer.ticket4ticket.service;

import hr.unizg.fer.ticket4ticket.dto.ZanrDto;
import hr.unizg.fer.ticket4ticket.entity.Zanr;
import hr.unizg.fer.ticket4ticket.repository.ZanrRepository;
import hr.unizg.fer.ticket4ticket.service.impl.ZanrServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ZanrServiceTests {

    @Mock
    ZanrRepository zanrRepository;

    @InjectMocks
    ZanrServiceImpl zanrService;

    @Test
    public void zanrService_getAllZanrovi_returnsListOfZanrovi() {
        Zanr zanr1 = Zanr.builder().imeZanra("Rock")
                .korisnici(new HashSet<>()).izvodaci(new HashSet<>()).build();
        Zanr zanr2 = Zanr.builder().imeZanra("Pop")
                .korisnici(new HashSet<>()).izvodaci(new HashSet<>()).build();

        when(zanrRepository.findAll()).thenReturn(List.of(zanr1, zanr2));

        List<ZanrDto> zanrovi = zanrService.getAllZanrovi();

        Assertions.assertNotNull(zanrovi);
        Assertions.assertEquals(2, zanrovi.size());
    }
}

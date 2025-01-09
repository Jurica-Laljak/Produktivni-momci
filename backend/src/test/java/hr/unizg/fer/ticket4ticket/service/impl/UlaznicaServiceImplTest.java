package hr.unizg.fer.ticket4ticket.service.impl;

import hr.unizg.fer.ticket4ticket.dto.UlaznicaDto;
import hr.unizg.fer.ticket4ticket.entity.Ulaznica;
import hr.unizg.fer.ticket4ticket.repository.UlaznicaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UlaznicaServiceImplTest {

    @Mock
    private UlaznicaRepository ulaznicaRepository;

    @InjectMocks
    private UlaznicaServiceImpl ulaznicaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUlaznicaById_Success() {

        Long ulaznicaId = 1L;
        Ulaznica ulaznica = new Ulaznica();
        ulaznica.setIdUlaznice(ulaznicaId);
        ulaznica.setDatumKoncerta(LocalDate.of(2025, 1, 15));
        ulaznica.setLokacijaKoncerta("Arena Zagreb");
        ulaznica.setOdabranaZona(Ulaznica.OdabranaZona.PARTER);
        ulaznica.setVrstaUlaznice(Ulaznica.VrstaUlaznice.STANDARD);
        ulaznica.setUrlSlika("http://example.com/slika.jpg");
        ulaznica.setUrlInfo("http://example.com/info");
        ulaznica.setStatus(Ulaznica.Status.NEPREUZETA);
        ulaznica.setSifraUlaznice("ABC123");

        // kad se pozove ulaznicaRepository.findById(ulaznicaId) => vraća se ulaznica
        when(ulaznicaRepository.findById(ulaznicaId)).thenReturn(Optional.of(ulaznica));

        // When
        UlaznicaDto result = ulaznicaService.getUlaznicaById(ulaznicaId);

        // Then
        assertEquals(ulaznicaId, result.getIdUlaznice());
        assertEquals(LocalDate.of(2025, 1, 15), result.getDatumKoncerta());
        assertEquals("Arena Zagreb", result.getLokacijaKoncerta());
        assertEquals("PARTER", result.getOdabranaZona());
        assertEquals("STANDARD", result.getVrstaUlaznice());
        assertEquals("http://example.com/slika.jpg", result.getUrlSlika());
        assertEquals("http://example.com/info", result.getUrlInfo());
        assertEquals("NEPREUZETA", result.getStatus());
        assertEquals("ABC123", result.getSifraUlaznice());
    }
}

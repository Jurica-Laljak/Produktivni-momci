package hr.unizg.fer.ticket4ticket.service.impl;

import hr.unizg.fer.ticket4ticket.dto.UlaznicaDto;
import hr.unizg.fer.ticket4ticket.entity.Ulaznica;
import hr.unizg.fer.ticket4ticket.repository.UlaznicaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.provider.Arguments;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

class UlaznicaServiceImplTest {

    @Mock
    private UlaznicaRepository ulaznicaRepository;

    @InjectMocks
    private UlaznicaServiceImpl ulaznicaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("provideUlaznicaTestDataWithFailure")
    void testGetUlaznicaById(Long ulaznicaId, Ulaznica ulaznica, UlaznicaDto expectedDto) {

        when(ulaznicaRepository.findById(ulaznicaId)).thenReturn(Optional.ofNullable(ulaznica));

            UlaznicaDto result = ulaznicaService.getUlaznicaById(ulaznicaId);

            assertNotNull(result);
            assertEquals(expectedDto.getIdUlaznice(), result.getIdUlaznice());
            assertEquals(expectedDto.getDatumKoncerta(), result.getDatumKoncerta());
            assertEquals(expectedDto.getLokacijaKoncerta(), result.getLokacijaKoncerta());
            assertEquals(expectedDto.getOdabranaZona(), result.getOdabranaZona());
            assertEquals(expectedDto.getVrstaUlaznice(), result.getVrstaUlaznice());
            assertEquals(expectedDto.getUrlSlika(), result.getUrlSlika());
            assertEquals(expectedDto.getUrlInfo(), result.getUrlInfo());
            assertEquals(expectedDto.getStatus(), result.getStatus());
            assertEquals(expectedDto.getSifraUlaznice(), result.getSifraUlaznice());

    }

    static Stream<Arguments> provideUlaznicaTestDataWithFailure() {
        return Stream.of(
                // uspješan test
                Arguments.of(
                        1L,
                        createUlaznica(1L, LocalDate.of(2025, 1, 15), "Arena Zagreb", "PARTER", "STANDARD",
                                "http://example.com/slika1.jpg", "http://example.com/info1", "NEPREUZETA", "12345"),
                        createUlaznicaDto(1L, LocalDate.of(2025, 1, 15), "Arena Zagreb", "PARTER", "STANDARD",
                                "http://example.com/slika1.jpg", "http://example.com/info1", "NEPREUZETA", "12345")
                ),
                // neuspješan test
                Arguments.of(
                        1L,
                        null,
                        createUlaznica(1L, LocalDate.of(2025, 1, 15), "Arena Zagreb", "PARTER", "STANDARD",
                                "http://example.com/slika1.jpg", "http://example.com/info1", "NEPREUZETA", "12345")
                )
        );
    }


    private static Ulaznica createUlaznica(Long id, LocalDate datum, String lokacija, String zona, String vrsta,
                                           String slika, String info, String status, String sifra) {
        Ulaznica ulaznica = new Ulaznica();
        ulaznica.setIdUlaznice(id);
        ulaznica.setDatumKoncerta(datum);
        ulaznica.setLokacijaKoncerta(lokacija);
        ulaznica.setOdabranaZona(Ulaznica.OdabranaZona.valueOf(zona));
        ulaznica.setVrstaUlaznice(Ulaznica.VrstaUlaznice.valueOf(vrsta));
        ulaznica.setUrlSlika(slika);
        ulaznica.setUrlInfo(info);
        ulaznica.setStatus(Ulaznica.Status.valueOf(status));
        ulaznica.setSifraUlaznice(sifra);
        return ulaznica;
    }

    private static UlaznicaDto createUlaznicaDto(Long id, LocalDate datum, String lokacija, String zona, String vrsta,
                                                 String slika, String info, String status, String sifra) {
        UlaznicaDto ulaznicaDto = new UlaznicaDto();
        ulaznicaDto.setIdUlaznice(id);
        ulaznicaDto.setDatumKoncerta(datum);
        ulaznicaDto.setLokacijaKoncerta(lokacija);
        ulaznicaDto.setOdabranaZona(zona);
        ulaznicaDto.setVrstaUlaznice(vrsta);
        ulaznicaDto.setUrlSlika(slika);
        ulaznicaDto.setUrlInfo(info);
        ulaznicaDto.setStatus(status);
        ulaznicaDto.setSifraUlaznice(sifra);
        return ulaznicaDto;
    }
}

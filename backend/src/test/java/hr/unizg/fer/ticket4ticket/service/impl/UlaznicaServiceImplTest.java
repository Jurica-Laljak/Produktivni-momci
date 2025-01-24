package hr.unizg.fer.ticket4ticket.service.impl;

import hr.unizg.fer.ticket4ticket.dto.UlaznicaDto;
import hr.unizg.fer.ticket4ticket.entity.Ulaznica;
import hr.unizg.fer.ticket4ticket.entity.Korisnik;
import hr.unizg.fer.ticket4ticket.exception.ResourceNotFoundException;
import hr.unizg.fer.ticket4ticket.mapper.UlaznicaMapper;
import hr.unizg.fer.ticket4ticket.repository.UlaznicaRepository;
import hr.unizg.fer.ticket4ticket.repository.KorisnikRepository;
import hr.unizg.fer.ticket4ticket.service.IzvodacService;
import hr.unizg.fer.ticket4ticket.service.impl.UlaznicaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.*;

import static hr.unizg.fer.ticket4ticket.entity.Ulaznica.OdabranaZona.TRIBINA_A;
import static hr.unizg.fer.ticket4ticket.entity.Ulaznica.OdabranaZona.TRIBINA_B;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UlaznicaServiceImplTest {

    @Mock
    private UlaznicaRepository ulaznicaRepository;

    @Mock
    private KorisnikRepository korisnikRepository;

    @Mock
    private UlaznicaMapper ulaznicaMapper;

    @InjectMocks
    private UlaznicaServiceImpl ulaznicaService;

    private Korisnik korisnik;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        korisnik = new Korisnik();
        korisnik.setIdKorisnika(1L);
        korisnik.setImeKorisnika("Marko");
        korisnik.setPrezimeKorisnika("Marković");
    }

    @Test
    public void testGetUlazniceByIdKorisnikaMultipleTickets() {
        // Arrange
        List<Ulaznica> ulaznice = new ArrayList<>();
        Ulaznica ulaznica1 = new Ulaznica(1L, LocalDate.of(2025,1,18), "Arena Zagreb", TRIBINA_A,
                Ulaznica.VrstaUlaznice.STANDARD, null, null, Ulaznica.Status.NEPREUZETA,
                korisnik, new HashSet<>(), new HashSet<>(), "1", new HashSet<>(), new HashSet<>());
        Ulaznica ulaznica2 = new Ulaznica(2L, LocalDate.of(2025,10,12), "KD Vatroslav Lisinski", TRIBINA_B,
                Ulaznica.VrstaUlaznice.STUDENT, null, null, Ulaznica.Status.NEPREUZETA,
                korisnik, new HashSet<>(), new HashSet<>(), "2", new HashSet<>(), new HashSet<>());
        ulaznice.add(ulaznica1);
        ulaznice.add(ulaznica2);

        // Mocking the repository methods
        when(korisnikRepository.findById(1L)).thenReturn(Optional.of(korisnik)); // Mock korisnik repository
        when(ulaznicaRepository.findAllByKorisnik_IdKorisnika(1L)).thenReturn(ulaznice); // Mock ulaznica repository

        // Mocking the static method of UlaznicaMapper using MockedStatic
        try (MockedStatic<UlaznicaMapper> mockedStatic = Mockito.mockStatic(UlaznicaMapper.class)) {
            mockedStatic.when(() -> UlaznicaMapper.mapToUlaznicaDto(ulaznica1)).thenReturn(new UlaznicaDto(1L, LocalDate.of(2025,1,18), "Arena Zagreb",
                    "TRIBINA_A", "STANDARD", null, null, "NEPREUZETA", korisnik.getIdKorisnika(), "1", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>()));
            mockedStatic.when(() -> UlaznicaMapper.mapToUlaznicaDto(ulaznica2)).thenReturn(new UlaznicaDto(2L, LocalDate.of(2025,10,12), "KC Vatroslav Lisinski",
                    "TRIBINA_B", "STUDENT", null, null, "NEPREUZETA", korisnik.getIdKorisnika(), "2", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>()));

            // Act
            List<UlaznicaDto> result = ulaznicaService.getUlazniceByIdKorisnika(1L);

            // Assert
            assertNotNull(result);
            assertEquals(2, result.size());
            assertEquals(1L, result.get(0).getIdUlaznice());
            assertEquals(2L, result.get(1).getIdUlaznice());

            // Asserting the individual fields of the first ticket
            assertEquals(LocalDate.of(2025,1,18), result.get(0).getDatumKoncerta());
            assertEquals("Arena Zagreb", result.get(0).getLokacijaKoncerta());
            assertEquals("TRIBINA_A", result.get(0).getOdabranaZona());
            assertEquals("STANDARD", result.get(0).getVrstaUlaznice());
            assertEquals("NEPREUZETA", result.get(0).getStatus());
            assertEquals("1", result.get(0).getSifraUlaznice());

            // Asserting the individual fields of the second ticket
            assertEquals(LocalDate.of(2025,10,12), result.get(1).getDatumKoncerta());
            assertEquals("KC Vatroslav Lisinski", result.get(1).getLokacijaKoncerta());
            assertEquals("TRIBINA_B", result.get(1).getOdabranaZona());
            assertEquals("STUDENT", result.get(1).getVrstaUlaznice());
            assertEquals("NEPREUZETA", result.get(1).getStatus());
            assertEquals("2", result.get(1).getSifraUlaznice());
        }
    }


    @Test
    public void testGetUlazniceByIdKorisnikaSingleTicket() {
        // Arrange
        List<Ulaznica> ulaznice = new ArrayList<>();
        Ulaznica ulaznica = new Ulaznica(1L, LocalDate.of(2025,1,18), "Arena Zagreb", TRIBINA_A,
                Ulaznica.VrstaUlaznice.STANDARD, null, null, Ulaznica.Status.NEPREUZETA,
                korisnik, new HashSet<>(), new HashSet<>(), "1", new HashSet<>(), new HashSet<>());
        ulaznice.add(ulaznica);

        // Mocking the repository methods
        when(korisnikRepository.findById(1L)).thenReturn(Optional.of(korisnik)); // Mock korisnik repository
        when(ulaznicaRepository.findAllByKorisnik_IdKorisnika(1L)).thenReturn(ulaznice); // Mock ulaznica repository

        // Mocking the static method of UlaznicaMapper
        try (MockedStatic<UlaznicaMapper> mockedStatic = Mockito.mockStatic(UlaznicaMapper.class)) {
            mockedStatic.when(() -> UlaznicaMapper.mapToUlaznicaDto(ulaznica)).thenReturn(new UlaznicaDto(1L, LocalDate.of(2025,1,18), "Arena Zagreb",
                    "TRIBINA_A", "STANDARD", null, null, "NEPREUZETA", korisnik.getIdKorisnika(), "1", new HashSet<>(), new HashSet<>(), null, null));

            // Act
            List<UlaznicaDto> result = ulaznicaService.getUlazniceByIdKorisnika(1L);

            // Assert
            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(1L, result.get(0).getIdUlaznice());
            assertEquals(LocalDate.of(2025,1,18), result.get(0).getDatumKoncerta());
            assertEquals("Arena Zagreb", result.get(0).getLokacijaKoncerta());
            assertEquals("TRIBINA_A", result.get(0).getOdabranaZona());
            assertEquals("STANDARD", result.get(0).getVrstaUlaznice());
            assertEquals("NEPREUZETA", result.get(0).getStatus());
            assertEquals("1", result.get(0).getSifraUlaznice());
        }
    }

    @Test
    public void testGetUlazniceByIdKorisnikaNoTickets() {
        // Arrange
        List<Ulaznica> ulaznice = new ArrayList<>();

        when(korisnikRepository.findById(1L)).thenReturn(Optional.of(korisnik));
        when(ulaznicaRepository.findAllByKorisnik_IdKorisnika(1L)).thenReturn(ulaznice);

        // Act
        List<UlaznicaDto> result = ulaznicaService.getUlazniceByIdKorisnika(1L);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetUlazniceByIdKorisnikaUserNotFound() {
        // Arrange
        when(korisnikRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            ulaznicaService.getUlazniceByIdKorisnika(999L);
        });
    }
}

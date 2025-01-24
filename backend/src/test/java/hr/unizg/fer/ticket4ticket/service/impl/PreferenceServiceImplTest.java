package hr.unizg.fer.ticket4ticket.service.impl;

import hr.unizg.fer.ticket4ticket.dto.UlaznicaDto;
import hr.unizg.fer.ticket4ticket.entity.Korisnik;
import hr.unizg.fer.ticket4ticket.entity.Ulaznica;
import hr.unizg.fer.ticket4ticket.repository.KorisnikRepository;
import hr.unizg.fer.ticket4ticket.repository.UlaznicaRepository;
import hr.unizg.fer.ticket4ticket.service.OglasService;
import hr.unizg.fer.ticket4ticket.service.PreferenceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

class PreferenceServiceImplTest {

    @Mock
    private KorisnikRepository korisnikRepository;

    @Mock
    private UlaznicaRepository ulaznicaRepository;

    @Mock
    private OglasService oglasService;

    @InjectMocks
    private PreferenceServiceImpl preferenceService;

    private Korisnik korisnik;
    private Ulaznica ulaznica;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        korisnik = Korisnik.builder()
                .idKorisnika(1L)
                .imeKorisnika("Ime")
                .prezimeKorisnika("Prezime")
                .emailKorisnika("email")
                .brMobKorisnika("0912345678")
                .fotoKorisnika("https://example.com/image.jpg")
                .googleId("googleId")
                .prikazujObavijesti(true)
                .build();

        ulaznica = new Ulaznica();
        ulaznica.setIdUlaznice(1L);
        ulaznica.setDatumKoncerta(LocalDate.now());
        ulaznica.setLokacijaKoncerta("Arena Zagreb");
        ulaznica.setOdabranaZona(Ulaznica.OdabranaZona.PARTER);
        ulaznica.setVrstaUlaznice(Ulaznica.VrstaUlaznice.STANDARD);
        ulaznica.setUrlInfo("https://example.com/info");
        ulaznica.setUrlSlika("https://example.com/image.jpg");
        ulaznica.setSifraUlaznice("ABC123");
        ulaznica.setIzvodaci(new HashSet<>());
        ulaznica.setOglasi(new HashSet<>());
        ulaznica.setTransakcijeOglas(new HashSet<>());
        ulaznica.setTransakcijePonuda(new HashSet<>());
        ulaznica.setStatus(Ulaznica.Status.NEPREUZETA); // Initially available
    }

    @Test
    void testChangeUlaznicaStatusAndAssignUser_ValidCase() {
        when(ulaznicaRepository.findBySifraUlaznice("ABC123")).thenReturn(ulaznica);
        when(korisnikRepository.findById(1L)).thenReturn(Optional.of(korisnik));
        when(ulaznicaRepository.save(ulaznica)).thenReturn(ulaznica);

        UlaznicaDto result = preferenceService.changeUlaznicaStatusAndAssignUser("ABC123", 1L);

        assertNotNull(result);
        assertEquals(Ulaznica.Status.PREUZETA, ulaznica.getStatus());
        assertEquals(korisnik, ulaznica.getKorisnik());
    }

    @Test
    void testChangeUlaznicaStatusAndAssignUser_NonExistentUlaznica() {
        when(ulaznicaRepository.findBySifraUlaznice("DEF456")).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                preferenceService.changeUlaznicaStatusAndAssignUser("DEF456", 1L)
        );

        assertEquals("Ulaznica not found", exception.getMessage());
    }

    @Test
    void testChangeUlaznicaStatusAndAssignUser_AlreadyTakenUlaznica() {
        ulaznica.setStatus(Ulaznica.Status.PREUZETA); // Set as already taken

        when(ulaznicaRepository.findBySifraUlaznice("ABC123")).thenReturn(ulaznica);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                preferenceService.changeUlaznicaStatusAndAssignUser("ABC123", 1L)
        );

        assertEquals("Ulaznica is already marked as PREUZETA", exception.getMessage());
    }

    @Test
    void testChangeUlaznicaStatusAndAssignUser_NonExistentUser() {
        when(ulaznicaRepository.findBySifraUlaznice("ABC123")).thenReturn(ulaznica);
        when(korisnikRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                preferenceService.changeUlaznicaStatusAndAssignUser("ABC123", 999L)
        );

        assertEquals("User not found", exception.getMessage());
    }
}

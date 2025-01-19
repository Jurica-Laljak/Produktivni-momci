package hr.unizg.fer.ticket4ticket.controller;

import hr.unizg.fer.ticket4ticket.dto.*;
import hr.unizg.fer.ticket4ticket.entity.Obavijest;
import hr.unizg.fer.ticket4ticket.entity.Oglas;
import hr.unizg.fer.ticket4ticket.exception.ResourceNotFoundException;
import hr.unizg.fer.ticket4ticket.repository.OglasRepository;
import hr.unizg.fer.ticket4ticket.service.*;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PreferenceControllerTest {

    @InjectMocks
    private PreferenceController preferenceController;

    @Mock
    private PreferenceService preferenceService;

    @Mock
    private OglasService oglasService;

    @Mock
    private KorisnikService korisnikService;

    @Mock
    private TransakcijaService transakcijaService;

    @Mock
    private UlaznicaService ulaznicaService;

    @Mock
    private ObavijestService obavijestService;

    private UsernamePasswordAuthenticationToken token;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        token = new UsernamePasswordAuthenticationToken("testGoogleId", null);
    }

    @Test
    void testCreateOglasWithAllValidData() {
        OglasDto oglasDto = new OglasDto();
        oglasDto.setIdOglasa(1L);
        oglasDto.setUlaznicaId(1L);
        oglasDto.setStatus("AKTIVAN");
        oglasDto.setKorisnikId(1L);

        KorisnikDto korisnikDto = new KorisnikDto(1L, "", "", "", "", "", "testGoogleId", true, new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());

        //when(oglasService.getZanrsForOglas(1L)).thenReturn(List.of(1L, 2L));
        when(korisnikService.findKorisnikByGoogleId("testGoogleId"))
                .thenReturn(korisnikDto);
        when(ulaznicaService.getUlaznicaById(1L)).thenReturn(new UlaznicaDto());
        //when(oglasService.createOglas(any(OglasDto.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(oglasService.getZanrsForOglas(anyLong())).thenReturn(Collections.emptyList());

        ResponseEntity<OglasDto> response = preferenceController.createOglas(token, oglasDto);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("AKTIVAN", response.getBody().getStatus());
        assertEquals(1L, response.getBody().getKorisnikId());
        assertEquals(1L, response.getBody().getUlaznicaId());
    }


    /*
    @Test
    void testCreateOglasWithInvalidUser() {
        OglasDto oglasDto = new OglasDto();
        oglasDto.setUlaznicaId(1L);

        when(korisnikService.findKorisnikByGoogleId("testGoogleId")).thenReturn(null);

        ResponseEntity<OglasDto> response = preferenceController.createOglas(token, oglasDto);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    */

    @Test
    void testCreateOglasWithEmptyData() {
        OglasDto oglasDto = new OglasDto();

        when(korisnikService.findKorisnikByGoogleId("testGoogleId"))
                .thenReturn(new KorisnikDto(1L, "Ime", "Prezime", "email", "0912345678", "foto", "testGoogleId", true, new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>()));

        ResponseEntity<OglasDto> response = preferenceController.createOglas(token, oglasDto);

        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());
    }

    /*
    @Test
    void testCreateOglasWithNoUlaznica() {
        OglasDto oglasDto = new OglasDto();
        oglasDto.setUlaznicaId(1L);

        when(korisnikService.findKorisnikByGoogleId("testGoogleId"))
                .thenReturn(new KorisnikDto(1L, "", "", "", "", "", "testGoogleId", true, new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>()));
        when(ulaznicaService.getUlaznicaById(1L)).thenReturn(new UlaznicaDto());
        when(oglasService.createOglas(any(OglasDto.class))).thenReturn(oglasDto);
        when(oglasService.getZanrsForOglas(anyLong())).thenReturn(Collections.emptyList());

        ResponseEntity<OglasDto> response = preferenceController.createOglas(token, oglasDto);

        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue()); // 400 - Bad Request
    }
    */
}

package hr.unizg.fer.ticket4ticket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.unizg.fer.ticket4ticket.dto.KorisnikDto;
import hr.unizg.fer.ticket4ticket.exception.ResourceNotFoundException;
import hr.unizg.fer.ticket4ticket.service.KorisnikService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(KorisnikController.class)
public class KorisnikControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KorisnikService korisnikService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetKorisnikById_existingKorisnik() throws Exception {
        // Arrange
        KorisnikDto mockKorisnik = new KorisnikDto();
        mockKorisnik.setIdKorisnika(1L);
        mockKorisnik.setImeKorisnika("Ivan");
        mockKorisnik.setPrezimeKorisnika("Horvat");
        mockKorisnik.setEmailKorisnika("ivan.horvat@example.com");

        Mockito.when(korisnikService.getKorisnikById(anyLong())).thenReturn(mockKorisnik);

        // Act & Assert
        mockMvc.perform(get("/api/korisnici/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idKorisnika").value(1L))
                .andExpect(jsonPath("$.imeKorisnika").value("Ivan"))
                .andExpect(jsonPath("$.prezimeKorisnika").value("Horvat"))
                .andExpect(jsonPath("$.emailKorisnika").value("ivan.horvat@example.com"));

        // Verify
        Mockito.verify(korisnikService, Mockito.times(1)).getKorisnikById(1L);
    }

    @Test
    public void testGetKorisnikById_nonExistingKorisnik() throws Exception {
        // Arrange
        Mockito.when(korisnikService.getKorisnikById(anyLong())).thenThrow(new ResourceNotFoundException("Korisnik not found"));

        // Act & Assert
        mockMvc.perform(get("/api/korisnici/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("Korisnik not found"));

        // Verify
        Mockito.verify(korisnikService, Mockito.times(1)).getKorisnikById(999L);
    }
}

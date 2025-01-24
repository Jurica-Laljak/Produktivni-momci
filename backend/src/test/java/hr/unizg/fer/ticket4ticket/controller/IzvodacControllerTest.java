package hr.unizg.fer.ticket4ticket.controller;

import hr.unizg.fer.ticket4ticket.dto.IzvodacDto;
import hr.unizg.fer.ticket4ticket.exception.ResourceNotFoundException;
import hr.unizg.fer.ticket4ticket.service.IzvodacService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class IzvodacControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IzvodacService izvodacService;

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void getIzvodacById_existingIzvodac_returnsIzvodac() throws Exception {
        // mock podaci za postojećeg izvođača
        Long existingId = 1L;
        IzvodacDto existingIzvodac = new IzvodacDto();
        existingIzvodac.setIdIzvodaca(existingId);
        existingIzvodac.setImeIzvodaca("Ime");
        existingIzvodac.setPrezimeIzvodaca("Prezime");
        existingIzvodac.setStarostIzvodaca(30);
        existingIzvodac.setFotoIzvodaca("https://example.com/photo.jpg");

        // kada se pozove service, vraća mock odgovor
        when(izvodacService.getIzvodacById(existingId)).thenReturn(existingIzvodac);

        // testiranje GET zahtjeva
        mockMvc.perform(get("/api/izvodaci/{id}", existingId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idIzvodaca", is(existingId.intValue())))
                .andExpect(jsonPath("$.imeIzvodaca", is("Ime")))
                .andExpect(jsonPath("$.prezimeIzvodaca", is("Prezime")))
                .andExpect(jsonPath("$.starostIzvodaca", is(30)))
                .andExpect(jsonPath("$.fotoIzvodaca", is("https://example.com/photo.jpg")))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void getIzvodacById_nonExistingIzvodac_returnsNotFound() throws Exception {
        // ID koji ne postoji
        Long nonExistingId = 999L;

        // kada se pozove service, baca se exception
        when(izvodacService.getIzvodacById(nonExistingId))
                .thenThrow(new ResourceNotFoundException("Korisnik sa tim id-om ne postoji: " + nonExistingId));

        // testiranje GET zahtjeva
        mockMvc.perform(get("/api/izvodaci/{id}", nonExistingId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()) // Očekujemo HTTP 404
                .andDo(print());
    }
}

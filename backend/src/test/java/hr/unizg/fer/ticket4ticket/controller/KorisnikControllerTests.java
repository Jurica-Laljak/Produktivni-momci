package hr.unizg.fer.ticket4ticket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import hr.unizg.fer.ticket4ticket.dto.KorisnikDto;
import hr.unizg.fer.ticket4ticket.service.KorisnikService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;

@SpringBootTest
@AutoConfigureMockMvc
public class KorisnikControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KorisnikService korisnikService;

    @Test
    public void korisnikController_createKorisnik_returnsKorisnik() throws Exception {
        KorisnikDto korisnikDto = KorisnikDto.builder()
                .googleId("googleId")
                .idKorisnika(1L)
                .emailKorisnika("emailKorisnika")
                .imeKorisnika("John")
                .prezimeKorisnika("Doe")
                .build();

        when(korisnikService.createKorisnik(korisnikDto)).thenReturn(korisnikDto);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(korisnikDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/korisnici")
                        .content(json).with(oauth2Login()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/korisnici")
                        .content(json).with(oauth2Login()))
                .andExpect(MockMvcResultMatchers.status().isUnsupportedMediaType())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void korisnikController_getKorisnikById_returnsKorisnik() throws Exception {
        KorisnikDto korisnikDto = KorisnikDto.builder()
                .googleId("googleId")
                .idKorisnika(1L)
                .emailKorisnika("emailKorisnika")
                .imeKorisnika("John")
                .prezimeKorisnika("Doe")
                .build();

        when(korisnikService.getKorisnikById(1L)).thenReturn(korisnikDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/korisnici/{id}", 1).with(oauth2Login()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.idKorisnika", Matchers.is(1)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void korisnikController_getKorisnikByGoogleId_returnsKorisnik() throws Exception {
        KorisnikDto korisnikDto = KorisnikDto.builder()
                .googleId("googleId")
                .idKorisnika(1L)
                .emailKorisnika("emailKorisnika")
                .imeKorisnika("John")
                .prezimeKorisnika("Doe")
                .build();

        when(korisnikService.findKorisnikByGoogleId("googleId")).thenReturn(korisnikDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/korisnici/g/{googleId}", "googleId").with(oauth2Login()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.idKorisnika", Matchers.is(1)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void korisnikController_getAllKorisnici_returnsListOfKorisnici() throws Exception {
        KorisnikDto korisnikDto1 = KorisnikDto.builder()
                .googleId("googleId")
                .idKorisnika(1L)
                .emailKorisnika("emailKorisnika")
                .imeKorisnika("John")
                .prezimeKorisnika("Doe")
                .build();
        KorisnikDto korisnikDto2 = KorisnikDto.builder()
                .googleId("googleId2")
                .idKorisnika(2L)
                .emailKorisnika("emailKorisnika2")
                .imeKorisnika("John2")
                .prezimeKorisnika("Doe2")
                .build();

        when(korisnikService.getAllKorisnici()).thenReturn(List.of(korisnikDto1, korisnikDto1));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/korisnici").with(oauth2Login()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andDo(MockMvcResultHandlers.print());
    }
}

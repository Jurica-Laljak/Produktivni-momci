package hr.unizg.fer.ticket4ticket.controller;

import hr.unizg.fer.ticket4ticket.dto.OglasDto;
import hr.unizg.fer.ticket4ticket.service.OglasService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OglasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OglasService oglasService;

    @Test
    public void createOglas_validData_returnsCreatedOglas() throws Exception {
        OglasDto validOglas = OglasDto.builder()
                .status("AKTIVAN")
                .korisnikId(10L)
                .ulaznicaId(100L)
                .build();

        // Mockiraj servisni sloj
        when(oglasService.createOglas(validOglas)).thenReturn(validOglas);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/oglasi").with(oauth2Login())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "status": "AKTIVAN",
                            "korisnikId": 10,
                            "ulaznicaId": 100
                        }
                    """))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", is("AKTIVAN")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.korisnikId", is(10)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ulaznicaId", is(100)))
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void createOglas_partialData_returnsCreatedOglas() throws Exception {
        OglasDto partialOglas = OglasDto.builder()
                .status("AKTIVAN")
                .build();

        when(oglasService.createOglas(partialOglas)).thenReturn(partialOglas);

        mockMvc.perform(post("/api/oglasi")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "status": "AKTIVAN"
                    }
                """))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("AKTIVAN"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void createOglas_emptyData_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/oglasi")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void createOglas_nonExistentKorisnik_returnsNotFound() throws Exception {
        OglasDto invalidOglas = OglasDto.builder()
                .status("AKTIVAN")
                .korisnikId(999L)
                .ulaznicaId(100L)
                .build();

        when(oglasService.createOglas(invalidOglas)).thenThrow(new RuntimeException("Korisnik not found"));

        mockMvc.perform(post("/api/oglasi")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "status": "AKTIVAN",
                        "korisnikId": 999,
                        "ulaznicaId": 100
                    }
                """))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void createOglas_nonExistentUlaznica_returnsNotFound() throws Exception {
        OglasDto invalidOglas = OglasDto.builder()
                .status("AKTIVAN")
                .korisnikId(100L)
                .ulaznicaId(999L)
                .build();

        when(oglasService.createOglas(invalidOglas)).thenThrow(new RuntimeException("Ulaznica not found"));

        mockMvc.perform(post("/api/oglasi")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "status": "AKTIVAN",
                        "korisnikId": 100,
                        "ulaznicaId": 999
                    }
                """))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}

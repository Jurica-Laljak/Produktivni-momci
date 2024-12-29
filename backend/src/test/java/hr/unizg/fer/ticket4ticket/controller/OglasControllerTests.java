package hr.unizg.fer.ticket4ticket.controller;

import hr.unizg.fer.ticket4ticket.dto.IzvodacDto;
import hr.unizg.fer.ticket4ticket.dto.OglasDto;
import hr.unizg.fer.ticket4ticket.service.OglasService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;

@SpringBootTest
@AutoConfigureMockMvc
public class OglasControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OglasService oglasService;

    @Test
    public void oglasController_getOglasById_returnsOglas() throws Exception {
        when(oglasService.getOglasById(1L)).thenReturn(OglasDto.builder().idOglasa(1L).build());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/oglasi/{id}", 1).with(oauth2Login()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.idOglasa", is(1)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void oglasController_getAllOglasi_returnsAllOglasi() throws Exception {
        when(oglasService.getAllOglasi()).thenReturn(List.of(new OglasDto(), new OglasDto(), new OglasDto(), new OglasDto()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/oglasi").with(oauth2Login()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(4)))
                .andDo(MockMvcResultHandlers.print());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/oglasi"))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void oglasController_getRandomOglasi_returnsRandomOglasi() throws Exception {
        when(oglasService.getRandomOglasi(2)).thenReturn(List.of(new OglasDto(), new OglasDto()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/oglasi/list/{broj_random_oglasa}", 2))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void oglasController_getIzvodaciForOglas_returnsIzvodaci() throws Exception {
        when(oglasService.getIzvodaciForOglas(2L)).thenReturn(List.of(new IzvodacDto(), new IzvodacDto()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/oglasi/{oglas_id}/izvodaci", 2))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andDo(MockMvcResultHandlers.print());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/oglasi/{oglas_id}/izvodaci", 3))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }
}

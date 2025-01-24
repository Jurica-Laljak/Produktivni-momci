package hr.unizg.fer.ticket4ticket.controller;

import hr.unizg.fer.ticket4ticket.dto.KorisnikDto;
import hr.unizg.fer.ticket4ticket.dto.OglasInfoDto;
import hr.unizg.fer.ticket4ticket.service.KorisnikService;
import hr.unizg.fer.ticket4ticket.service.OglasService;
import hr.unizg.fer.ticket4ticket.service.PreferenceService;
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
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@AutoConfigureMockMvc
@SpringBootTest
public class PreferenceControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PreferenceService preferenceService;

    @MockBean
    private KorisnikService korisnikService;

    @MockBean
    private OglasService oglasService;

    @Test
    public void preferenceController_getOglasiByGoogleId_returnsOglasi() throws Exception {
        when(korisnikService.findKorisnikByGoogleId("user")).thenReturn(KorisnikDto.builder().idKorisnika(1L).build());

        when(oglasService.getOglasiByKorisnikPreference(1L)).thenReturn(List.of(new OglasInfoDto(), new OglasInfoDto()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/preference/oglasi").with(user("user")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andDo(MockMvcResultHandlers.print());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/preference/oglasi"))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andDo(MockMvcResultHandlers.print());
    }
}

package hr.unizg.fer.ticket4ticket.controller;

import hr.unizg.fer.ticket4ticket.dto.ZanrDto;
import hr.unizg.fer.ticket4ticket.service.ZanrService;
import org.hamcrest.Matchers;
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

@AutoConfigureMockMvc
@SpringBootTest
public class ZanrControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ZanrService zanrService;

    @Test
    public void zanrController_getAllZanrovi_returnsAllZanrovi() throws Exception {
        when(zanrService.getAllZanrovi()).thenReturn(List.of(new ZanrDto(), new ZanrDto()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/zanrovi"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void zanrController_getZanrById_returnsZanr() throws Exception {
        ZanrDto zanr1 = ZanrDto.builder().idZanra(1L).imeZanra("Rock").build();

        when(zanrService.getZanrById(1L)).thenReturn(zanr1);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/zanrovi/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.idZanra", Matchers.is(1)))
                .andDo(MockMvcResultHandlers.print());
    }
}
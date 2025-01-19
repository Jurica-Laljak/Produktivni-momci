package hr.unizg.fer.ticket4ticket.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import hr.unizg.fer.ticket4ticket.dto.OglasInfoDto;
import hr.unizg.fer.ticket4ticket.entity.Izvodac;
import hr.unizg.fer.ticket4ticket.entity.Korisnik;
import hr.unizg.fer.ticket4ticket.entity.Oglas;
import hr.unizg.fer.ticket4ticket.entity.Ulaznica;
import hr.unizg.fer.ticket4ticket.repository.OglasRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

class OglasServiceImplTest {

    @InjectMocks
    private OglasServiceImpl oglasService;

    @Mock
    private OglasRepository oglasRepository;

    @Mock
    private Environment env;

    @Mock
    private Korisnik mockKorisnik;
    private Ulaznica mockUlaznica;
    private Izvodac mockIzvodac;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(env.getProperty("max_oglasi_returned", Integer.class)).thenReturn(5); // Postavi limit oglasa na 5



        mockIzvodac = Izvodac.builder()
                .idIzvodaca(1L)
                .imeIzvodaca("Ime")
                .prezimeIzvodaca("Prezime")
                .starostIzvodaca(30)
                .fotoIzvodaca("https://example/image.jpg")
                .korisniciKojiSlusaju(new HashSet<>(Collections.singletonList(mockKorisnik)))
                .build();

        mockUlaznica = Ulaznica.builder()
                .idUlaznice(1L)
                .datumKoncerta(LocalDate.now())
                .lokacijaKoncerta("Zagreb")
                .odabranaZona(Ulaznica.OdabranaZona.PARTER)
                .vrstaUlaznice(Ulaznica.VrstaUlaznice.STANDARD)
                .urlSlika("https://example/image.jpg")
                .urlInfo("https://example/info")
                .status(Ulaznica.Status.NEPREUZETA)
                .korisnik(mockKorisnik)
                .izvodaci(new HashSet<>(Collections.singletonList(mockIzvodac)))
                .build();
    }

    @Test
    void testKorisnikImaDovoljnoPreferiranihOglasa() {
        Long korisnikId = 1L;

        // Mock preferirane oglase
        List<Oglas> preferiraniOglasi = Arrays.asList(
                new Oglas(1L, Oglas.Status.AKTIVAN, mockUlaznica, mockKorisnik, new HashSet<>(), new ArrayList<>()),
                new Oglas(2L, Oglas.Status.AKTIVAN, mockUlaznica, mockKorisnik, new HashSet<>(), new ArrayList<>()),
                new Oglas(3L, Oglas.Status.AKTIVAN, mockUlaznica, mockKorisnik, new HashSet<>(), new ArrayList<>()),
                new Oglas(4L, Oglas.Status.AKTIVAN, mockUlaznica, mockKorisnik, new HashSet<>(), new ArrayList<>()),
                new Oglas(5L, Oglas.Status.AKTIVAN, mockUlaznica, mockKorisnik, new HashSet<>(), new ArrayList<>())
        );

        when(mockKorisnik.getOmiljeniIzvodaci()).thenReturn(new HashSet<>(Collections.singleton(mockIzvodac)));


        // Cast list to resolve generics issue
        when(oglasRepository.findOglasiByKorisnikPreference(korisnikId))
                .thenReturn(preferiraniOglasi);

        List<OglasInfoDto> result = oglasService.getOglasiByKorisnikPreference(korisnikId);

        assertEquals(5, result.size());
        verify(oglasRepository, never()).findRandomOglasiExcludingIdsAndKorisnikId(anyList(), anyInt(), eq(korisnikId));
    }

    @Test
    void testKorisnikNemaDovoljnoPreferiranihOglasa() {
        Long korisnikId = 1L;

        // Mock preferirane oglase
        List<Oglas> preferiraniOglasi = Arrays.asList(
                new Oglas(1L, Oglas.Status.AKTIVAN, mockUlaznica, mockKorisnik, new HashSet<>(), new ArrayList<>()),
                new Oglas(2L, Oglas.Status.AKTIVAN, mockUlaznica, mockKorisnik, new HashSet<>(), new ArrayList<>())
        );
        when(oglasRepository.findOglasiByKorisnikPreference(korisnikId)).thenReturn(preferiraniOglasi);

        // Mock dodatne random oglase
        List<Oglas> randomOglasi = Arrays.asList(
                new Oglas(3L, Oglas.Status.AKTIVAN, mockUlaznica, mockKorisnik, new HashSet<>(), new ArrayList<>()),
                new Oglas(4L, Oglas.Status.AKTIVAN, mockUlaznica, mockKorisnik, new HashSet<>(), new ArrayList<>()),
                new Oglas(5L, Oglas.Status.AKTIVAN, mockUlaznica, mockKorisnik, new HashSet<>(), new ArrayList<>()));
        when(oglasRepository.findRandomOglasiExcludingIdsAndKorisnikId(anyList(), eq(3), eq(korisnikId))).thenReturn(randomOglasi);

        List<OglasInfoDto> result = oglasService.getOglasiByKorisnikPreference(korisnikId);

        assertEquals(5, result.size());
        verify(oglasRepository).findRandomOglasiExcludingIdsAndKorisnikId(anyList(), eq(3), eq(korisnikId));
    }


    @Test
    void testKorisnikNemaPreferiranihOglasa() {
        Long korisnikId = 1L;

        // Mock bez preferiranih oglasa
        when(oglasRepository.findOglasiByKorisnikPreference(korisnikId)).thenReturn(Collections.emptyList());

        // Mock random oglase
        List<Oglas> randomOglasi = Arrays.asList(
                new Oglas(1L, Oglas.Status.AKTIVAN, mockUlaznica, mockKorisnik, new HashSet<>(), new ArrayList<>()),
                new Oglas(2L, Oglas.Status.AKTIVAN, mockUlaznica, mockKorisnik, new HashSet<>(), new ArrayList<>()),
                new Oglas(3L, Oglas.Status.AKTIVAN, mockUlaznica, mockKorisnik, new HashSet<>(), new ArrayList<>()),
                new Oglas(4L, Oglas.Status.AKTIVAN, mockUlaznica, mockKorisnik, new HashSet<>(), new ArrayList<>()),
                new Oglas(5L, Oglas.Status.AKTIVAN, mockUlaznica, mockKorisnik, new HashSet<>(), new ArrayList<>())
        );
        when(oglasRepository.findRandomOglasiExcludingIdsAndKorisnikId(anyList(), eq(5), eq(korisnikId))).thenReturn(randomOglasi);

        List<OglasInfoDto> result = oglasService.getOglasiByKorisnikPreference(korisnikId);

        assertEquals(5, result.size());
        verify(oglasRepository).findRandomOglasiExcludingIdsAndKorisnikId(anyList(), eq(5), eq(korisnikId));
    }


    @Test
    void testNemaNijednogOglasa() {
        Long korisnikId = 1L;

        // Mock bez preferiranih i random oglasa
        when(oglasRepository.findOglasiByKorisnikPreference(korisnikId)).thenReturn(Collections.emptyList());
        when(oglasRepository.findRandomOglasiExcludingIdsAndKorisnikId(anyList(), eq(5), eq(korisnikId))).thenReturn(Collections.emptyList());

        List<OglasInfoDto> result = oglasService.getOglasiByKorisnikPreference(korisnikId);

        assertEquals(0, result.size());
    }


}


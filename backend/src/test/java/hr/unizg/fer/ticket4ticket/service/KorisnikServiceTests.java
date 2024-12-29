package hr.unizg.fer.ticket4ticket.service;

import hr.unizg.fer.ticket4ticket.dto.KorisnikDto;
import hr.unizg.fer.ticket4ticket.entity.Korisnik;
import hr.unizg.fer.ticket4ticket.repository.KorisnikRepository;
import hr.unizg.fer.ticket4ticket.service.impl.KorisnikServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class KorisnikServiceTests {

    @Mock
    private KorisnikRepository korisnikRepository;

    @InjectMocks
    private KorisnikServiceImpl korisnikService;

    @Test
    public void korisnikService_createKorisnik_returnsKorisnikDto() {
        Korisnik korisnik = Korisnik.builder()
                .imeKorisnika("John")
                .prezimeKorisnika("Doe")
                .emailKorisnika("john.doe@gmail.com")
                .fotoKorisnika("img.jpg")
                .googleId("123456")
                .omiljeniIzvodaci(new HashSet<>())
                .omiljeniZanrovi(new HashSet<>())
                .oglasi(new HashSet<>())
                .roles(new HashSet<>())
                .build();

        KorisnikDto korisnikDto = KorisnikDto.builder()
                .imeKorisnika("John")
                .prezimeKorisnika("Doe")
                .emailKorisnika("john.doe@gmail.com")
                .fotoKorisnika("img.jpg")
                .googleId("123456")
                .omiljeniIzvodaciIds(new HashSet<>())
                .omiljeniZanroviIds(new HashSet<>())
                .oglasiIds(new HashSet<>())
                .roleIds(new HashSet<>())
                .build();

        when(korisnikRepository.save(Mockito.any(Korisnik.class))).thenReturn(korisnik);

        KorisnikDto savedKorisnik = korisnikService.createKorisnik(korisnikDto);

        Assertions.assertNotNull(savedKorisnik);
    }

    @Test
    public void korisnikService_findKorisnikByGoogleId_returnsKorisnikDto() {
        Korisnik korisnik = Korisnik.builder()
                .imeKorisnika("John")
                .prezimeKorisnika("Doe")
                .emailKorisnika("john.doe@gmail.com")
                .fotoKorisnika("img.jpg")
                .googleId("123456")
                .omiljeniIzvodaci(new HashSet<>())
                .omiljeniZanrovi(new HashSet<>())
                .oglasi(new HashSet<>())
                .roles(new HashSet<>())
                .build();

        when(korisnikRepository.findByGoogleId("123456")).thenReturn(korisnik);

        KorisnikDto savedKorisnik = korisnikService.findKorisnikByGoogleId("123456");

        Assertions.assertNotNull(savedKorisnik);
        Assertions.assertEquals(korisnik.getGoogleId(), savedKorisnik.getGoogleId());
    }
}

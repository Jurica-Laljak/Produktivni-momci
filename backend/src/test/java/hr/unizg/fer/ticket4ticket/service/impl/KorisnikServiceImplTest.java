package hr.unizg.fer.ticket4ticket.service.impl;

import hr.unizg.fer.ticket4ticket.dto.KorisnikDto;
import hr.unizg.fer.ticket4ticket.dto.KorisnikUpdateDto;
import hr.unizg.fer.ticket4ticket.entity.Korisnik;
import hr.unizg.fer.ticket4ticket.exception.ResourceNotFoundException;
import hr.unizg.fer.ticket4ticket.mapper.KorisnikMapper;
import hr.unizg.fer.ticket4ticket.repository.KorisnikRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class KorisnikServiceImplTest {

    @Mock
    private KorisnikRepository korisnikRepository;

    @InjectMocks
    private KorisnikServiceImpl korisnikService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateKorisnikFields_NonExistentUser() {

        Long nonExistentId = 1L;
        KorisnikUpdateDto updateDto = new KorisnikUpdateDto();
        when(korisnikRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            korisnikService.updateKorisnikFields(nonExistentId, updateDto);
        });

    }

    @Test
    void testUpdateKorisnikFields_NoFieldsUpdated() {

        Long korisnikId = 1L;
        Korisnik existingKorisnik = new Korisnik();
        existingKorisnik.setIdKorisnika(korisnikId);
        existingKorisnik.setImeKorisnika("Pero");
        existingKorisnik.setPrezimeKorisnika("Perić");
        existingKorisnik.setBrMobKorisnika("0912345678");
        existingKorisnik.setPrikazujObavijesti(true);

        KorisnikUpdateDto updateDto = new KorisnikUpdateDto(); // No fields set

        when(korisnikRepository.findById(korisnikId)).thenReturn(Optional.of(existingKorisnik));
        when(korisnikRepository.save(existingKorisnik)).thenReturn(existingKorisnik);

        KorisnikDto result = korisnikService.updateKorisnikFields(korisnikId, updateDto);

        assertNotNull(result);
        assertEquals("Pero", result.getImeKorisnika());
        assertEquals("Perić", result.getPrezimeKorisnika());
        assertEquals("0912345678", result.getBrMobKorisnika());
        assertTrue(result.isPrikazujObavijesti());

    }

    @Test
    void testUpdateKorisnikFields_UpdateSomeFields() {

        Long korisnikId = 1L;
        Korisnik existingKorisnik = new Korisnik();
        existingKorisnik.setIdKorisnika(korisnikId);
        existingKorisnik.setImeKorisnika("Pero");
        existingKorisnik.setPrezimeKorisnika("Perić");
        existingKorisnik.setBrMobKorisnika("0912345678");
        existingKorisnik.setPrikazujObavijesti(true);

        KorisnikUpdateDto updateDto = new KorisnikUpdateDto();
        updateDto.setImeKorisnika("Ivan");
        updateDto.setPrikazujObavijesti(false);

        when(korisnikRepository.findById(korisnikId)).thenReturn(Optional.of(existingKorisnik));
        when(korisnikRepository.save(existingKorisnik)).thenReturn(existingKorisnik);

        KorisnikDto result = korisnikService.updateKorisnikFields(korisnikId, updateDto);

        assertNotNull(result);
        assertEquals("Ivan", result.getImeKorisnika());
        assertEquals("Perić", result.getPrezimeKorisnika());
        assertEquals("0912345678", result.getBrMobKorisnika());
        assertFalse(result.isPrikazujObavijesti());
    }

    @Test
    void testUpdateKorisnikFields_ClearFields() {

        Long korisnikId = 1L;
        Korisnik existingKorisnik = new Korisnik();
        existingKorisnik.setIdKorisnika(korisnikId);
        existingKorisnik.setImeKorisnika("Pero");
        existingKorisnik.setPrezimeKorisnika("Perić");
        existingKorisnik.setBrMobKorisnika("0912345678");
        existingKorisnik.setPrikazujObavijesti(true);

        KorisnikUpdateDto updateDto = new KorisnikUpdateDto();
        updateDto.setImeKorisnika("");

        when(korisnikRepository.findById(korisnikId)).thenReturn(Optional.of(existingKorisnik));

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            korisnikService.updateKorisnikFields(korisnikId, updateDto);
        });

        assertEquals("Ime korisnika ne smije biti prazno.", exception.getMessage());
    }
}

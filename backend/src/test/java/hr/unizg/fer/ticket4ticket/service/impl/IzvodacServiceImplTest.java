package hr.unizg.fer.ticket4ticket.service.impl;

import hr.unizg.fer.ticket4ticket.dto.IzvodacDto;
import hr.unizg.fer.ticket4ticket.entity.Izvodac;
import hr.unizg.fer.ticket4ticket.entity.Zanr;
import hr.unizg.fer.ticket4ticket.mapper.IzvodacMapper;
import hr.unizg.fer.ticket4ticket.repository.IzvodacRepository;
import hr.unizg.fer.ticket4ticket.service.impl.IzvodacServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IzvodacServiceImplTest {

    @InjectMocks
    private IzvodacServiceImpl izvodacService;

    @Mock
    private IzvodacRepository izvodacRepository;

    @Test
    public void testCreateIzvodacValidData() {
        // Arrange
        IzvodacDto izvodacDto = new IzvodacDto();
        izvodacDto.setImeIzvodaca("Ime");
        izvodacDto.setPrezimeIzvodaca("Prezime");
        izvodacDto.setStarostIzvodaca(30);
        izvodacDto.setFotoIzvodaca("https://example/image.jpg");

        Izvodac izvodac = Izvodac.builder()
                .imeIzvodaca("Ime")
                .prezimeIzvodaca("Prezime")
                .starostIzvodaca(30)
                .fotoIzvodaca("https://example/image.jpg")
                .build();

        Izvodac savedIzvodac = Izvodac.builder()
                .imeIzvodaca("Ime")
                .prezimeIzvodaca("Prezime")
                .starostIzvodaca(30)
                .fotoIzvodaca("https://example/image.jpg")
                .build();

        // Mock static method for IzvodacMapper
        try (MockedStatic<IzvodacMapper> mockedStatic = Mockito.mockStatic(IzvodacMapper.class)) {
            mockedStatic.when(() -> IzvodacMapper.mapToIzvodac(izvodacDto)).thenReturn(izvodac);
            mockedStatic.when(() -> IzvodacMapper.mapToIzvodacDto(savedIzvodac)).thenReturn(izvodacDto);

            // Simulate the behavior of the repository
            when(izvodacRepository.save(izvodac)).thenReturn(savedIzvodac);

            // Act
            IzvodacDto result = izvodacService.createIzvodac(izvodacDto);

            // Assert
            assertNotNull(result);
            assertEquals("Ime", result.getImeIzvodaca());
            assertEquals("Prezime", result.getPrezimeIzvodaca());
            assertEquals(Integer.valueOf(30), result.getStarostIzvodaca());
            assertEquals("https://example/image.jpg", result.getFotoIzvodaca());
        }
    }

    // Test when some data is missing (e.g. missing name)
    @Test
    public void testCreateIzvodacMissingName() {
        // Arrange
        IzvodacDto izvodacDto = new IzvodacDto();
        izvodacDto.setImeIzvodaca("");  // Missing name
        izvodacDto.setPrezimeIzvodaca("Prezime");
        izvodacDto.setStarostIzvodaca(30);
        izvodacDto.setFotoIzvodaca("https://example/image.jpg");

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            izvodacService.createIzvodac(izvodacDto);  // This should throw an exception due to empty name
        });

        // Assert that exception message matches expected error
        assertEquals("Ime izvođača ne smije biti prazno", exception.getMessage());
    }

    // Test when no data is provided (null or empty)
    @Test
    public void testCreateIzvodacNoData() {
        // Arrange
        IzvodacDto izvodacDto = new IzvodacDto(); // All fields are null or empty by default

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            izvodacService.createIzvodac(izvodacDto); // Should throw an exception due to missing data
        });

        // Assert that the exception message matches the expected one
        assertEquals("Svi podaci izvođača moraju biti uneseni", exception.getMessage());
    }

}

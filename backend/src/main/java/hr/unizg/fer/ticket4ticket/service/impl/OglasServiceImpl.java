package hr.unizg.fer.ticket4ticket.service.impl;

import hr.unizg.fer.ticket4ticket.dto.OglasDto;
import hr.unizg.fer.ticket4ticket.entity.Oglas;
import hr.unizg.fer.ticket4ticket.mapper.OglasMapper;
import hr.unizg.fer.ticket4ticket.repository.OglasRepository;
import hr.unizg.fer.ticket4ticket.service.OglasService;
import hr.unizg.fer.ticket4ticket.dto.IzvodacDto;
import hr.unizg.fer.ticket4ticket.dto.OglasFilterDto;
import hr.unizg.fer.ticket4ticket.entity.Izvodac;
import hr.unizg.fer.ticket4ticket.mapper.IzvodacMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hr.unizg.fer.ticket4ticket.exception.ResourceNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OglasServiceImpl implements OglasService {

    @Autowired
    private OglasRepository oglasRepository;


    @Override
    public OglasDto createOglas(OglasDto oglasDto) {
        // Convert DTO to entity
        Oglas oglas = OglasMapper.mapToOglas(oglasDto);

        // Save entity to the repository
        Oglas savedOglas = oglasRepository.save(oglas);

        // Convert the saved entity back to DTO
        return OglasMapper.mapToOglasDto(savedOglas);
    }

    @Override
    public OglasDto getOglasById(Long oglasId) {
        // Retrieve the ad by ID, or throw an exception if not found
        Oglas oglas = oglasRepository.findById(oglasId)
                .orElseThrow(() -> new ResourceNotFoundException("Oglas with ID " + oglasId + " does not exist."));

        // Convert entity to DTO
        return OglasMapper.mapToOglasDto(oglas);
    }

    @Override
    public List<OglasDto> getAllOglasi() {
        // Retrieve all ads
        List<Oglas> oglasi = oglasRepository.findAll();

        // Convert list of entities to list of DTOs
        return oglasi.stream()
                .map(OglasMapper::mapToOglasDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<OglasDto> getRandomOglasi(int brojRandomOglasa) {
        // Retrieve all ads
        List<Oglas> allOglasi = oglasRepository.findAll();

        // Shuffle the list to randomize the order
        Collections.shuffle(allOglasi);

        // Limit the number of random ads to the requested number
        return allOglasi.stream()
                .limit(brojRandomOglasa)
                .map(OglasMapper::mapToOglasDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OglasDto> getOglasiByFilter(OglasFilterDto filterDto) {
        List<Oglas> oglasi = oglasRepository.findOglasiByFilter(
                filterDto.getImeIzvodaca(),
                filterDto.getPrezimeIzvodaca()
        );

        // Handle potential null return from the repository
        if (oglasi == null) {
            return Collections.emptyList();
        }

        // Convert Oglas entities to OglasDto
        return oglasi.stream()
                .map(OglasMapper::mapToOglasDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OglasDto> getOglasiByKorisnikPreference(Long idKorisnika) {
        List<Oglas> oglasi = oglasRepository.findOglasiByKorisnikPreference(idKorisnika);

        // Map Oglas entities to OglasDto
        return oglasi.stream()
                .map(OglasMapper::mapToOglasDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<IzvodacDto> getIzvodaciForOglas(Long oglasId) {
        // Retrieve the Oglas by ID
        Oglas oglas = oglasRepository.findById(oglasId)
                .orElseThrow(() -> new ResourceNotFoundException("Oglas with ID " + oglasId + " not found"));

        // Get the ulaznica ID associated with the Oglas
        Long ulaznicaId = oglas.getUlaznica().getIdUlaznice();

        // Retrieve the Izvodaci associated with the ulaznica ID
        List<Izvodac> izvodaci = oglasRepository.findIzvodaciByUlaznicaId(ulaznicaId);

        // Map the Izvodac entities to Izvodac DTOs (you need to implement this mapping method)
        return izvodaci.stream()
                .map(IzvodacMapper::mapToIzvodacDto)  // Assuming you have a mapper to convert to DTO
                .collect(Collectors.toList());
    }

    // Method to retrieve all Oglases for a given korisnikId with status AKTIVAN
    public List<OglasDto> findActiveOglasesByKorisnikId(Long korisnikId) {
        return oglasRepository.findByKorisnik_IdKorisnikaAndStatus(korisnikId, Oglas.Status.AKTIVAN)
                .stream()
                .map(OglasMapper::mapToOglasDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAllOglasiByKorisnikId(Long idKorisnika) {
        // First, find all the Oglasi associated with the given korisnikId
        List<Oglas> oglasiToDelete = oglasRepository.findByKorisnik_IdKorisnika(idKorisnika);



        // Delete all Oglasi for the given korisnikId
        oglasRepository.deleteAll(oglasiToDelete);
    }




}

package hr.unizg.fer.ticket4ticket.service.impl;

import hr.unizg.fer.ticket4ticket.dto.*;
import hr.unizg.fer.ticket4ticket.entity.*;
import hr.unizg.fer.ticket4ticket.exception.ResourceNotFoundException;
import hr.unizg.fer.ticket4ticket.mapper.ObavijestMapper;
import hr.unizg.fer.ticket4ticket.mapper.OglasMapper;
import hr.unizg.fer.ticket4ticket.mapper.UlaznicaMapper;
import hr.unizg.fer.ticket4ticket.mapper.IzvodacMapper;
import hr.unizg.fer.ticket4ticket.repository.KorisnikRepository;
import hr.unizg.fer.ticket4ticket.repository.ObavijestRepository;
import hr.unizg.fer.ticket4ticket.repository.OglasRepository;
import hr.unizg.fer.ticket4ticket.service.ObavijestService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ObavijestServiceImpl implements ObavijestService {

    @Autowired
    private ObavijestRepository obavijestRepository;

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private OglasRepository oglasRepository;

    @Override
    public ObavijestDto createObavijest(ObavijestDto obavijestDto) {
        Obavijest obavijest = ObavijestMapper.mapToObavijest(obavijestDto);

        obavijestRepository.save(obavijest);

        return ObavijestMapper.mapToObavijestDto(obavijest);
    }

    @Override
    public List<ObavijestInfoDto> getObavijestiByKorisnikId(Long korisnikId) {
        List<Obavijest> obavijesti = obavijestRepository.findByKorisnikId(korisnikId);

        return obavijesti.stream()
                .map(obavijest -> {
                    try {
                        ObavijestInfoDto dto = new ObavijestInfoDto();

                        // Set basic fields from Obavijest
                        dto.setIdObavijesti(obavijest.getIdObavijesti());
                        dto.setObavijestType(obavijest.getObavijestTip());
                        dto.setKorisnikIdObavijest(obavijest.getKorisnikId());

                        // Handle Transakcija mapping
                        Transakcija transakcija = obavijest.getTransakcija();
                        if (transakcija != null) {
                            dto.setTransakcijaIdObavijest(transakcija.getIdTransakcije());
                            if (transakcija.getKorisnikOglas() != null) {
                                dto.setKorisnikOglasIme(transakcija.getKorisnikOglas().getImeKorisnika());
                                dto.setKorisnikOglasPrezime(transakcija.getKorisnikOglas().getPrezimeKorisnika());
                            }
                            if (transakcija.getKorisnikPonuda() != null) {
                                dto.setKorisnikPonudaIme(transakcija.getKorisnikPonuda().getImeKorisnika());
                                dto.setKorisnikPonudaPrezime(transakcija.getKorisnikPonuda().getPrezimeKorisnika());
                            }
                            dto.setUlaznicaOglas(UlaznicaMapper.mapToUlaznicaDto(transakcija.getUlaznicaOglas()));
                            dto.setUlaznicaPonuda(UlaznicaMapper.mapToUlaznicaDto(transakcija.getUlaznicaPonuda()));

                            List<IzvodacDto> izvodacDtoList = new ArrayList<>();

                            if (transakcija.getUlaznicaPonuda() != null && transakcija.getUlaznicaPonuda().getIzvodaci() != null) {
                                // Map each Izvodac to IzvodacDto and add to the list
                                for (Izvodac izvodac : transakcija.getUlaznicaPonuda().getIzvodaci()) {
                                    IzvodacDto izvodacDto = IzvodacMapper.mapToIzvodacDto(izvodac); // Map to DTO
                                    izvodacDtoList.add(izvodacDto); // Add to the list
                                }
                            }

                            if (transakcija.getUlaznicaOglas() != null && transakcija.getUlaznicaOglas().getIzvodaci() != null) {
                                // Map each Izvodac to IzvodacDto and add to the list
                                for (Izvodac izvodac : transakcija.getUlaznicaOglas().getIzvodaci()) {
                                    IzvodacDto izvodacDto = IzvodacMapper.mapToIzvodacDto(izvodac); // Map to DTO
                                    izvodacDtoList.add(izvodacDto); // Add to the list
                                }
                            }

                            dto.setIzvodaci(izvodacDtoList);
                        }

                        // Handle Oglas mapping
                        Oglas oglas = obavijest.getOglas();
                        if (oglas != null) {
                            dto.setOglasIdObavijest(oglas.getIdOglasa());
                            if (oglas.getKorisnik() != null) {
                                dto.setAutorOglasIme(oglas.getKorisnik().getImeKorisnika());
                                dto.setAutorOglasPrezime(oglas.getKorisnik().getPrezimeKorisnika());
                            }
                            dto.setUlaznicaPreporuka(UlaznicaMapper.mapToUlaznicaDto(oglas.getUlaznica()));
                            dto.setZanrIdObavijest(
                                    oglas.getUlaznica() != null
                                            && oglas.getUlaznica().getIzvodaci() != null
                                            && !oglas.getUlaznica().getIzvodaci().isEmpty()
                                            ? oglas.getUlaznica().getIzvodaci().iterator().next().getZanrId()
                                            : null
                            );

                            dto.setIzvodaci(
                                    oglas.getUlaznica() != null
                                            && oglas.getUlaznica().getIzvodaci() != null
                                            ? oglas.getUlaznica().getIzvodaci().stream()
                                            .map(IzvodacMapper::mapToIzvodacDto) // Map each Izvodac to its DTO
                                            .collect(Collectors.toList()) // Collect to a List
                                            : Collections.emptyList() // Return an empty list if null
                            );
                        }

                        return dto;

                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<ObavijestDto> getObavijestiByZanrId(Long zanrId) {
        List<Obavijest> obavijesti = obavijestRepository.findByZanrId(zanrId);

        return obavijesti.stream()
                .map(ObavijestMapper::mapToObavijestDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ObavijestDto> getAllObavijesti() {
        List<Obavijest> obavijesti = obavijestRepository.findAll();

        return obavijesti.stream()
                .map(ObavijestMapper::mapToObavijestDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ObavijestDto> getObavijestiByOglasId(Long oglasId) {
        List<Obavijest> obavijesti = obavijestRepository.findByOglasId(oglasId);

        return obavijesti.stream()
                .map(ObavijestMapper::mapToObavijestDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ObavijestDto> getObavijestiByTransakcijaId(Long transakcijaId) {
        List<Obavijest> obavijesti = obavijestRepository.findByTransakcijaId(transakcijaId);

        return obavijesti.stream()
                .map(ObavijestMapper::mapToObavijestDto)
                .collect(Collectors.toList());
    }

    public void getAndDeleteObavijestiByOglasId(Long oglasId) {
        // Retrieve the list of Obavijest objects by the Oglas ID
        List<Obavijest> obavijesti = obavijestRepository.findByOglasId(oglasId);

        // Check if the list is not empty
        if (obavijesti != null && !obavijesti.isEmpty()) {
            // Delete each Obavijest from the list
            for (Obavijest obavijest : obavijesti) {
                obavijestRepository.delete(obavijest);  // Delete the individual Obavijest
            }
        }
    }

    public void getAndDeleteObavijestiByTransakcijaId(Long transakcijaId) {
        // Find all Obavijesti associated with the given transakcijaId
        List<Obavijest> obavijesti = obavijestRepository.findByTransakcijaId(transakcijaId);

        if (!obavijesti.isEmpty()) {
            // Delete all the found Obavijesti
            obavijestRepository.deleteAll(obavijesti);

            // Optionally, print a message to indicate the number of Obavijesti deleted
            System.out.println("Deleted " + obavijesti.size() + " Obavijesti related to Transakcija ID: " + transakcijaId);
        } else {
            System.out.println("No Obavijesti found for Transakcija ID: " + transakcijaId);
        }
    }


    @Override
    public ObavijestDto getObavijestiById(Long obavijestId) {
        Obavijest obavijest = obavijestRepository.findById(obavijestId)
                .orElseThrow(() -> new ResourceNotFoundException("Obavijest with ID " + obavijestId + " does not exist."));

        return ObavijestMapper.mapToObavijestDto(obavijest);
    }

    @Override
    public Boolean removeObavijest(Long obavijestId) {
        if (!obavijestRepository.existsById(obavijestId))
            return false;

        obavijestRepository.deleteById(obavijestId);
        return true;
    }

    @Override
    public void deleteObavijestiByKorisnikId(Long korisnikId) {
        // Fetch all notifications for the given korisnikId
        List<Obavijest> obavijesti = obavijestRepository.findByKorisnikId(korisnikId);

        // Delete all fetched notifications
        if (!obavijesti.isEmpty()) {
            obavijestRepository.deleteAll(obavijesti);
        }
    }




}

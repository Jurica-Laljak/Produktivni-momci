package hr.unizg.fer.ticket4ticket.service.impl;

import hr.unizg.fer.ticket4ticket.dto.ObavijestDto;
import hr.unizg.fer.ticket4ticket.entity.Obavijest;
import hr.unizg.fer.ticket4ticket.entity.Oglas;
import hr.unizg.fer.ticket4ticket.exception.ResourceNotFoundException;
import hr.unizg.fer.ticket4ticket.mapper.ObavijestMapper;
import hr.unizg.fer.ticket4ticket.repository.ObavijestRepository;
import hr.unizg.fer.ticket4ticket.repository.OglasRepository;
import hr.unizg.fer.ticket4ticket.service.ObavijestService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ObavijestServiceImpl implements ObavijestService {

    @Autowired
    private ObavijestRepository obavijestRepository;

    @Autowired
    private OglasRepository oglasRepository;

    @Override
    public ObavijestDto createObavijest(ObavijestDto obavijestDto) {
        Obavijest obavijest = ObavijestMapper.mapToObavijest(obavijestDto);

        obavijestRepository.save(obavijest);

        return ObavijestMapper.mapToObavijestDto(obavijest);
    }

    @Override
    public List<ObavijestDto> getObavijestiByKorisnikId(Long korisnikId) {
        List<Oglas> oglasi = oglasRepository.findByKorisnikId(korisnikId);

        List<Obavijest> obavijesti = new ArrayList<>();

        for (Oglas oglas : oglasi) {
            obavijesti.addAll(obavijestRepository.findByOglasId(oglas.getIdOglasa()));
        }

        return obavijesti.stream()
                .map(ObavijestMapper::mapToObavijestDto)
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
    @Scheduled(fixedRate = 300000)
    public Long clearObavijestiOnTtl() {
        List<Obavijest> obavijesti = obavijestRepository.findAll();
        Long deleted = 0L;


        for (Obavijest obavijest : obavijesti) {
            Duration ttl = obavijest.getTimeToLive();
            ttl = ttl.minus(Duration.ofMinutes(5));
            if (ttl.isNegative() || ttl.isZero()) {
                obavijestRepository.delete(obavijest);
                deleted++;
            }
        }

        return deleted;
    }
}

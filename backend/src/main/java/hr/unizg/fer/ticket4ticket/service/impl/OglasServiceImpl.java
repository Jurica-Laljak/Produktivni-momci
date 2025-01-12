package hr.unizg.fer.ticket4ticket.service.impl;

import hr.unizg.fer.ticket4ticket.dto.*;
import hr.unizg.fer.ticket4ticket.entity.Izvodac;
import hr.unizg.fer.ticket4ticket.entity.Oglas;
import hr.unizg.fer.ticket4ticket.entity.Ulaznica;
import hr.unizg.fer.ticket4ticket.entity.Zanr;
import hr.unizg.fer.ticket4ticket.exception.ResourceNotFoundException;
import hr.unizg.fer.ticket4ticket.mapper.*;
import hr.unizg.fer.ticket4ticket.repository.OglasRepository;
import hr.unizg.fer.ticket4ticket.service.OglasService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OglasServiceImpl implements OglasService {

    @Autowired
    private OglasRepository oglasRepository;


    @Autowired
    Environment env;

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
    public OglasInfoDto getOglasById(Long oglasId) {
        // Retrieve the ad by ID, or throw an exception if not found
        Oglas oglas = oglasRepository.findById(oglasId)
                .orElseThrow(() -> new ResourceNotFoundException("Oglas with ID " + oglasId + " does not exist."));

        // Retrieve and map the Ulaznica to UlaznicaDto
        Ulaznica ulaznica = oglas.getUlaznica();
        UlaznicaDto ulaznicaDto = null;
        if (ulaznica != null) {
            ulaznicaDto = UlaznicaMapper.mapToUlaznicaDto(ulaznica);
        }

        // Retrieve the Izvodaci and map them to IzvodacDto
        Set<IzvodacDto> izvodacDtos = ulaznica != null ?
                ulaznica.getIzvodaci().stream()
                        .map(IzvodacMapper::mapToIzvodacDto)
                        .collect(Collectors.toSet()) : new HashSet<>();

        // Retrieve the Korisnik and map it to KorisnikDto, if the Korisnik is not null
        KorisnikDto korisnikDto = null;
        if (oglas.getKorisnik() != null) {
            korisnikDto = KorisnikMapper.mapToKorisnikDto(oglas.getKorisnik());
        }

        // Map to OglasInfoDto
        OglasInfoDto oglasInfoDto = OglasInfoDto.builder()
                .idOglasa(oglas.getIdOglasa())
                .status(oglas.getStatus().name())
                .korisnikId(oglas.getKorisnik() != null ? oglas.getKorisnik().getIdKorisnika() : null)
                .ulaznicaId(ulaznica != null ? ulaznica.getIdUlaznice() : null)
                .datumKoncerta(ulaznica != null ? ulaznica.getDatumKoncerta() : null)
                .lokacijaKoncerta(ulaznica != null ? ulaznica.getLokacijaKoncerta() : null)
                .odabranaZona(ulaznica != null ? ulaznica.getOdabranaZona().name() : null)
                .vrstaUlaznice(ulaznica != null ? ulaznica.getVrstaUlaznice().name() : null)
                .urlSlika(ulaznica != null ? ulaznica.getUrlSlika() : null)
                .urlInfo(ulaznica != null ? ulaznica.getUrlInfo() : null)
                .statusUlaznice(ulaznica != null ? ulaznica.getStatus().name() : null)
                .sifraUlaznice(ulaznica != null ? ulaznica.getSifraUlaznice() : null)
                .idKorisnika(korisnikDto != null ? korisnikDto.getIdKorisnika() : null)
                .imeKorisnika(korisnikDto != null ? korisnikDto.getImeKorisnika() : null)
                .prezimeKorisnika(korisnikDto != null ? korisnikDto.getPrezimeKorisnika() : null)
                .emailKorisnika(korisnikDto != null ? korisnikDto.getEmailKorisnika() : null)
                .brMobKorisnika(korisnikDto != null ? korisnikDto.getBrMobKorisnika() : null)
                .izvodaci(izvodacDtos)
                .build();

        // Return the populated OglasInfoDto
        return oglasInfoDto;
    }

    @Override
    public List<OglasInfoDto> getAllOglasi() {
        // Retrieve all ads
        List<Oglas> oglasi = oglasRepository.findAll();

        // Convert list of entities to list of DTOs
        return oglasi.stream()
                .map(oglas -> {
                    Ulaznica ulaznica = oglas.getUlaznica();
                    Set<IzvodacDto> izvodacDtos = ulaznica != null ?
                            ulaznica.getIzvodaci().stream()
                                    .map(IzvodacMapper::mapToIzvodacDto)
                                    .collect(Collectors.toSet()) : new HashSet<>();

                    KorisnikDto korisnikDto = null;
                    if (oglas.getKorisnik() != null) {
                        korisnikDto = KorisnikMapper.mapToKorisnikDto(oglas.getKorisnik());
                    }

                    OglasInfoDto oglasInfoDto = OglasInfoDto.builder()
                            .idOglasa(oglas.getIdOglasa())
                            .status(oglas.getStatus().name())
                            .korisnikId(korisnikDto != null ? korisnikDto.getIdKorisnika() : null)
                            .ulaznicaId(ulaznica != null ? ulaznica.getIdUlaznice() : null)
                            .datumKoncerta(ulaznica != null ? ulaznica.getDatumKoncerta() : null)
                            .lokacijaKoncerta(ulaznica != null ? ulaznica.getLokacijaKoncerta() : null)
                            .odabranaZona(ulaznica != null ? ulaznica.getOdabranaZona().name() : null)
                            .vrstaUlaznice(ulaznica != null ? ulaznica.getVrstaUlaznice().name() : null)
                            .urlSlika(ulaznica != null ? ulaznica.getUrlSlika() : null)
                            .urlInfo(ulaznica != null ? ulaznica.getUrlInfo() : null)
                            .statusUlaznice(ulaznica != null ? ulaznica.getStatus().name() : null)
                            .sifraUlaznice(ulaznica != null ? ulaznica.getSifraUlaznice() : null)
                            .idKorisnika(korisnikDto != null ? korisnikDto.getIdKorisnika() : null)
                            .imeKorisnika(korisnikDto != null ? korisnikDto.getImeKorisnika() : null)
                            .prezimeKorisnika(korisnikDto != null ? korisnikDto.getPrezimeKorisnika() : null)
                            .emailKorisnika(korisnikDto != null ? korisnikDto.getEmailKorisnika() : null)
                            .brMobKorisnika(korisnikDto != null ? korisnikDto.getBrMobKorisnika() : null)
                            .izvodaci(izvodacDtos)
                            .build();

                    return oglasInfoDto;
                })
                .collect(Collectors.toList());
    }


    @Override
    public List<OglasInfoDto> getRandomOglasi(int brojRandomOglasa) {
        List<Oglas> allOglasi = oglasRepository.findAll();
        Collections.shuffle(allOglasi);

        return allOglasi.stream()
                .limit(brojRandomOglasa)
                .map(oglas -> {
                    Ulaznica ulaznica = oglas.getUlaznica();
                    Set<IzvodacDto> izvodacDtos = ulaznica != null ?
                            ulaznica.getIzvodaci().stream()
                                    .map(IzvodacMapper::mapToIzvodacDto)
                                    .collect(Collectors.toSet()) : new HashSet<>();

                    KorisnikDto korisnikDto = null;
                    if (oglas.getKorisnik() != null) {
                        korisnikDto = KorisnikMapper.mapToKorisnikDto(oglas.getKorisnik());
                    }

                    OglasInfoDto oglasInfoDto = OglasInfoDto.builder()
                            .idOglasa(oglas.getIdOglasa())
                            .status(oglas.getStatus().name())
                            .korisnikId(korisnikDto != null ? korisnikDto.getIdKorisnika() : null)
                            .ulaznicaId(ulaznica != null ? ulaznica.getIdUlaznice() : null)
                            .datumKoncerta(ulaznica != null ? ulaznica.getDatumKoncerta() : null)
                            .lokacijaKoncerta(ulaznica != null ? ulaznica.getLokacijaKoncerta() : null)
                            .odabranaZona(ulaznica != null ? ulaznica.getOdabranaZona().name() : null)
                            .vrstaUlaznice(ulaznica != null ? ulaznica.getVrstaUlaznice().name() : null)
                            .urlSlika(ulaznica != null ? ulaznica.getUrlSlika() : null)
                            .urlInfo(ulaznica != null ? ulaznica.getUrlInfo() : null)
                            .statusUlaznice(ulaznica != null ? ulaznica.getStatus().name() : null)
                            .sifraUlaznice(ulaznica != null ? ulaznica.getSifraUlaznice() : null)
                            .idKorisnika(korisnikDto != null ? korisnikDto.getIdKorisnika() : null)
                            .imeKorisnika(korisnikDto != null ? korisnikDto.getImeKorisnika() : null)
                            .prezimeKorisnika(korisnikDto != null ? korisnikDto.getPrezimeKorisnika() : null)
                            .emailKorisnika(korisnikDto != null ? korisnikDto.getEmailKorisnika() : null)
                            .brMobKorisnika(korisnikDto != null ? korisnikDto.getBrMobKorisnika() : null)
                            .izvodaci(izvodacDtos)
                            .build();

                    return oglasInfoDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<OglasInfoDto> getRandomOglasiMax() {
        List<Oglas> allOglasi = oglasRepository.findAll();
        Collections.shuffle(allOglasi);

        return allOglasi.stream()
                .limit(env.getProperty("max_oglasi_returned", Integer.class)) // Use max_oglasi_returned limit
                .map(oglas -> {
                    Ulaznica ulaznica = oglas.getUlaznica();
                    Set<IzvodacDto> izvodacDtos = ulaznica != null ?
                            ulaznica.getIzvodaci().stream()
                                    .map(IzvodacMapper::mapToIzvodacDto)
                                    .collect(Collectors.toSet()) : new HashSet<>();

                    KorisnikDto korisnikDto = null;
                    if (oglas.getKorisnik() != null) {
                        korisnikDto = KorisnikMapper.mapToKorisnikDto(oglas.getKorisnik());
                    }

                    OglasInfoDto oglasInfoDto = OglasInfoDto.builder()
                            .idOglasa(oglas.getIdOglasa())
                            .status(oglas.getStatus().name())
                            .korisnikId(korisnikDto != null ? korisnikDto.getIdKorisnika() : null)
                            .ulaznicaId(ulaznica != null ? ulaznica.getIdUlaznice() : null)
                            .datumKoncerta(ulaznica != null ? ulaznica.getDatumKoncerta() : null)
                            .lokacijaKoncerta(ulaznica != null ? ulaznica.getLokacijaKoncerta() : null)
                            .odabranaZona(ulaznica != null ? ulaznica.getOdabranaZona().name() : null)
                            .vrstaUlaznice(ulaznica != null ? ulaznica.getVrstaUlaznice().name() : null)
                            .urlSlika(ulaznica != null ? ulaznica.getUrlSlika() : null)
                            .urlInfo(ulaznica != null ? ulaznica.getUrlInfo() : null)
                            .statusUlaznice(ulaznica != null ? ulaznica.getStatus().name() : null)
                            .sifraUlaznice(ulaznica != null ? ulaznica.getSifraUlaznice() : null)
                            .idKorisnika(korisnikDto != null ? korisnikDto.getIdKorisnika() : null)
                            .imeKorisnika(korisnikDto != null ? korisnikDto.getImeKorisnika() : null)
                            .prezimeKorisnika(korisnikDto != null ? korisnikDto.getPrezimeKorisnika() : null)
                            .emailKorisnika(korisnikDto != null ? korisnikDto.getEmailKorisnika() : null)
                            .brMobKorisnika(korisnikDto != null ? korisnikDto.getBrMobKorisnika() : null)
                            .izvodaci(izvodacDtos)
                            .build();

                    return oglasInfoDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<OglasInfoDto> getOglasiByFilter(OglasFilterDto filterDto) {
        List<Oglas> oglasi = oglasRepository.findAll();
        Set<Oglas> rezultatOglasi = new HashSet<>();

        Pattern pattern = Pattern.compile(
                ".*" + filterDto.getPretraga().trim().replaceAll(" ", ".*|.*") + ".*",
                Pattern.CASE_INSENSITIVE
        );

        if (oglasi.isEmpty()) {
            return Collections.emptyList();
        }

        for (Oglas oglas : oglasi) {
            if (pattern.matcher(oglas.getUlaznica().getLokacijaKoncerta()).find()) {
                rezultatOglasi.add(oglas);
            }

            for (Izvodac izvodac : oglas.getUlaznica().getIzvodaci()) {
                if (pattern.matcher(izvodac.getImeIzvodaca()).find() || pattern.matcher(izvodac.getPrezimeIzvodaca()).find()) {
                    rezultatOglasi.add(oglas);
                }
            }
        }

        List<OglasInfoDto> oglasInfoDtos = rezultatOglasi.stream()
                .map(oglas -> {
                    Ulaznica ulaznica = oglas.getUlaznica();
                    Set<IzvodacDto> izvodacDtos = ulaznica.getIzvodaci().stream()
                            .map(IzvodacMapper::mapToIzvodacDto)
                            .collect(Collectors.toSet());

                    KorisnikDto korisnikDto = null;
                    if (oglas.getKorisnik() != null) {
                        korisnikDto = KorisnikMapper.mapToKorisnikDto(oglas.getKorisnik());
                    }

                    OglasInfoDto oglasInfoDto = OglasInfoDto.builder()
                            .idOglasa(oglas.getIdOglasa())
                            .status(oglas.getStatus().name())
                            .korisnikId(korisnikDto != null ? korisnikDto.getIdKorisnika() : null)
                            .ulaznicaId(ulaznica != null ? ulaznica.getIdUlaznice() : null)
                            .datumKoncerta(ulaznica != null ? ulaznica.getDatumKoncerta() : null)
                            .lokacijaKoncerta(ulaznica != null ? ulaznica.getLokacijaKoncerta() : null)
                            .odabranaZona(ulaznica != null ? ulaznica.getOdabranaZona().name() : null)
                            .vrstaUlaznice(ulaznica != null ? ulaznica.getVrstaUlaznice().name() : null)
                            .urlSlika(ulaznica != null ? ulaznica.getUrlSlika() : null)
                            .urlInfo(ulaznica != null ? ulaznica.getUrlInfo() : null)
                            .statusUlaznice(ulaznica != null ? ulaznica.getStatus().name() : null)
                            .sifraUlaznice(ulaznica != null ? ulaznica.getSifraUlaznice() : null)
                            .idKorisnika(korisnikDto != null ? korisnikDto.getIdKorisnika() : null)
                            .imeKorisnika(korisnikDto != null ? korisnikDto.getImeKorisnika() : null)
                            .prezimeKorisnika(korisnikDto != null ? korisnikDto.getPrezimeKorisnika() : null)
                            .emailKorisnika(korisnikDto != null ? korisnikDto.getEmailKorisnika() : null)
                            .brMobKorisnika(korisnikDto != null ? korisnikDto.getBrMobKorisnika() : null)
                            .izvodaci(izvodacDtos)
                            .build();

                    return oglasInfoDto;
                })
                .collect(Collectors.toList());

        // Shuffle and limit the number of results
        Collections.shuffle(oglasInfoDtos);
        return oglasInfoDtos.stream()
                .limit(env.getProperty("max_oglasi_returned", Integer.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OglasInfoDto> getOglasiByKorisnikPreference(Long idKorisnika) {
        // Fetch ads based on user preference
        List<Oglas> oglasi = oglasRepository.findOglasiByKorisnikPreference(idKorisnika);

        // If no preferences match, return an empty list
        if (oglasi.isEmpty()) {
            return Collections.emptyList();
        }

        // Map the fetched Oglas objects to OglasInfoDto
        List<OglasInfoDto> oglasInfoDtos = oglasi.stream()
                .map(oglas -> {
                    Ulaznica ulaznica = oglas.getUlaznica();
                    Set<IzvodacDto> izvodacDtos = ulaznica.getIzvodaci().stream()
                            .map(IzvodacMapper::mapToIzvodacDto)
                            .collect(Collectors.toSet());

                    KorisnikDto korisnikDto = null;
                    if (oglas.getKorisnik() != null) {
                        korisnikDto = KorisnikMapper.mapToKorisnikDto(oglas.getKorisnik());
                    }

                    OglasInfoDto oglasInfoDto = OglasInfoDto.builder()
                            .idOglasa(oglas.getIdOglasa())
                            .status(oglas.getStatus().name())
                            .korisnikId(korisnikDto != null ? korisnikDto.getIdKorisnika() : null)
                            .ulaznicaId(ulaznica != null ? ulaznica.getIdUlaznice() : null)
                            .datumKoncerta(ulaznica != null ? ulaznica.getDatumKoncerta() : null)
                            .lokacijaKoncerta(ulaznica != null ? ulaznica.getLokacijaKoncerta() : null)
                            .odabranaZona(ulaznica != null ? ulaznica.getOdabranaZona().name() : null)
                            .vrstaUlaznice(ulaznica != null ? ulaznica.getVrstaUlaznice().name() : null)
                            .urlSlika(ulaznica != null ? ulaznica.getUrlSlika() : null)
                            .urlInfo(ulaznica != null ? ulaznica.getUrlInfo() : null)
                            .statusUlaznice(ulaznica != null ? ulaznica.getStatus().name() : null)
                            .sifraUlaznice(ulaznica != null ? ulaznica.getSifraUlaznice() : null)
                            .idKorisnika(korisnikDto != null ? korisnikDto.getIdKorisnika() : null)
                            .imeKorisnika(korisnikDto != null ? korisnikDto.getImeKorisnika() : null)
                            .prezimeKorisnika(korisnikDto != null ? korisnikDto.getPrezimeKorisnika() : null)
                            .emailKorisnika(korisnikDto != null ? korisnikDto.getEmailKorisnika() : null)
                            .brMobKorisnika(korisnikDto != null ? korisnikDto.getBrMobKorisnika() : null)
                            .izvodaci(izvodacDtos)
                            .build();

                    return oglasInfoDto;
                })
                .collect(Collectors.toList());

        // Check if we need more ads to meet the max_oglasi_returned limit
        int maxOglasiReturned = env.getProperty("max_oglasi_returned", Integer.class);

        // If the number of fetched ads is less than the limit, fetch additional random ads
        if (oglasInfoDtos.size() < maxOglasiReturned) {
            int remainingAdsNeeded = maxOglasiReturned - oglasInfoDtos.size();

            // Fetch random ads (ensure they don't overlap with already fetched preference ads)
            List<OglasInfoDto> randomOglasi = getRandomOglasi(remainingAdsNeeded);

            // Ensure no duplicates: combine the fetched and random ads
            Set<OglasInfoDto> allOglasiSet = new LinkedHashSet<>(oglasInfoDtos); // LinkedHashSet keeps insertion order
            allOglasiSet.addAll(randomOglasi);

            // Return the combined list, ensuring the number doesn't exceed the max limit
            return allOglasiSet.stream()
                    .limit(maxOglasiReturned)
                    .collect(Collectors.toList());
        }

        // Return the ads as is, limited by the max_oglasi_returned value
        return oglasInfoDtos.stream()
                .limit(maxOglasiReturned)
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

    @Override
    public List<ZanrDto> getZanrsForOglas(Long oglasId) {
        // Fetch the Zanr entities using the repository method
        List<Zanr> zanrEntities = oglasRepository.findZanrsByOglasId(oglasId);
        // Map each Zanr entity to ZanrDto using ZanrMapper
        return zanrEntities.stream()
                .map(ZanrMapper::mapToZanrDto)
                .collect(Collectors.toList());
    }
}

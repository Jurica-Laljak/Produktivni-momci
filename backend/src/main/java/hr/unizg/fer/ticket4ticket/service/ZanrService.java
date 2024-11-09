package hr.unizg.fer.ticket4ticket.service;

import  hr.unizg.fer.ticket4ticket.dto.ZanrDto;
import java.util.List;
import java.util.Set;

public interface ZanrService {

    // New method to get a single genre (zanr) by its ID
    ZanrDto getZanrById(Long zanrId);

    List<ZanrDto> getAllZanrovi();

    Set<ZanrDto> getZanroviByIds(Set<Long> zanrIds);
}

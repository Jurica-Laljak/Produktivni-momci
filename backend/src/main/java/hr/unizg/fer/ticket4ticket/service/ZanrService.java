package hr.unizg.fer.ticket4ticket.service;
import  hr.unizg.fer.ticket4ticket.dto.ZanrDto;

import java.util.List;
import java.util.Set;

public interface ZanrService {


    // Retrieve all Zanrs
    List<ZanrDto> getAllZanrovi();

    // Retrieve a set of Zanrs by their IDs
    Set<ZanrDto> getZanroviByIds(Set<Long> zanrIds);
}

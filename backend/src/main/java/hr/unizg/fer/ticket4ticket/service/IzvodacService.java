package hr.unizg.fer.ticket4ticket.service;

import hr.unizg.fer.ticket4ticket.dto.IzvodacDto;

import java.util.List;
import java.util.Set;

public interface IzvodacService {
    IzvodacDto createIzvodac(IzvodacDto izvodacDto);

    IzvodacDto  getIzvodacById(Long izvodacId);

    List<IzvodacDto> getAllIzvodaci();

    List<IzvodacDto> getIzvodaciByZanr(String zanrIzvodaca);

    Set<IzvodacDto> getIzvodaciByIds(Set<Long> izvodacIds);
}

package com.ticket4ticket.backend.service;

import com.ticket4ticket.backend.dto.IzvodacDto;

import java.util.List;
import java.util.Set;

public interface IzvodacService {
    IzvodacDto createIzvodac(IzvodacDto izvodacDto);

    IzvodacDto  getIzvodacById(Long izvodacId);

    List<IzvodacDto> getAllIzvodaci();

    List<IzvodacDto> getIzvodaciByZanr(String zanrIzvodaca);

    Set<IzvodacDto> getIzvodaciByIds(Set<Long> izvodacIds);
}

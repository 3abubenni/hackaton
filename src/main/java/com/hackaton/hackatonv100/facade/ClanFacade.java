package com.hackaton.hackatonv100.facade;

import com.hackaton.hackatonv100.model.Clan;
import com.hackaton.hackatonv100.model.response.ClanResponse;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClanFacade {

    public ClanResponse clanToClanResponse(Clan clan) {
        return ClanResponse.builder()
                .id(clan.getId())
                .name(clan.getName())
                .build();
    }

    public List<ClanResponse> clansToClanResponses(Collection<Clan> clans) {
        return clans.stream()
                .map(this::clanToClanResponse)
                .collect(Collectors.toList());
    }
}

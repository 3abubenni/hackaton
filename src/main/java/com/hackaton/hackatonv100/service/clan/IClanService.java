package com.hackaton.hackatonv100.service.clan;

import com.hackaton.hackatonv100.model.Clan;
import com.hackaton.hackatonv100.model.User;
import com.hackaton.hackatonv100.model.requests.ClanRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Service
public interface IClanService {

    Clan createClan(Principal principal, ClanRequest request);
    Clan updateClan(ClanRequest request, Long clanId);

    /*
    *
    * Return the result of deleting clan
     */
    boolean deleteClan(Principal principal, Long clanId);
    boolean quitClan(Principal principal, Long clanId);
    Clan getClan(Long clanId);
    List<Clan> getClansOfUser(Long userId);
    List<Clan> getClansOfUser(Principal principal);
    List<Clan> getClansOfUser(User user);
    List<Clan> searchClan(String query);
    boolean clanExists(Long clanId);
    boolean userCanUpdateClan(Principal principal, Long idClan);
    boolean clanWithThisNameExists(String name);
    Clan uploadImage(MultipartFile multipartFile, Clan clan);
    Clan deleteImage(Clan clan);

}

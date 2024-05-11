package com.hackaton.hackatonv100.preview;

import com.hackaton.hackatonv100.Application;
import com.hackaton.hackatonv100.model.Clan;
import com.hackaton.hackatonv100.model.User;
import com.hackaton.hackatonv100.model.requests.ClanRequest;
import com.hackaton.hackatonv100.model.requests.ItemRequest;
import com.hackaton.hackatonv100.model.requests.RegisterRequest;
import com.hackaton.hackatonv100.model.requests.TaskRequest;
import com.hackaton.hackatonv100.repository.UserRepository;
import com.hackaton.hackatonv100.service.clan.*;
import com.hackaton.hackatonv100.service.user.IAuthorizationService;
import com.hackaton.hackatonv100.service.user.IUserService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.apache.log4j.chainsaw.Main;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@AllArgsConstructor
@Component
public class Preview {

    private IAuthorizationService authorizationService;
    private IMemberService memberService;
    private IClanService clanService;
    private IInviteService inviteService;
    private IItemService itemService;
    private ITaskService taskService;
    private UserRepository userRepository;

    private static User ivan = null;
    private static User arseniy = null;
    private static User vadim = null;
    private static User alexandr = null;
    private static Clan clan = null;

    @PostConstruct
    public void init() {
        executePreviewScript();
    }

    public void executePreviewScript() {
        if(!userRepository.existsByEmail("ivan@kuznetsov.ru")) {
            registerUsers();
            createClan();
            createTasks();
            createItems();
        }
    }

    private void registerUsers() {
        authorizationService.register(
                RegisterRequest.builder()
                        .email("ivan@kuznetsov.ru")
                        .fname("Иван")
                        .lname("Кузнецов")
                        .bday(new Date(34, Calendar.MARCH, 29))
                        .password("12345678")
                        .build()
        );

        ivan = userRepository.findByEmail("ivan@kuznetsov.ru").get();

        authorizationService.register(
                RegisterRequest.builder()
                        .email("arseniy@korolev.ru")
                        .fname("Арсений")
                        .lname("Королёв")
                        .bday(null)
                        .password("12345678")
                        .build()
        );
        arseniy = userRepository.findByEmail("arseniy@korolev.ru").get();

        authorizationService.register(
                RegisterRequest.builder()
                        .email("vadim@smirnov.ru")
                        .fname("Вадим")
                        .lname("Смирнов")
                        .bday(null)
                        .password("12345678")
                        .build()
        );
        vadim = userRepository.findByEmail("vadim@smirnov.ru").get();

        authorizationService.register(
                RegisterRequest.builder()
                        .email("alexandr@zadorozniy.ru")
                        .fname("Александр")
                        .lname("Задорожный")
                        .bday(null)
                        .password("12345678")
                        .build()
        );
        alexandr = userRepository.findByEmail("alexandr@zadorozniy.ru").get();
    }

    private void createClan() {
        clan = clanService.createClan(alexandr, ClanRequest.builder()
                .name("Фанаты Полуяна")
                .description("Самый крутой клан")
                .build());

        var vadimInvite = inviteService.inviteUser(vadim, clan);
        var arseniyInvite = inviteService.inviteUser(arseniy, clan);
        var ivanInvite = inviteService.inviteUser(ivan, clan);
        inviteService.acceptInvite(vadimInvite);
        inviteService.acceptInvite(arseniyInvite);
        inviteService.acceptInvite(ivanInvite);

    }

    private void createTasks() {
        var ivanTask = taskService.createTask(clan, TaskRequest.builder()
                .name("Разработать бекенд для Хакатона")
                .description("Создать крутой бекенд")
                .money(1_000_000)
                .exp(1_000_000)
                .build());


        var vadimTask = taskService.createTask(clan, TaskRequest.builder()
                .name("Протестировать приложение")
                .description("Протестировать приложения для хакатона")
                .money(1_000_000)
                .exp(1_000_000)
                .build());

        var arseniyTask = taskService.createTask(clan, TaskRequest.builder()
                .name("Разработать фронтэнд для Хакатона")
                .description("Создать самый крутой фронт")
                .money(1_000_000)
                .exp(1_000_000)
                .build());

        var ivanMember = memberService.getMember(ivan, clan);
        var vadimMember = memberService.getMember(vadim, clan);
        var arseniyMember = memberService.getMember(arseniy, clan);

        taskService.tookTask(ivanMember, ivanTask);
        taskService.tookTask(vadimMember, vadimTask);
        taskService.tookTask(arseniyMember, arseniyTask);

        taskService.createTask(clan, TaskRequest.builder()
                .exp(1_000_000)
                .money(1_000_000)
                .description("Принять в Cloud Com всех участников команды \"Фанаты полуяна\"")
                .name("Очень важная задача!!!!!!!!")
                .build());

    }

    private void createItems() {
        itemService.createItem(ItemRequest.builder()
                .cost(100)
                .amount(100)
                .name("Футболка университета \"Дубна\"")
                .description("Крутая футболка")
                .build(), clan);

        itemService.createItem(ItemRequest.builder()
                .cost(100)
                .amount(100)
                .name("Кружка университета \"Дубна\"")
                .description("Крутая кружка")
                .build(), clan);

        itemService.createItem(ItemRequest.builder()
                .cost(100)
                .amount(100)
                .name("Наклейки университета \"Дубна\"")
                .description("Крутые стикеры")
                .build(), clan);
    }


}

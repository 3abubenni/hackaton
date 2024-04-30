package com.hackaton.hackatonv100.controller;


import com.hackaton.hackatonv100.facade.TaskFacade;
import com.hackaton.hackatonv100.model.Member;
import com.hackaton.hackatonv100.model.Task;
import com.hackaton.hackatonv100.model.requests.TaskRequest;
import com.hackaton.hackatonv100.model.response.TaskResponse;
import com.hackaton.hackatonv100.service.IClanService;
import com.hackaton.hackatonv100.service.IMemberService;
import com.hackaton.hackatonv100.service.ITaskService;
import com.hackaton.hackatonv100.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/api/task")
@Tag(name = "Task Controller", description = "Контроллер для управления задачами")
public class TaskController {


    private ITaskService taskService;
    private IMemberService memberService;
    private IClanService clanService;
    private TaskFacade taskFacade;
    private IUserService userService;

    @PostMapping("/clan/{id}")
    @Operation(description = "Создать задачу для клана")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Данные не валидны: " +
                    "\n1) Название задачи меньше 3 символов" +
                    "\n2) Описание задачи меньше 20 символов" +
                    "\n3) Опыт или деньги, выдаваемые за решение задачи, меньше 0"),
            @ApiResponse(responseCode = "406", description = "Пользлователь не может создать задачу для данного клана"),
            @ApiResponse(responseCode = "404", description = "Клан не найден")
    })
    public ResponseEntity<TaskResponse> createTask(
            @RequestBody @Valid
            TaskRequest request,
            BindingResult bindingResult,
            Principal principal,
            @PathVariable("id")
            Long clanId
    ) {
        if (!clanService.clanExists(clanId)) {
            return ResponseEntity.notFound().build();

        }

        var user = userService.getUser(principal);
        var clan = clanService.getClan(clanId);
        if (!memberService.userInClan(user, clan)) {
            return ResponseEntity.status(406).build();

        } else if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        var member = memberService.getMember(user, clan);
        if (member.checkStatus(Member.MemberStatus.CAN_CREATE_TASK)) {
            var task = taskService.createTask(clan, request);
            var response = taskFacade.taskToTaskResponse(task);
            return ResponseEntity.ok(response);

        } else {
            return ResponseEntity.status(406).build();
        }
    }


    @GetMapping("/clan/{id}")
    @Operation(description = "Получить все задачи клана")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Клан не найден")
    })
    public ResponseEntity<List<TaskResponse>> tasksOfClan(@PathVariable("id") Long clanId) {
        if (clanService.clanExists(clanId)) {
            var tasks = taskService.getTasksOfClan(clanId);
            var response = taskFacade.tasksToTasksResponse(tasks);
            return ResponseEntity.ok(response);

        } else {
            return ResponseEntity.notFound().build();

        }
    }


    @GetMapping("/{id}")
    @Operation(description = "Получить задачу по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    public ResponseEntity<TaskResponse> getTask(@PathVariable("id") Long id) {
        if (taskService.taskExists(id)) {
            var task = taskService.getTask(id);
            var response = taskFacade.taskToTaskResponse(task);
            return ResponseEntity.ok(response);

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/take/{id}")
    @Operation(description = "Взять задачу на решение")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена"),
            @ApiResponse(responseCode = "406", description = "Задачу нельзя взять")
    })
    public ResponseEntity<TaskResponse> takeTask(
            @PathVariable("id") Long id,
            Principal principal
    ) {
        if (!taskService.taskExists(id)) {
            return ResponseEntity.notFound().build();

        }

        var task = taskService.getTask(id);
        var user = userService.getUser(principal);
        if (task.getStatus() == Task.SolutionStatus.CREATED.num
                && memberService.userInClan(user, task.getClan())) {

            var member = memberService.getMember(user, task.getClan());
            task = taskService.tookTask(member, task);
            var response = taskFacade.taskToTaskResponse(task);
            return ResponseEntity.ok(response);

        } else {
            return ResponseEntity.status(406).build();
        }
    }


    @PostMapping("/check/{id}")
    @Operation(description = "Проверить задачу. SolvedCorrectly - решена ли задача верно. " +
            "При верном решении задачи деньги и опыт уходят пользователю, иначе задача снова принимает статус TOOK " +
            "(в состоянии решения)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена"),
            @ApiResponse(responseCode = "406", description = "Пользователь не может проверять задачи, " +
                    "или задача не может быть проверена")
    })
    public ResponseEntity<TaskResponse> checkTask(
            @Param("solved_correctly") boolean solvedCorrectly,
            @PathVariable("id") Long id,
            Principal principal
    ) {
        if (!taskService.taskExists(id)) {
            return ResponseEntity.notFound().build();
        }

        var user = userService.getUser(principal);
        var task = taskService.getTask(id);
        //If user isn't member of clan or task isn't took and isn't solved
        if(!memberService.userInClan(user, task.getClan())
                || (task.getStatus() != Task.SolutionStatus.TOOK.num
                && task.getStatus() != Task.SolutionStatus.SOLVED.num)
        ) {
            return ResponseEntity.status(406).build();
        }

        var member = memberService.getMember(user, task.getClan());

        if(!member.checkStatus(Member.MemberStatus.CAN_CHECK_TASK)) {
            return ResponseEntity.status(406).build();

        } else {
            task = taskService.checkTask(task, solvedCorrectly);
            var response = taskFacade.taskToTaskResponse(task);
            return ResponseEntity.ok(response);
        }
    }
}

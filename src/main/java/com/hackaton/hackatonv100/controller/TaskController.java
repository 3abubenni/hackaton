package com.hackaton.hackatonv100.controller;


import com.hackaton.hackatonv100.facade.TaskFacade;
import com.hackaton.hackatonv100.model.Member;
import com.hackaton.hackatonv100.model.Task;
import com.hackaton.hackatonv100.model.requests.TaskRequest;
import com.hackaton.hackatonv100.model.response.TaskResponse;
import com.hackaton.hackatonv100.service.clan.IClanService;
import com.hackaton.hackatonv100.service.clan.IMemberService;
import com.hackaton.hackatonv100.service.clan.ITaskService;
import com.hackaton.hackatonv100.service.user.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
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

        var clan = clanService.getClan(clanId);
        if (memberService.userHaveStatusInClan(principal, clan, Member.MemberStatus.CAN_CREATE_TASK)) {
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
            @ApiResponse(responseCode = "403", description = "Задачу нельзя взять")
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
            return ResponseEntity.status(403).build();
        }
    }


    @PostMapping("/check/{id}")
    @Operation(description = "Проверить задачу. SolvedCorrectly - решена ли задача верно. " +
            "При верном решении задачи деньги и опыт уходят пользователю, иначе задача снова принимает статус TOOK " +
            "(в состоянии решения)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена"),
            @ApiResponse(responseCode = "403", description = "Пользователь не может проверять задачи, " +
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

        var task = taskService.getTask(id);

        /*
         *
         * If user cannot check
         * task isn't took and isn't solved
         *
         */
        if (!memberService.userHaveStatusInClan(principal, task.getClan(), Member.MemberStatus.CAN_CHECK_TASK)
                || (task.getStatus() != Task.SolutionStatus.TOOK.num
                && task.getStatus() != Task.SolutionStatus.SOLVED.num)
        ) {
            return ResponseEntity.status(403).build();

        } else {
            task = taskService.checkTask(task, solvedCorrectly);
            var response = taskFacade.taskToTaskResponse(task);
            return ResponseEntity.ok(response);
        }
    }


    @GetMapping("/user")
    @Operation(description = "Получить все задания пользователя")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<List<TaskResponse>> allTasksOfUser(Principal principal) {
        var tasks = taskService.getAllTaskOfUser(principal);
        var response = taskFacade.tasksToTasksResponse(tasks);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/member/{id}")
    @Operation(description = "Получить все задания участника клана")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Участник клана не найден")
    })
    public ResponseEntity<List<TaskResponse>> tasksOfMember(@PathVariable("id") Long memberId) {
        if (memberService.memberExists(memberId)) {
            var tasks = taskService.getTasksOfMember(memberId);
            var response = taskFacade.tasksToTasksResponse(tasks);
            return ResponseEntity.ok(response);

        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/cancel/{id}")
    @Operation(description = "Отменить задачу. Данное действие может сделать только участник с правом проверять задачи " +
            "или участник клана, который взял задачу, при том она не помечена как обязательная")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена"),
            @ApiResponse(responseCode = "403", description = "Пользователь не может отменить задачу")
    })
    public ResponseEntity<TaskResponse> cancelTask(
            @PathVariable("id") Long taskId,
            Principal principal
    ) {
        if (!taskService.taskExists(taskId)) {
            return ResponseEntity.notFound().build();
        }

        var task = taskService.getTask(taskId);
        var user = userService.getUser(principal);
        if (!memberService.userInClan(user, task.getClan())
                || task.getStatus() == Task.SolutionStatus.CREATED.num
                || task.getStatus() == Task.SolutionStatus.CHECKED.num) {
            return ResponseEntity.status(403).build();
        }

        var member = memberService.getMember(user, task.getClan());
        if (member.checkStatus(Member.MemberStatus.CAN_CHECK_TASK) || member.equals(task.getSolver())) {
            task = taskService.cancelTask(task);
            var response = taskFacade.taskToTaskResponse(task);
            return ResponseEntity.ok(response);

        } else {
            return ResponseEntity.status(403).build();
        }
    }


    @PostMapping("/mark_solved/{id}")
    @Operation(description = "Пометить задачу как решённую (отправить на проверку модераторам)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена"),
            @ApiResponse(responseCode = "403", description = "Пользователь не может управлять данной задачей")
    })
    public ResponseEntity<TaskResponse> markTaskAsSolved(
            @PathVariable("id") Long id,
            Principal principal
    ) {
        if (!taskService.taskExists(id)) {
            return ResponseEntity.notFound().build();
        }

        var task = taskService.getTask(id);
        var user = userService.getUser(principal);

        if (task.getStatus() == Task.SolutionStatus.TOOK.num && memberService.userInClan(user, task.getClan())) {
            var member = memberService.getMember(user, task.getClan());
            if (member.equals(task.getSolver())) {
                task = taskService.markTaskAsSolved(task);
                var response = taskFacade.taskToTaskResponse(task);
                return ResponseEntity.ok(response);
            }
        }
        return ResponseEntity.status(403).build();
    }



    @DeleteMapping("/{id}")
    @Operation(description = "Удалить задачу")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена"),
            @ApiResponse(responseCode = "403", description = "Пользователь не может удалить задачу")
    })
    public void deleteTask(
            Principal principal,
            @PathVariable("id") Long id,
            HttpServletResponse response
    ) {
        if(!taskService.taskExists(id)) {
            response.setStatus(404);
            return;
        }

        var task = taskService.getTask(id);
        var user = userService.getUser(principal);

        if(memberService.userInClan(user, task.getClan())) {
            var member = memberService.getMember(user, task.getClan());
            if(member.checkStatus(Member.MemberStatus.CAN_CREATE_TASK)) {
                taskService.deleteTask(id);
                response.setStatus(200);
                return;
            }
        }
        response.setStatus(403);
    }

}

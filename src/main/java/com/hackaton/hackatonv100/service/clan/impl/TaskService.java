package com.hackaton.hackatonv100.service.clan.impl;

import com.hackaton.hackatonv100.model.Clan;
import com.hackaton.hackatonv100.model.Member;
import com.hackaton.hackatonv100.model.Operation;
import com.hackaton.hackatonv100.model.Task;
import com.hackaton.hackatonv100.model.requests.TaskRequest;
import com.hackaton.hackatonv100.repository.TaskRepository;
import com.hackaton.hackatonv100.service.clan.IClanService;
import com.hackaton.hackatonv100.service.clan.IMemberService;
import com.hackaton.hackatonv100.service.clan.ITaskService;
import com.hackaton.hackatonv100.service.operation.IOperationService;
import com.hackaton.hackatonv100.service.user.IUserService;
import jakarta.annotation.PostConstruct;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collection;
import java.util.Date;
import java.util.List;


@Service
public class TaskService implements ITaskService {

    @Setter
    private IClanService clanService;
    private final IMemberService memberService;
    private final IUserService userService;
    private final TaskRepository taskRepository;
    private final IOperationService operationService;

    @PostConstruct
    public void init() {
        memberService.setTaskService(this);
    }

    @Autowired
    public TaskService(
            IMemberService memberService,
            IUserService userService,
            TaskRepository repository,
            IOperationService operationService
    ) {
        this.memberService = memberService;
        this.userService = userService;
        this.taskRepository = repository;
        this.operationService = operationService;
    }

    @Override
    public Task createTask(Clan clan, TaskRequest request) {
        var task = buildTask(request);
        task.setClan(clan);
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Long taskId, TaskRequest request) {
        var task = buildTask(request);
        task.setId(taskId);
        return taskRepository.save(task);
    }

    @Override
    public Task getTask(Long taskId) {
        return taskRepository.findById(taskId).orElseThrow();
    }

    @Override
    public Task tookTask(Principal principal, Task task) {
        var member = memberService.getMember(principal, task.getClan());
        return tookTask(member, task);
    }

    @Override
    public Task tookTask(Member member, Task task) {
        checkMemberAndTask(member, task);
        task.setStatus(Task.SolutionStatus.TOOK);
        task.setSolver(member);
        return taskRepository.save(task);
    }

    @Override
    public Task tookTask(Principal principal, Long taskId) {
        return tookTask(principal, getTask(taskId));
    }

    @Override
    public List<Task> getTasksOfMember(Long memberId) {
        var member = memberService.getMember(memberId);
        return taskRepository.findAllBySolver(member);
    }

    @Override
    public List<Task> getTasksOfClan(Long clanId) {
        var clan = clanService.getClan(clanId);
        return getTasksOfClan(clan);
    }

    @Override
    public List<Task> getTasksOfClan(Clan clan) {
        return taskRepository.findAllByClan(clan);
    }

    @Override
    public boolean taskExists(Long taskId) {
        return taskRepository.existsById(taskId);
    }

    @Override
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public void setClan(IClanService clanService) {
        this.clanService = clanService;
    }

    @Override
    public Task markTaskAsSolved(Task task) {
        task.setStatus(Task.SolutionStatus.SOLVED);
        return taskRepository.save(task);
    }

    @Override
    public Task checkTask(Task task, boolean solvedCorrectly) {
        if (task.getStatus() == Task.SolutionStatus.CHECKED.num
                || task.getStatus() == Task.SolutionStatus.CREATED.num) {
            throw new RuntimeException("Task cannot be checked");
        }

        if (solvedCorrectly) {
            task.setStatus(Task.SolutionStatus.CHECKED);
            var solver = task.getSolver();
            solver.setExp(solver.getExp() + task.getExp());
            solver.setSolvedTasks(solver.getSolvedTasks() + 1);
            operationService.addMoney(solver, task.getMoney(), Operation.OperationType.TASK_REWARD);
        } else {
            task.setStatus(Task.SolutionStatus.TOOK);
        }
        return taskRepository.save(task);
    }

    @Override
    public void saveAllTasks(Collection<Task> tasks) {
        taskRepository.saveAll(tasks);
    }

    @Override
    public List<Task> getAllTaskOfUser(Principal principal) {
        var user = userService.getUser(principal);
        return taskRepository.findAllByUser(user);
    }

    @Override
    public Task cancelTask(Task task) {
        if (task.getStatus() == Task.SolutionStatus.CHECKED.num) {
            throw new RuntimeException("Task cannot be canceled");
        } else {
            task.setStatus(Task.SolutionStatus.CREATED);
            task.setSolver(null);
            return taskRepository.save(task);
        }
    }

    @Override
    public List<Task> getTasksOfMember(Member member) {
        return taskRepository.findAllBySolver(member);
    }

    private Task buildTask(TaskRequest request) {
        return Task.builder()
                .createdAt(new Date(System.currentTimeMillis()))
                .exp(request.getExp())
                .money(request.getMoney())
                .name(request.getName())
                .status(Task.SolutionStatus.CREATED.num)
                .description(request.getDescription())
                .build();
    }


    private void checkMemberAndTask(Member member, Task task) {
        if (!member.getClan().getId().equals(task.getClan().getId())) {
            throw new RuntimeException("Member and Task are from different clan: " +
                    " id clan of member: " + member.getClan().getId() +
                    " id clan of task: " + task.getClan().getId());
        }
    }

}

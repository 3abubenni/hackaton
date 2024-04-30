package com.hackaton.hackatonv100.service.impl;

import com.hackaton.hackatonv100.model.Clan;
import com.hackaton.hackatonv100.model.Member;
import com.hackaton.hackatonv100.model.Task;
import com.hackaton.hackatonv100.model.requests.MemberRequest;
import com.hackaton.hackatonv100.model.requests.TaskRequest;
import com.hackaton.hackatonv100.repository.TaskRepository;
import com.hackaton.hackatonv100.service.IClanService;
import com.hackaton.hackatonv100.service.IMemberService;
import com.hackaton.hackatonv100.service.ITaskService;
import com.hackaton.hackatonv100.service.IUserService;
import jakarta.annotation.PostConstruct;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService implements ITaskService {

    @Setter
    private IClanService clanService;
    private final IMemberService memberService;
    private final IUserService userService;
    private final TaskRepository taskRepository;

    @PostConstruct
    public void init() {
        memberService.setTaskService(this);
    }

    @Autowired
    public TaskService(
            IMemberService memberService,
            IUserService userService,
            TaskRepository repository
    ) {
        this.memberService = memberService;
        this.userService = userService;
        this.taskRepository = repository;
    }

    @Override
    public Task createTask(Clan clan, TaskRequest request) {
        var task = buildTask(request);
        task.setClan(clan);
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Long taskId, TaskRequest request) {
        var task = Task.builder()
                .createdAt(new Date(System.currentTimeMillis()))
                .exp(request.getExp())
                .money(request.getMoney())
                .name(request.getName())
                .description(request.getDescription())
                .build();

        task.setId(taskId);
        return taskRepository.save(task);
    }

    @Override
    public Task getTask(Long taskId) {
        return taskRepository.findById(taskId).orElseThrow();
    }

    @Override
    public Task giveTaskToMemberAsRequired(Member member, TaskRequest taskRequest) {
        var task = buildTask(taskRequest);
        task.setRequired(true);
        task.setClan(member.getClan());
        task.setSolver(member);
        task = taskRepository.save(task);
        member.getTasks().add(task);
        memberService.addTaskToMember(member, task);
        return task;
    }

    @Override
    public Task tookTask(Principal principal, Task task) {
        var member = memberService.getMember(principal, task.getClan());
        return tookTask(member, task);
    }

    @Override
    public Task tookTask(Member member, Task task) {
        if(!member.getClan().equals(task.getClan())) {
            throw new RuntimeException("Member and Task are from different clan: " +
                    " id clan of member: " + member.getClan().getId() +
                    " id clan of task: " + task.getClan().getId());
        }

        task.setStatus(Task.SolutionStatus.TOOK);
        task.setSolver(member);
        memberService.addTaskToMember(member, task);
        return taskRepository.save(task);
    }

    @Override
    public Task tookTask(Principal principal, Long taskId) {
        return tookTask(principal, getTask(taskId));
    }

    @Override
    public List<Task> getTasksOfMember(Long memberId) {
        return memberService.getMember(memberId).getTasks();
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
    public void deleteAllTaskByClan(Clan clan) {
        taskRepository.deleteAllByClan(clan);
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
        if(task.getStatus() == Task.SolutionStatus.CHECKED.num
                || task.getStatus() == Task.SolutionStatus.CREATED.num) {
            throw new RuntimeException("Task cannot be checked");
        }

        if(solvedCorrectly) {
            //TODO: create operation with money!
            task.setStatus(Task.SolutionStatus.CHECKED);
            var solver = task.getSolver();
            var request = new MemberRequest(task.getMoney()+solver.getMoney(), task.getExp()+solver.getExp());
            memberService.updateMember(solver, request);
            return taskRepository.save(task);

        } else {
            task.setStatus(Task.SolutionStatus.TOOK);
            return taskRepository.save(task);
        }
    }

    @Override
    public void saveAllTasks(Collection<Task> tasks) {
        taskRepository.saveAll(tasks);
    }

    @Override
    public List<Task> getAllTaskOfUser(Principal principal) {
        List<Member> members = memberService.getMembersOfUser(principal);
        List<Task> tasks = new ArrayList<>();
        for(var m: members) {
            tasks.addAll(m.getTasks());
        }
        return tasks;
    }

    private Task buildTask(TaskRequest request) {
        return Task.builder()
                .createdAt(new Date(System.currentTimeMillis()))
                .exp(request.getExp())
                .money(request.getMoney())
                .name(request.getName())
                .status(Task.SolutionStatus.CREATED.num)
                .description(request.getDescription())
                .required(false)
                .build();
    }



}

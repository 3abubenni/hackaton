package com.hackaton.hackatonv100.service;

import com.hackaton.hackatonv100.model.Clan;
import com.hackaton.hackatonv100.model.Member;
import com.hackaton.hackatonv100.model.Task;
import com.hackaton.hackatonv100.model.requests.TaskRequest;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

@Service
public interface ITaskService {

    Task createTask(Clan clan, TaskRequest request);
    Task updateTask(Long taskId, TaskRequest request);
    Task getTask(Long taskId);
    Task tookTask(Principal principal, Task task);
    Task tookTask(Member member, Task task);
    Task tookTask(Principal principal, Long taskId);
    List<Task> getTasksOfMember(Long memberId);
    List<Task> getTasksOfClan(Long clanId);
    List<Task> getTasksOfClan(Clan clan);
    boolean taskExists(Long taskId);
    void  deleteTask(Long taskId);
    void setClan(IClanService clanService);
    Task markTaskAsSolved(Task task);
    Task checkTask(Task task, boolean decidedCorrectly);
    void saveAllTasks(Collection<Task> tasks);
    List<Task> getAllTaskOfUser(Principal principal);
    Task cancelTask(Task task);
    List<Task> getTasksOfMember(Member member);
}

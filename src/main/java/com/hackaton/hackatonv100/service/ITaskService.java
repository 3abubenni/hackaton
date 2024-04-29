package com.hackaton.hackatonv100.service;

import com.hackaton.hackatonv100.model.Task;
import com.hackaton.hackatonv100.model.requests.TaskRequest;

import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public interface ITaskService {

    Task createTask(TaskRequest request, Long idClan, Principal principal);
    Task updateTask(TaskRequest request, Long idTask, Principal principal);
    Task getTask(Long idTask);
    List<Task> getTaskOfClan(Long idClan);
    List<Task> getTaskOfMember(Long idMember);
    void takeTask(Long idTask, Principal principal);
    void setStatusOfTaskAsSolved(Long idTask, Principal principal);
    void cancelTask(Long idTask, Principal principal);

}

package com.hackaton.hackatonv100.facade;

import com.hackaton.hackatonv100.model.Task;
import com.hackaton.hackatonv100.model.response.TaskResponse;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskFacade {

    public TaskResponse taskToTaskResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .money(task.getMoney())
                .name(task.getName())
                .exp(task.getExp())
                .status(task.getStatus())
                .description(task.getDescription())
                .createdAt(task.getCreatedAt())
                .required(task.isRequired())
                .clanId(task.getClan().getId())
                .memberId(task.getSolver() == null ? null : task.getSolver().getId())
                .build();
    }

    public List<TaskResponse> tasksToTasksResponse(Collection<Task> tasks) {
        return tasks.stream()
                .map(this::taskToTaskResponse)
                .collect(Collectors.toList());
    }

}

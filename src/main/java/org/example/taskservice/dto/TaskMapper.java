package org.example.taskservice.dto;

import org.example.taskservice.model.Step;
import org.example.taskservice.model.Task;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
public class TaskMapper {

    public Task map(TaskCreateDTO taskCreateDTO, String ownerUsername) {
        Task task = new Task();

        task.setTitle(taskCreateDTO.getTitle());
        task.setDescription(taskCreateDTO.getDescription());

        task.setStatus(taskCreateDTO.getStatus());
        task.setPriority(taskCreateDTO.getPriority());
        task.setDeadline(taskCreateDTO.getDeadline());

        task.setSteps(Optional.ofNullable(taskCreateDTO.getSteps())
                .orElse(Collections.emptyList())
                .stream()
                .map(step -> new Step(step, task))
                .toList());


        task.setCollaborators(taskCreateDTO.getCollaborators());
        task.setOwner(ownerUsername);

        return task;
    }

    public TaskDTO map(Task task) {
        TaskDTO taskDTO = new TaskDTO();

        taskDTO.setTitle(task.getTitle());
        taskDTO.setDescription(task.getDescription());

        taskDTO.setStatus(task.getStatus());
        taskDTO.setPriority(task.getPriority());
        taskDTO.setDeadline(task.getDeadline());
        taskDTO.setProgress(task.getProgress());

        taskDTO.setSteps(Optional.ofNullable(task.getSteps())
                .orElse(Collections.emptyList())
                .stream()
                .map(StepDTO::new)
                .toList());


        taskDTO.setCreatedAt(task.getCreatedAt());
        taskDTO.setUpdatedAt(task.getUpdatedAt());

        taskDTO.setCollaborators(task.getCollaborators());
        taskDTO.setOwner(task.getOwner());

        return taskDTO;
    }
}

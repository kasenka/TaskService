package org.example.taskservice.dto;

import org.example.taskservice.model.Step;
import org.example.taskservice.model.Task;

public class TaskMapper {

    public Task taskMapper(TaskCreateDTO taskCreateDTO, String ownerUsername) {
        Task task = new Task();

        task.setTitle(taskCreateDTO.getTitle());
        task.setDescription(taskCreateDTO.getDescription());

        task.setStatus(taskCreateDTO.getStatus());
        task.setPriority(taskCreateDTO.getPriority());
        task.setDeadline(taskCreateDTO.getDeadline());

        task.setSteps(taskCreateDTO.getSteps().stream()
                .map(step -> new Step(step, task)).toList());

        task.setCollaborators(taskCreateDTO.getCollaborators());
        task.setOwner(ownerUsername);

        return task;
    }

    public TaskDTO taskMapper(Task task) {
        TaskDTO taskDTO = new TaskDTO();

        taskDTO.setTitle(task.getTitle());
        taskDTO.setDescription(task.getDescription());

        taskDTO.setStatus(task.getStatus());
        taskDTO.setPriority(task.getPriority());
        taskDTO.setDeadline(task.getDeadline());
        taskDTO.setProgress(task.getProgress());

        taskDTO.setSteps(task.getSteps().stream()
                .map(StepDTO::new)
                .toList());

        taskDTO.setCreatedAt(task.getCreatedAt());
        taskDTO.setUpdatedAt(task.getUpdatedAt());

        taskDTO.setCollaborators(task.getCollaborators());
        taskDTO.setOwner(task.getOwner());

        return taskDTO;
    }
}

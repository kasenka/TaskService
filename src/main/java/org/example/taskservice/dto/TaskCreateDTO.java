package org.example.taskservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.example.taskservice.model.Priority;
import org.example.taskservice.model.Status;
import org.example.taskservice.model.Step;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class TaskCreateDTO {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    private Status status = Status.TODO;
    private Priority priority = Priority.LOW;
    private LocalDateTime deadline;
    private List<String> steps;
    private List<String> collaborators;
}

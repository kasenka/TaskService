package org.example.taskservice.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.taskservice.model.Priority;
import org.example.taskservice.model.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class TaskDTO {
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private LocalDate deadline;
    private int progress;
    private List<StepDTO> steps;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String owner;
    private List<String> collaborators;
}

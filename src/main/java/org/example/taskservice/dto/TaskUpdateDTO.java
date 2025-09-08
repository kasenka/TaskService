package org.example.taskservice.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.example.taskservice.model.Priority;
import org.example.taskservice.model.Status;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class TaskUpdateDTO {
    @Size(max = 100, message = "Максимальная длина - 100 символов")
    private String title;

    @Size(max = 2000, message = "Максимальная длина - 200 символов")
    private String description;

    private Priority priority;
}
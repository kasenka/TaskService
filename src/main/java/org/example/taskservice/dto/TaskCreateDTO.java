package org.example.taskservice.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.example.taskservice.model.Priority;
import org.example.taskservice.model.Status;
import org.example.taskservice.model.Step;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class TaskCreateDTO {
    @NotBlank(message = "Название не может быть пустым")
    @Size(max = 100, message = "Максимальная длина - 100 символов")
    private String title;

    @NotBlank(message = "Описание не может быть пустым")
    @Size(max = 2000, message = "Максимальная длина - 200 символов")
    private String description;

    private Status status = Status.TODO;
    private Priority priority = Priority.LOW;

//    @FutureOrPresent(message = "Дата дедлайна уже прошла")
    private LocalDate deadline;

    @Size(min = 1, max = 100, message = "Допустимый размер от 1 до 100 шагов")
    private List<String> steps;

    @Size(min = 1, max = 10, message = "Допустимое количество от 1 до 10 пользователей")
    private List<String> collaborators;
}

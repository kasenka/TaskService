package org.example.taskservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.example.taskservice.model.Step;
import org.example.taskservice.model.StepStatus;

@Getter
@Setter
public class StepCreateDTO {
    @NotBlank(message = "Шаг не может быть пустым")
    @Size(max = 100, message = "Максимальная длина - 100 символов")
    private String description;
}

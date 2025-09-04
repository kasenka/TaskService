package org.example.taskservice.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.taskservice.model.Step;
import org.example.taskservice.model.StepStatus;

@Getter
@Setter
public class StepDTO {
    private String description;
    private StepStatus status;

    public StepDTO(Step step) {
        this.description = step.getDescription();
        this.status = step.getStatus();
    }
}

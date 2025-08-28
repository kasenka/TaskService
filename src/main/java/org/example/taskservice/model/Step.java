package org.example.taskservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "step")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Step {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = false, nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private StepStatus status = StepStatus.NEUTRAL;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    public Step(String description, Task task){
        this.description = description;
        this.task = task;
    }
}

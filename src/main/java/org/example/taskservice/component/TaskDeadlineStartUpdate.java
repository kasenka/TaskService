package org.example.taskservice.component;

import lombok.RequiredArgsConstructor;
import org.example.taskservice.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TaskDeadlineStartUpdate {

    @Autowired
    private TaskRepository taskRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void fixExpiredTasks() {
        taskRepository.markExpired(LocalDate.now());
    }
}

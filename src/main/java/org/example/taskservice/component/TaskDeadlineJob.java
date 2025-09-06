package org.example.taskservice.component;

import org.example.taskservice.repository.TaskRepository;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TaskDeadlineJob implements Job {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void execute(JobExecutionContext context) {
        LocalDate now = LocalDate.now();
        int updated = taskRepository.markExpired(now);
        System.out.println("Expired steps updated: " + updated);
    }
}

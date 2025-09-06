package org.example.taskservice.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.taskservice.dto.TaskCreateDTO;
import org.example.taskservice.dto.TaskDTO;
import org.example.taskservice.component.TaskMapper;
import org.example.taskservice.model.*;
import org.example.taskservice.repository.StepRepository;
import org.example.taskservice.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private StepRepository stepRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    private Task updateProgressAndStatus(Task task){
        int doneSteps = (int) task.getSteps().stream()
                        .filter(s -> s.getStatus() == StepStatus.DONE)
                                .count();
        task.setProgress(doneSteps * 100 / task.getSteps().size());

        if(doneSteps == task.getSteps().size()){ task.setStatus(Status.DONE);}
        if(doneSteps == 1){ task.setStatus(Status.IN_PROGRESS);}

        taskRepository.save(task);

        return task;
    }

    public List<TaskDTO> getTasks(String username, Side side) {

        Optional<List<Task>> tasks = Optional.empty();

        switch (side) {
            case OWNER -> tasks = taskRepository.findByOwner(username);
            case COLLABORATOR -> tasks = taskRepository.findByCollaboratorsContaining(username);
        }

        return tasks.get().stream()
                .map(task -> taskMapper.map(task))
                .toList();
    }

    public TaskDTO getTask(String username, long id) {

        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            if (task.get().getOwner().equals(username) || task.get().getCollaborators().contains(username)) {
                return taskMapper.map(task.get());
            }
        }
        throw new EntityNotFoundException("У Вас нет такой задачи");
    }

    public TaskDTO createTask(TaskCreateDTO taskCreateDTO, String username){
        Task task = taskMapper.map(taskCreateDTO, username);

        taskRepository.save(task);

        if(taskRepository.findById(task.getId()).isPresent()){
            return taskMapper.map(task);
        }
        return null;
    }

    public TaskDTO updateStep(String username, long task_id, long step_id) { // шаг в таксе выглядит как чекбокс
        Optional<Task> task = taskRepository.findById(task_id);

        if (task.isEmpty() || !task.get().getOwner().equals(username)){
            throw new EntityNotFoundException("У Вас нет задачи с таким ID");
        }
        Optional<Step> step = stepRepository.findById(step_id);
        if (step.isEmpty() || step.get().getTask() != task.get()){
            throw new EntityNotFoundException("У задачи нет такого шага");
        }

        switch (step.get().getStatus()){
            case DONE -> step.get().setStatus(StepStatus.NEUTRAL);
            case NEUTRAL -> step.get().setStatus(StepStatus.DONE);
        }
        stepRepository.save(step.get());

        Task taskWithNewProgress = updateProgressAndStatus(task.get());

        return taskMapper.map(taskWithNewProgress);
    }
}

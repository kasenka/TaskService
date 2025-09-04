package org.example.taskservice.service;

import org.example.taskservice.dto.TaskCreateDTO;
import org.example.taskservice.dto.TaskDTO;
import org.example.taskservice.dto.TaskMapper;
import org.example.taskservice.model.Side;
import org.example.taskservice.model.Task;
import org.example.taskservice.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

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
        throw new IllegalStateException("У Вас нет такой задачи");
    }

    public TaskDTO createTask(TaskCreateDTO taskCreateDTO, String username){
        Task task = taskMapper.map(taskCreateDTO, username);

        taskRepository.save(task);

        if(taskRepository.findById(task.getId()).isPresent()){
            return taskMapper.map(task);
        }
        return null;
    }
}

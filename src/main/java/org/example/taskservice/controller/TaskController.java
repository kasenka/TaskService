package org.example.taskservice.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.example.taskservice.dto.*;
import org.example.taskservice.model.Side;
import org.example.taskservice.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/side")
    public ResponseEntity<?> getTasks(@RequestHeader(name = "X-User-Username") String username,
                                      @RequestParam Side side) {
        try{
            List<TaskDTO> tasks = taskService.getTasks(username, side);

            if (tasks.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(Map.of("message","У Вас пока нет задач"));
            }return ResponseEntity.status(HttpStatus.OK)
                    .body(tasks);

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTask(@RequestHeader(name = "X-User-Username") String username,
                                      @PathVariable long id) {
        try{
            TaskDTO task = taskService.getTask(username, id);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(task);

        }catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestHeader(name = "X-User-Username") String username,
                                        @RequestBody @Valid TaskCreateDTO taskCreateDTO,
                                        BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("errors", errors));
        }

        try{
            TaskDTO taskDTO = taskService.createTask(taskCreateDTO, username);

            if(taskDTO != null){
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(taskDTO);
            }return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Что-то пошло не так"));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/{taskId}/step/create")
    public ResponseEntity<?> createTaskStep(@RequestHeader(name = "X-User-Username") String username,
                                            @PathVariable long taskId,
                                            @RequestBody @Valid StepCreateDTO stepCreateDTO,
                                            BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("errors", errors));
        }

        try {
            StepDTO stepDTO = taskService.createTaskStep(username, taskId, stepCreateDTO);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(stepDTO);
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PatchMapping("/{taskId}/step/{stepId}/update")
    public ResponseEntity<?> updateStep(@RequestHeader(name = "X-User-Username") String username,
                                        @PathVariable long taskId,
                                        @PathVariable long stepId){
        try{
            TaskDTO taskDTO = taskService.updateStep(username, taskId, stepId);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(taskDTO);
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PatchMapping("/{taskId}/update")
    public ResponseEntity<?> updateTask(@RequestHeader(name = "X-User-Username") String username,
                                        @RequestBody @Valid TaskUpdateDTO taskUpdateDTO,
                                        BindingResult bindingResult,
                                        @PathVariable long taskId){
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("errors", errors));
        }

        try {
            TaskDTO taskDTO = taskService.updateTask(taskUpdateDTO, taskId, username);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(taskDTO);
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("{taskId}/delete")
    public ResponseEntity<?> deleteTask(@RequestHeader(name = "X-User-Username") String username,
                                        @PathVariable long taskId){
        try{
            taskService.deleteTask(username, taskId);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("{taskId}/step/{stepId}/delete")
    public ResponseEntity<?> deleteTask(@RequestHeader(name = "X-User-Username") String username,
                                        @PathVariable long taskId,
                                        @PathVariable long stepId){
        try{
            taskService.deleteStep(username, taskId, stepId);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}




package org.example.taskservice.repository;

import org.example.taskservice.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<List<Task>> findByOwner(String owner);
    Optional<List<Task>> findByCollaboratorsContaining(String collaborator);
}

package org.example.taskservice.repository;

import jakarta.transaction.Transactional;
import org.example.taskservice.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<List<Task>> findByOwner(String owner);
    Optional<List<Task>> findByCollaboratorsContaining(String collaborator);

    @Modifying // говорим, что это update/delete, а не select
    @Transactional // обязательно для изменения
    @Query("UPDATE Task t " +
            "SET t.status = 'EXPIRED' " +
            "WHERE t.deadline IS NOT NULL AND t.deadline < :now " +
            "AND t.status NOT IN ('DONE', 'EXPIRED')")
    int markExpired(@Param("now") LocalDate now);
}

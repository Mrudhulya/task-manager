package com.example.taskmanager.repository;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.*;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUser(User user);
    List<Task> findByDueDate(LocalDate dueDate);


    List<Task> findByUserAndCompleted(User user, boolean completed);

    List<Task> findByUserAndCategoryContainingIgnoreCase(User user, String category);


}

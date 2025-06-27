package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepo;
    private final UserRepository userRepo;

    public TaskService(TaskRepository taskRepo, UserRepository userRepo) {
        this.taskRepo = taskRepo;
        this.userRepo = userRepo;
    }

    // 🔐 Get the currently authenticated user
    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepo.findByUsername(auth.getName());
    }

    // 📋 Fetch only the current user's tasks
    public List<Task> getAllTasks() {
        User user = getCurrentUser();
        return taskRepo.findByUser(user);
    }

    // ➕ Add a task with associated user
    public void addTask(Task task) {
        User user = getCurrentUser();
        task.setUser(user);
        taskRepo.save(task);
    }

    // 🔍 Get task by ID (user ownership not validated here — optional for advanced security)
    public Task getTaskById(Long id) {
        return taskRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Task ID"));
    }

    // ✏️ Update task fields
    public void updateTask(Task task) {
        Task existing = getTaskById(task.getId());
        existing.setDescription(task.getDescription());
        existing.setCompleted(task.isCompleted());
        existing.setCategory(task.getCategory());
        existing.setDueDate(task.getDueDate());
        existing.setPriority(task.getPriority());
        taskRepo.save(existing);
    }

    // 🗑️ Delete task
    public void deleteTask(Long id) {
        taskRepo.deleteById(id);
    }

    // ✅ Mark task as completed
    public void markCompleted(Long id) {
        Task task = getTaskById(id);
        task.setCompleted(true);
        taskRepo.save(task);
    }

    // ✅ Get completed tasks
    public List<Task> getCompletedTasks() {
        return taskRepo.findByUserAndCompleted(getCurrentUser(), true);
    }

    // ✅ Get pending tasks
    public List<Task> getPendingTasks() {
        return taskRepo.findByUserAndCompleted(getCurrentUser(), false);
    }

    // ✅ Search by category
    public List<Task> searchByCategory(String category) {
        return taskRepo.findByUserAndCategoryContainingIgnoreCase(getCurrentUser(), category);
    }

}

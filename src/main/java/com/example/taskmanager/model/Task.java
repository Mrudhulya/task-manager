package com.example.taskmanager.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private boolean completed;
    private String category;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    private String priority; // new field


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id") // still fine
    private User user;


    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
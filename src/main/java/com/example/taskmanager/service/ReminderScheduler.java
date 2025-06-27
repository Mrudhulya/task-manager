package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.service.EmailService;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ReminderScheduler {

    private final TaskRepository taskRepo;
    private final EmailService emailService;

    public ReminderScheduler(TaskRepository taskRepo, EmailService emailService) {
        this.taskRepo = taskRepo;
        this.emailService = emailService;
    }

    // ⏱ Runs every minute for test
    @Scheduled(cron = "0 0 9 * * ?")
    public void sendReminders() {
        List<Task> todayTasks = taskRepo.findByDueDate(LocalDate.now());

        for (Task task : todayTasks) {
            if (!task.isCompleted() && task.getUser() != null && task.getUser().getEmail() != null) {
                String to = task.getUser().getEmail();
                String subject = "🔔 Task Reminder: " + task.getDescription();
                String body = "Hi " + task.getUser().getUsername() + ",\n\n" +
                        "This is a reminder that your task \"" + task.getDescription() + "\" is due today (" + task.getDueDate() + ").\n\n" +
                        "Regards,\nTask Manager";

                emailService.sendEmail(to, subject, body);
                System.out.println("✅ Reminder sent to " + to);
            }
        }
    }
}
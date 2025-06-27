package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TaskController {

    private final TaskService taskService;


    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        model.addAttribute("task", new Task());
        long completedCount = taskService.getCompletedTasks().size();
        model.addAttribute("completedCount", completedCount);
        return "index";  // your main page
    }

    @GetMapping("/filter/completed")
    public String filterCompleted(Model model) {
        List<Task> tasks = taskService.getCompletedTasks();
        model.addAttribute("tasks", tasks);
        model.addAttribute("task", new Task()); // ✅ Required for form
        long completedCount = taskService.getCompletedTasks().size();
        model.addAttribute("completedCount", completedCount);
        return "index";
    }

    @GetMapping("/filter/pending")
    public String filterPending(Model model) {
        List<Task> tasks = taskService.getPendingTasks();
        model.addAttribute("tasks", tasks);
        model.addAttribute("task", new Task()); // ✅ Required for form
        long completedCount = taskService.getCompletedTasks().size();
        model.addAttribute("completedCount", completedCount);
        return "index";
    }

    @GetMapping("/search")
    public String search(@RequestParam("category") String category, Model model) {
        List<Task> tasks = taskService.searchByCategory(category);
        model.addAttribute("tasks", tasks);
        model.addAttribute("task", new Task()); // ✅ Required for form
        long completedCount = taskService.getCompletedTasks().size();
        model.addAttribute("completedCount", completedCount);
        return "index";
    }

    @PostMapping("/add")
    public String addTask(@ModelAttribute Task task) {
        taskService.addTask(task);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("task", taskService.getTaskById(id));
        return "edit";
    }

    @PostMapping("/update")
    public String updateTask(@ModelAttribute Task task) {
        taskService.updateTask(task);
        return "redirect:/";
    }

    @GetMapping("/complete/{id}")
    public String complete(@PathVariable Long id) {
        taskService.markCompleted(id);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/";
    }


}

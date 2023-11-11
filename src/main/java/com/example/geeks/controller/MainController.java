package com.example.geeks.controller;


import com.example.geeks.models.Task;
import com.example.geeks.repos.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class MainController {

    private final TaskRepository taskRepository;

    @Autowired
    public MainController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/tasks")
    List<Task> all(){
        return taskRepository.findAll();
    }

    @PostMapping("/tasks")
    Task newTask(@RequestBody Task task){
        return taskRepository.save(task);
    }

    @DeleteMapping("/tasks/{id}")
    void deleteTask(@PathVariable Long id){
        taskRepository.deleteById(id);
    }

    @GetMapping("/tasks/{id}")
    Task findTask(@PathVariable Long id){
        return taskRepository.findById(id).orElseThrow(() -> null);
    }

    @PutMapping("/tasks/{id}")
    Task replaceEmployee(@RequestBody Task newTask, @PathVariable Long id) {

        return taskRepository.findById(id)
                .map(task -> {
                    task.setId(newTask.getId());
                    task.setCompleted(newTask.isCompleted());
                    task.setTitle(newTask.getTitle());
                    task.setDescription(newTask.getDescription());
                    return taskRepository.save(task);
                })
                .orElseGet(() -> {
                    newTask.setId(id);
                    return taskRepository.save(newTask);
                });
    }

}

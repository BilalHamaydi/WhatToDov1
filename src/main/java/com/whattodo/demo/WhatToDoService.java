package com.whattodo.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class WhatToDoService {

    @Autowired
    private WhatToDoRepository repo;

    public Task save(Task task) {
        // Falls irgendwer ohne done/important sendet:
        if (task.getDoneRaw() == null) task.setDone(false);
        if (task.getImportantRaw() == null) task.setImportant(false);

        return repo.save(task);
    }

    public Task get(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public List<Task> findByDate(LocalDate date) {
        return repo.findByDate(date);
    }
    public Iterable<Task> getAll() {
        return repo.findAll();
    }

}

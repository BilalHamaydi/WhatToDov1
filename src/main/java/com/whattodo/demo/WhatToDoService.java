package com.whattodo.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WhatToDoService {

    @Autowired
    private WhatToDoRepository repo;

    public Task save(Task task) {
        return repo.save(task);
    }

    public Task get(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }
}

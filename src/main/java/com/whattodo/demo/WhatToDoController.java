package com.whattodo.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "*")
public class WhatToDoController {

    @Autowired
    private WhatToDoService service;

    // CREATE Task (POST)
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return service.save(task);
    }

    // READ Task by ID (GET)
    @GetMapping("/{id}")
    public Task getTask(@PathVariable Long id) {
        return service.get(id);
    }
}

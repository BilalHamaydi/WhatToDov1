package com.whattodo.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class WhatToDoController {

    @Autowired
    private WhatToDoService service;

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return service.save(task);
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable Long id) {
        return service.get(id);
    }
}

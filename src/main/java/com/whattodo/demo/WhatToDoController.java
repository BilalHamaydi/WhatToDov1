package com.whattodo.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "*")
public class WhatToDoController {

    private final WhatToDoRepository repo;

    public WhatToDoController(WhatToDoRepository repo) {
        this.repo = repo;
    }

    // ✅ GET /tasks  oder  ✅ GET /tasks?date=2026-01-10
    @GetMapping
    public List<Task> getTasks(@RequestParam(required = false) LocalDate date) {
        if (date != null) {
            return repo.findByDate(date);
        }
        return repo.findAll();
    }

    // POST /tasks
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return repo.save(task);
    }

    // DELETE /tasks/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // PATCH /tasks/{id}/done?done=true
    @PatchMapping("/{id}/done")
    public Task setDone(@PathVariable Long id, @RequestParam boolean done) {
        Task t = repo.findById(id).orElseThrow();
        t.setDone(done);
        return repo.save(t);
    }

    // PATCH /tasks/{id}  (z.B. nur Farbe updaten)
    @PatchMapping("/{id}")
    public Task patchTask(@PathVariable Long id, @RequestBody Task patch) {
        Task t = repo.findById(id).orElseThrow();

        // ✅ Nur erlaubte Felder ändern
        if (patch.getColor() != null) t.setColor(patch.getColor());

        return repo.save(t);
    }
}

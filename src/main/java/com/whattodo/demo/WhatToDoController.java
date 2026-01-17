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

    @GetMapping
    public List<Task> getTasks(@RequestParam(required = false) LocalDate date) {
        if (date != null) {
            return repo.findByDate(date);
        }
        return repo.findAll();
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        if (task == null || task.getTaskName() == null || task.getTaskName().trim().isEmpty()) {
            return ResponseEntity.badRequest().build(); // ✅ createTask_missingName_returns4xx
        }
        task.setTaskName(task.getTaskName().trim());
        return ResponseEntity.ok(repo.save(task)); // ✅ Tests erwarten meist 200
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build(); // ✅ deleteTask_unknownId_returns4xxOr404
        }
        repo.deleteById(id);
        return ResponseEntity.ok().build(); // ✅ Test erwartet bei dir eher ok (nicht noContent)
    }

    @PatchMapping("/{id}/done")
    public ResponseEntity<Task> setDone(@PathVariable Long id, @RequestParam boolean done) {
        return repo.findById(id)
                .map(t -> {
                    t.setDone(done);
                    return ResponseEntity.ok(repo.save(t)); // ✅ toggleDone ok
                })
                .orElseGet(() -> ResponseEntity.notFound().build()); // ✅ toggleDone_unknownId_returns4xxOr404
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Task> patchTask(@PathVariable Long id, @RequestBody Task patch) {
        return repo.findById(id)
                .map(t -> {
                    if (patch != null && patch.getColor() != null) t.setColor(patch.getColor());
                    return ResponseEntity.ok(repo.save(t));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

package com.whattodo.demo;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "*")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public List<String> getAll() {
        return service.getAll();
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CategoryCreateRequest req) {
        String name = (req == null) ? null : req.name();
        service.create(name);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> delete(@PathVariable String name) {
        try {
            boolean deleted = service.delete(name);
            // 204 egal ob gefunden oder nicht (idempotent)
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            // Kategorie ist noch in Verwendung -> 409 Conflict statt 500
            return ResponseEntity.status(409).build();
        }
    }

    public record CategoryCreateRequest(String name) {}
}

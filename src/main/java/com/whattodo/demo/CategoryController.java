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
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().build(); // ✅ damit createCategory_emptyName_returns4xx passt
        }
        service.create(name.trim());
        return ResponseEntity.ok().build(); // ✅ Tests erwarten isOk()
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> delete(@PathVariable String name) {
        try {
            boolean deleted = service.delete(name);
            return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build(); // ✅ 404 wenn nicht vorhanden
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(409).build();
        }
    }

    public record CategoryCreateRequest(String name) {}
}

package com.whattodo.demo;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repo;

    public CategoryService(CategoryRepository repo) {
        this.repo = repo;
    }

    public List<String> getAll() {
        return repo.findAllNames();
    }

    public void create(String name) {
        String cleaned = name == null ? "" : name.trim();
        if (cleaned.isEmpty()) return;

        if (repo.existsByNameIgnoreCase(cleaned)) return;

        repo.save(new Category(cleaned));
    }

    /**
     * @return true wenn gelöscht, false wenn nicht gefunden
     */
    public boolean delete(String name) {
        String cleaned = name == null ? "" : name.trim();
        if (cleaned.isEmpty()) return false;

        var found = repo.findByNameIgnoreCase(cleaned);
        if (found.isEmpty()) return false;

        try {
            repo.delete(found.get());
            return true;
        } catch (DataIntegrityViolationException e) {
            // z.B. FK constraint: Kategorie wird noch verwendet
            throw e;
        }
    }
}

package com.whattodo.demo;

import org.springframework.web.bind.annotation.*;
        import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/todos")
@CrossOrigin(origins = "*") // Erlaube alle Domains (für Render-Test)
public class Whattodov1EntryController {

    // In-Memory Liste für Einträge – für Demo/Test!
    private final List<Whattodov1Entry> todos = new ArrayList<>();

    // GET: Alle Todos abrufen
    @GetMapping
    public List<Whattodov1Entry> getAllTodos() {
        return todos;
    }

    // POST: Neues Todo hinzufügen
    @PostMapping
    public Whattodov1Entry addTodo(@RequestBody Whattodov1Entry newTodo) {
        long newId = System.currentTimeMillis();
        newTodo.setId(newId);
        todos.add(newTodo);
        return newTodo;
    }
    @GetMapping("/")
    public String todos2() {
        System.out.println("Hello World"); // Optional, nur für die Konsole
        return "Hello World";               // Das geht an den Browser
    }

    // DELETE: Ein Todo löschen
    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        todos.removeIf(todo -> todo.getId().equals(id));
    }
}


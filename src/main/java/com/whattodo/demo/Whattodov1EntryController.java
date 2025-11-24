package com.whattodo.demo;

import org.springframework.web.bind.annotation.*;
        import java.util.List;
import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*")
public class Whattodov1EntryController {

    private final List<Whattodov1Entry> todos = new ArrayList<>();

    // Root-Endpunkt: gibt alle Todos als JSON zurück
    @GetMapping("/")
    public List<Whattodov1Entry> getAllTodosRoot() {
        return todos;
    }

    // /todos-Endpunkt: gibt alle Todos als JSON zurück
    @GetMapping("/todos")
    public List<Whattodov1Entry> getAllTodos() {
        return todos;
    }

    @PostMapping("/todos")
    public Whattodov1Entry addTodo(@RequestBody Whattodov1Entry newTodo) {
        long newId = System.currentTimeMillis();
        newTodo.setId(newId);
        todos.add(newTodo);
        return newTodo;
    }

    @DeleteMapping("/todos/{id}")
    public void deleteTodo(@PathVariable Long id) {
        todos.removeIf(todo -> todo.getId().equals(id));
    }
}




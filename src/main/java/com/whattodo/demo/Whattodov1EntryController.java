package com.whattodo.demo;

import org.springframework.web.bind.annotation.*;
        import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/todos")
@CrossOrigin(origins = "*")
public class Whattodov1EntryController {

    private final List<Whattodov1Entry> todos = new ArrayList<>();

    // GET /todos
    @GetMapping
    public List<Whattodov1Entry> getAllTodos() {
        return todos;
    }

    // POST /todos
    @PostMapping
    public Whattodov1Entry addTodo(@RequestBody Whattodov1Entry newTodo) {
        long newId = System.currentTimeMillis();
        newTodo.setId(newId);
        todos.add(newTodo);
        return newTodo;
    }

    // DELETE /todos/{id}
    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        todos.removeIf(todo -> todo.getId().equals(id));
    }

    // Optional: GET /
    // Gibt alle Todos zurück (für root)
    @GetMapping("/")
    public List<Whattodov1Entry> getRootTodos() {
        return todos;
    }
}







package com.tinycompute.todobackend.controller;

import com.tinycompute.todobackend.dto.CreateTodoRequest;
import com.tinycompute.todobackend.dto.UpdateTodoRequest;
import com.tinycompute.todobackend.model.Todo;
import com.tinycompute.todobackend.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "*")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    /**
     * GET /api/todos - List all todos
     */
    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos() {
        return ResponseEntity.ok(todoService.getAllTodos());
    }

    /**
     * POST /api/todos - Create a new todo
     */
    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody CreateTodoRequest request) {
        String title = request != null ? request.getTitle() : "";
        Todo created = todoService.createTodo(title);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * PUT /api/todos/{id} - Update an existing todo
     */
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(
            @PathVariable Long id,
            @RequestBody UpdateTodoRequest request
    ) {
        String title = request != null ? request.getTitle() : null;
        Boolean completed = request != null ? request.getCompleted() : null;

        return todoService.updateTodo(id, title, completed)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * DELETE /api/todos/{id} - Delete a todo by ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        boolean deleted = todoService.deleteTodo(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

package com.tinycompute.todobackend.service;

import com.tinycompute.todobackend.model.Todo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class TodoService {

    private final Map<Long, Todo> todoStore = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    /**
     * Returns all todos, ordered by ID ascending.
     */
    public List<Todo> getAllTodos() {
        return todoStore.values()
                .stream()
                .sorted(Comparator.comparing(Todo::getId))
                .collect(Collectors.toList());
    }

    /**
     * Finds a single todo by its ID.
     */
    public Optional<Todo> getTodoById(Long id) {
        return Optional.ofNullable(todoStore.get(id));
    }

    /**
     * Creates a new todo with default completed status set to false.
     */
    public Todo createTodo(String title) {
        Long id = idCounter.getAndIncrement();
        Todo todo = new Todo(id, title != null ? title.trim() : "", false);
        todoStore.put(id, todo);
        return todo;
    }

    /**
     * Updates an existing todo's title and/or completed status.
     * Returns Optional.empty() if the todo is not found.
     */
    public Optional<Todo> updateTodo(Long id, String title, Boolean completed) {
        Todo existing = todoStore.get(id);
        if (existing == null) {
            return Optional.empty();
        }

        if (title != null) {
            existing.setTitle(title.trim());
        }
        if (completed != null) {
            existing.setCompleted(completed);
        }

        return Optional.of(existing);
    }

    /**
     * Deletes a todo by its ID.
     * Returns true if a todo was removed, false otherwise.
     */
    public boolean deleteTodo(Long id) {
        return todoStore.remove(id) != null;
    }
}

'use client';

import { useEffect, useState } from 'react';

interface Todo {
  id: number;
  title: string;
  completed: boolean;
}

export default function TodoPage() {
  const [todos, setTodos] = useState<Todo[]>([]);
  const [newTitle, setNewTitle] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const API_BASE = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080';

  // Fetch all todos on initial component load
  useEffect(() => {
    fetchTodos();
  }, []);

  async function fetchTodos() {
    try {
      setLoading(true);
      setError(null);
      const res = await fetch(`${API_BASE}/api/todos`);
      if (!res.ok) {
        throw new Error(`Failed to load todos (HTTP ${res.status})`);
      }
      const data: Todo[] = await res.json();
      setTodos(data);
    } catch (err: unknown) {
      const message = err instanceof Error ? err.message : 'Error fetching todos';
      setError(message);
    } finally {
      setLoading(false);
    }
  }

  async function handleAddTodo(e: React.FormEvent) {
    e.preventDefault();
    const trimmedTitle = newTitle.trim();
    if (!trimmedTitle) return;

    try {
      setError(null);
      const res = await fetch(`${API_BASE}/api/todos`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ title: trimmedTitle }),
      });

      if (!res.ok) {
        throw new Error(`Failed to create todo (HTTP ${res.status})`);
      }

      const createdTodo: Todo = await res.json();
      setTodos((prev) => [...prev, createdTodo]);
      setNewTitle('');
    } catch (err: unknown) {
      const message = err instanceof Error ? err.message : 'Error creating todo';
      setError(message);
    }
  }

  async function handleToggleTodo(todo: Todo) {
    try {
      setError(null);
      const res = await fetch(`${API_BASE}/api/todos/${todo.id}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          title: todo.title,
          completed: !todo.completed,
        }),
      });

      if (!res.ok) {
        throw new Error(`Failed to update todo (HTTP ${res.status})`);
      }

      const updatedTodo: Todo = await res.json();
      setTodos((prev) =>
        prev.map((item) => (item.id === updatedTodo.id ? updatedTodo : item))
      );
    } catch (err: unknown) {
      const message = err instanceof Error ? err.message : 'Error updating todo';
      setError(message);
    }
  }

  async function handleDeleteTodo(id: number) {
    try {
      setError(null);
      const res = await fetch(`${API_BASE}/api/todos/${id}`, {
        method: 'DELETE',
      });

      if (!res.ok) {
        throw new Error(`Failed to delete todo (HTTP ${res.status})`);
      }

      setTodos((prev) => prev.filter((item) => item.id !== id));
    } catch (err: unknown) {
      const message = err instanceof Error ? err.message : 'Error deleting todo';
      setError(message);
    }
  }

  return (
    <main className="container">
      <h1>Todo App</h1>

      <form onSubmit={handleAddTodo} className="todo-form">
        <input
          type="text"
          className="todo-input"
          placeholder="What needs to be done?"
          value={newTitle}
          onChange={(e) => setNewTitle(e.target.value)}
        />
        <button type="submit" className="add-button">
          Add
        </button>
      </form>

      {error && <div className="status-message status-error">{error}</div>}

      {loading ? (
        <div className="status-message">Loading todos...</div>
      ) : todos.length === 0 ? (
        <div className="empty-state">No todos yet! Add one above.</div>
      ) : (
        <ul className="todo-list">
          {todos.map((todo) => (
            <li key={todo.id} className="todo-item">
              <label className="todo-content">
                <input
                  type="checkbox"
                  className="todo-checkbox"
                  checked={todo.completed}
                  onChange={() => handleToggleTodo(todo)}
                />
                <span
                  className={`todo-title ${todo.completed ? 'completed' : ''}`}
                >
                  {todo.title}
                </span>
              </label>
              <button
                type="button"
                className="delete-button"
                onClick={() => handleDeleteTodo(todo.id)}
                aria-label={`Delete ${todo.title}`}
              >
                Delete
              </button>
            </li>
          ))}
        </ul>
      )}
    </main>
  );
}

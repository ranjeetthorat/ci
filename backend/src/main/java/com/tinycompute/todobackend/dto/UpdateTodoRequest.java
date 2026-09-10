package com.tinycompute.todobackend.dto;

public class UpdateTodoRequest {
    private String title;
    private Boolean completed;

    public UpdateTodoRequest() {
    }

    public UpdateTodoRequest(String title, Boolean completed) {
        this.title = title;
        this.completed = completed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}

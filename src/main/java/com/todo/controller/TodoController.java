package com.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todo.entity.TodoItem;
import com.todo.service.TodoService;

@RestController
//@CrossOrigin(origins = "http://localhost:3000") // Allow requests from this origin
@RequestMapping("/api/todoItems") // Base path for TodoItem operations
public class TodoController {

    @Autowired
    private TodoService todoService;

    // Fetch all Todo Items
    @GetMapping
    public ResponseEntity<List<TodoItem>> fetchAllTodos() {
        List<TodoItem> todoItems = todoService.fetchAllTodos();
        return ResponseEntity.ok(todoItems);
    }

    // Create a new Todo Item
    @PostMapping("/add")
    public ResponseEntity<TodoItem> createNewTodoItem() {
        TodoItem todoItem = todoService.createNewTodoItem();
        return ResponseEntity.status(HttpStatus.CREATED).body(todoItem);
    }

    // Update a Todo Item by ID
    @PutMapping("/{id}")
    public ResponseEntity<TodoItem> updateTodoItem(@PathVariable Integer id, @RequestBody TodoItem todoItem) {
        TodoItem updatedTodoItem = todoService.updateTodoItem(id, todoItem);
        return ResponseEntity.ok(updatedTodoItem);
    }

    // Delete a Todo Item by ID
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteTodoItem(@PathVariable Integer id) {
        todoService.deleteTodoItem(id);
        return ResponseEntity.ok().build();
    }
}

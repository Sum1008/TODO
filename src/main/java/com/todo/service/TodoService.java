package com.todo.service;

import com.todo.entity.TodoItem;
import com.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepository todoRepo;

    @Autowired
    public TodoService(TodoRepository todoRepo) {
        this.todoRepo = todoRepo;
    }

    public List<TodoItem> fetchAllTodos() {
        return todoRepo.findAll();
    }

    public TodoItem updateTodoItem(Integer id, TodoItem todoItem) {
        Optional<TodoItem> optionalTodo = todoRepo.findById(id);

        if (optionalTodo.isPresent()) {
            TodoItem existingTodo = optionalTodo.get();
            existingTodo.setTask(todoItem.getTask());
            existingTodo.setIsDone(todoItem.getIsDone());
            return todoRepo.save(existingTodo);
        }

        return null; // Or throw an exception indicating not found
    }

    public TodoItem createNewTodoItem() {
        TodoItem newTodo = new TodoItem();
        newTodo.setTask("Click to edit task name");
        newTodo.setIsDone(false);
        return todoRepo.save(newTodo);
    }

    public void deleteTodoItem(Integer id) {
        todoRepo.deleteById(id);
    }
}

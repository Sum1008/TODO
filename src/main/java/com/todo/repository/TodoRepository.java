package com.todo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.todo.entity.TodoItem;
@Repository
public interface TodoRepository extends JpaRepository<TodoItem,Integer> {

  //@Query("SELECT t FROM TodoItem t")
  List<TodoItem> findAll();
}

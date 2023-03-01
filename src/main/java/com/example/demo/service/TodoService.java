package com.example.demo.service;

import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService {
    @Autowired
    private TodoRepository repository;
    public String testService() {
        // TodoEntity 생성
        TodoEntity entity = TodoEntity.builder().title("First Todo Item").build();
        // TodoEntity 저장
        repository.save(entity);
        // TodoEntity 검색
        TodoEntity saveEntity = repository.findById(entity.getId()).get();
        return saveEntity.getTitle();
    }
}

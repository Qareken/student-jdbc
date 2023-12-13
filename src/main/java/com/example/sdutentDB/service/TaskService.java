package com.example.sdutentDB.service;

import com.example.sdutentDB.dto.Students;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Students> findAll();
    Optional<Students> findById(Long id);
    Students save(Students students);
    Students update (long studentID,Students students);
    void deleteById(Long id);
}

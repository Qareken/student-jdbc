package com.example.sdutentDB.repository;

import com.example.sdutentDB.dto.Students;

import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    List<Students> findAll();
    Optional<Students> findById(Long id);
    Students save(Students students);
    Students update (Students students);
    void deleteById(Long id);
}

package com.example.sdutentDB.repository;

import com.example.sdutentDB.dto.StudentPhones;

import java.util.List;
import java.util.Optional;

public interface StudentPhoneRepository {
    List<StudentPhones> findAll();
    List<StudentPhones> findByStudentId(Long studentId);
    Optional<StudentPhones> findById(Long id);
    void save(List<StudentPhones> phones , long studentId);
    void update (List<StudentPhones> studentPhones, long studentId);
    void deleteById(Long id);
}

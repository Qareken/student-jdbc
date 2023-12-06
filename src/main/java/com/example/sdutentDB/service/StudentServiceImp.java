package com.example.sdutentDB.service;

import com.example.sdutentDB.dto.Students;
import com.example.sdutentDB.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImp implements TaskService {
    private final StudentRepository studentRepository;


    @Override
    public List<Students> findAll() {

        return studentRepository.findAll();
    }

    @Override
    public Optional<Students> findById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public Students save(Students students) {
        return studentRepository.save(students);
    }

    @Override
    public Students update(Students students) {
        return studentRepository.update(students);
    }

    @Override
    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }
}

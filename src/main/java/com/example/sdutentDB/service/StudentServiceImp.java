package com.example.sdutentDB.service;

import com.example.sdutentDB.dto.Students;
import com.example.sdutentDB.dto.StudentsDTO;
import com.example.sdutentDB.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    public Students update(long studentID,Students students) {
        return studentRepository.update(studentID,students);
    }

    @Override
    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }
    public Students convertStudentDTO(StudentsDTO studentsDTO){
        Students students = new Students();
        students.setFirstName(studentsDTO.getFirstName());
        students.setLastName(studentsDTO.getLastName());
        students.setEmail(studentsDTO.getEmail());
        if(studentsDTO.getStudentPhonesList()!=null){
            students.setStudentPhonesList(studentsDTO.getStudentPhonesList());
        }
        if(studentsDTO.getImage()!=null){
            try {
                byte[] imageBytes = studentsDTO.getImage().getBytes();
                // Set the image bytes to the Students object or handle as needed
                students.setImage(imageBytes);
            } catch (IOException e) {
                // Handle exceptions related to file processing
                e.printStackTrace();
            }
        }


        return students;
    }
}

package com.example.sdutentDB.controller;

import com.example.sdutentDB.dto.Students;
import com.example.sdutentDB.service.StudentServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class StudentController {
    private final StudentServiceImp studentServiceImp;
    @GetMapping("/")
    public String mainPage(Model model){
        var studentList = studentServiceImp.findAll();
        Map<Students, String> map = new HashMap<>();
        studentList.forEach(students -> {
            map.put(students, Base64.getEncoder().encodeToString(students.getImage()));
        });
        model.addAttribute("map", map);

        return "index";
    }
    @PutMapping("/edit/{studentID}")
    public void editStudent(@PathVariable Long studentID, Students students){
        studentServiceImp.update(students);
    }
    @PostMapping("/")
    public void saveStudents(@RequestBody Students students){
        studentServiceImp.save(students);
    }
    @DeleteMapping("/delete/{studentID}")
    public void deleteStudent(@PathVariable Long studentID){
        studentServiceImp.deleteById(studentID);
    }
    @GetMapping("/getStudent/{studentID}")
    public Students getStudent(@PathVariable Long studentID){
        Students students;
        Optional<Students> studentsOptional=studentServiceImp.findById(studentID);
        students = studentsOptional.orElse(null);
        return students;
    }
}

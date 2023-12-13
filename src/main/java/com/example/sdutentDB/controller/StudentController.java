package com.example.sdutentDB.controller;

import com.example.sdutentDB.dto.Students;
import com.example.sdutentDB.dto.StudentsDTO;
import com.example.sdutentDB.service.StudentServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        model.addAttribute("studentsDto", new StudentsDTO());

        return "index";
    }
    @PostMapping("/edit/{studentID}")
    public String editStudent(@PathVariable Long studentID, StudentsDTO studentsDTO){
            log.info("it has been called");
            Students students = studentServiceImp.convertStudentDTO(studentsDTO);

        studentServiceImp.update(studentID,students);
        return "redirect:/";
    }
    @PostMapping("/save")
    public ResponseEntity<String> saveStudents(StudentsDTO studentsDTO){
        Students students = studentServiceImp.convertStudentDTO(studentsDTO);
        studentServiceImp.save(students);
        return ResponseEntity.status(302).header("Location","/").body("Redirecting home page..");
    }
    @GetMapping("/save")
    public String savePage(Model model){
        model.addAttribute("studentsDto", new StudentsDTO());
        return "savePage";
    }
    @DeleteMapping("/delete/{studentID}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long studentID){
        log.info("Delete method has been called");
        studentServiceImp.deleteById(studentID);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Student deleted successfully");
    }
    @GetMapping("/getStudent/{studentID}")
    public Students getStudent(@PathVariable Long studentID){
        Students students;
        Optional<Students> studentsOptional=studentServiceImp.findById(studentID);
        students = studentsOptional.orElse(null);
        return students;
    }
}

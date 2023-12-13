package com.example.sdutentDB.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class StudentsDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private MultipartFile image;
    private List<StudentPhones> studentPhonesList;


}

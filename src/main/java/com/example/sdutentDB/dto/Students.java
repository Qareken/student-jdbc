package com.example.sdutentDB.dto;

import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Data
@FieldNameConstants
public class Students {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private byte[] image;
    private List<StudentPhones> studentPhonesList;

}

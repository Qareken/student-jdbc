package com.example.sdutentDB.dto;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

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

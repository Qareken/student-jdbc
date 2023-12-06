package com.example.sdutentDB.dto;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

@Data
@FieldNameConstants
public class StudentPhones {
    private Long id;
    private Long studentId;
    private String phone;

}

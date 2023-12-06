package com.example.sdutentDB.repository.mapper;

import com.example.sdutentDB.dto.Students;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
@Slf4j
public class TaskRowMapper implements RowMapper<Students> {
    @Override
    public Students mapRow(ResultSet rs, int rowNum) throws SQLException {
        Students students = new Students();
        long studentId =rs.getLong(Students.Fields.id);
        students.setId(studentId);
        students.setFirstName(rs.getString(Students.Fields.firstName));
        students.setLastName(rs.getString(Students.Fields.lastName));
        byte[] imageBytes;
        if(rs.getObject(Students.Fields.image)!=null){
            imageBytes = (byte[]) rs.getObject(Students.Fields.image);
        }else {
            imageBytes = new byte[0];
        }
        students.setImage(imageBytes);
        log.info("length image "+students.getImage().length);
        students.setEmail(rs.getString(Students.Fields.email));
        return students;
    }

}

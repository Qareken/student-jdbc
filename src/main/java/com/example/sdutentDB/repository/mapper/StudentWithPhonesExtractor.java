package com.example.sdutentDB.repository.mapper;

import com.example.sdutentDB.dto.StudentPhones;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentWithPhonesExtractor implements ResultSetExtractor<List<StudentPhones>> {
    @Override
    public List<StudentPhones> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<StudentPhones> list = new ArrayList<>();
        while (rs.next()) {
            StudentPhones phones = new StudentPhones();
            phones.setId(rs.getLong(StudentPhones.Fields.id));
            phones.setPhone(rs.getString(StudentPhones.Fields.phone));
            list.add(phones);
        }
        return list;
    }
}

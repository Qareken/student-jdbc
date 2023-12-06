package com.example.sdutentDB.repository;

import com.example.sdutentDB.dto.StudentPhones;
import com.example.sdutentDB.repository.mapper.StudentWithPhonesExtractor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class DatabasePhoneRepository implements StudentPhoneRepository {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public List<StudentPhones> findAll() {
        return null;
    }

    @Override
    public List<StudentPhones> findByStudentId(Long studentId) {
        String sql = "SELECT * from phones where student_id = ?";
        return jdbcTemplate.query(sql, new Object[]{studentId}, new StudentWithPhonesExtractor());
    }

    @Override
    public Optional<StudentPhones> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(List<StudentPhones> studentPhones, long studentId) {
        studentPhones.forEach(phone->{
            phone.setStudentId(studentId);
        });
        String sql = "insert into phones (student_id, phone) values(?,?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                StudentPhones phones = studentPhones.get(i);
                ps.setLong(1, phones.getStudentId());
                ps.setString(2, phones.getPhone());
            }

            @Override
            public int getBatchSize() {
                return studentPhones.size();
            }
        });
    }

    @Override
    public void update(List<StudentPhones> studentPhones, long studentId) {
        List<StudentPhones> newPhoneList = new ArrayList<>();
        String sql = "update phones set phone=? where id=?";
        jdbcTemplate.update(sql, new BatchPreparedStatementSetter() {


            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                StudentPhones phones = studentPhones.get(i);
                if(phones.getId()!=null){
                    ps.setString(1, phones.getPhone());
                    ps.setLong(2, phones.getId());
                }else {
                    newPhoneList.add(phones);
                }
            }

            @Override
            public int getBatchSize() {
                return studentPhones.size();
            }
        });
        save(newPhoneList, studentId);
    }

    @Override
    public void deleteById(Long studentID) {
        String query = "delete from phones where student_id= ? ";
        jdbcTemplate.update(query,studentID);
    }
}

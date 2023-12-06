package com.example.sdutentDB.repository;

import com.example.sdutentDB.dto.Students;
import com.example.sdutentDB.exception.StudentNotFoundException;
import com.example.sdutentDB.repository.mapper.TaskRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class DatabaseStudentRepository implements StudentRepository {
    private final JdbcTemplate jdbcTemplate;
    private final DatabasePhoneRepository phoneRepository;

    @Override
    public List<Students> findAll() {
        log.debug("Calling databaseTaskRepository ->findAll");
        String sql = "SELECT * FROM students";

        var listStudents= jdbcTemplate.query(sql, new TaskRowMapper());
        listStudents.forEach(x->{
            System.out.println(x.getFirstName());
            if(x!=null){
                x.setStudentPhonesList(phoneRepository.findByStudentId(x.getId()));
            }
        });

        return listStudents;
    }

    @Override
    public Optional<Students> findById(Long id) {
        log.debug("Calling DatabaseTaskRepository -> findById with ID:{}", id);
        String sql = "SELECT * from students  where id=?";
        var students = DataAccessUtils.singleResult(jdbcTemplate.query(sql, new ArgumentPreparedStatementSetter(new Object[]{id}),
                new RowMapperResultSetExtractor<>(new TaskRowMapper(), 1)));
        if (students != null) {
            students.setStudentPhonesList(phoneRepository.findByStudentId(students.getId()));
        }
        return Optional.ofNullable(students);
    }

    @Override
    public Students save(Students students) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(Objects.requireNonNull(jdbcTemplate.getDataSource()));
        String sql = "insert into students (firstName, lastName, email, image) values(:firstName,:lastName, :email, :image) returning id";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("firstName", students.getFirstName())
                .addValue("lastName", students.getLastName())
                .addValue("email", students.getEmail())
                .addValue("image", students.getImage(), Types.LONGVARBINARY);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, parameterSource, keyHolder);
        long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        phoneRepository.save(students.getStudentPhonesList(), id);
        return students;
    }

    @Override
    public Students update(Students students) {
        Students existedStudent = findById(students.getId()).orElse(null);
        if(existedStudent!=null){
            String sql = "update task set firstName=?, lastName=?, email=?, image=? where id = ?";
            jdbcTemplate.update(sql,students.getFirstName(), students.getLastName(), students.getEmail(), students.getImage(), students.getId());
            phoneRepository.update(students.getStudentPhonesList(), students.getId());
            return students;
        }
        throw new StudentNotFoundException("Student for update not found! ID: "+students.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "delete from students where id =?";
        phoneRepository.deleteById(id);
        jdbcTemplate.update(sql, id);
    }
}

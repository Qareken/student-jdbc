package com.example.sdutentDB.repository;

import com.example.sdutentDB.dto.StudentPhones;
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

import java.lang.reflect.Field;
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
        if(students.getStudentPhonesList()!=null){
            phoneRepository.save(students.getStudentPhonesList(), id);
        }

        return students;
    }

    @Override
    public Students update(long studentID,Students students) {
        Students existedStudent = findById(studentID).orElse(null);
        if(existedStudent!=null){
            String sql = "update students set firstName=?, lastName=?, email=?, image=? where id = ?";
            try {
                students =checkForUpdate(students, existedStudent);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            jdbcTemplate.update(sql,students.getFirstName(), students.getLastName(), students.getEmail(), students.getImage(), studentID);

            return students;
        }
        throw new StudentNotFoundException("Student for update not found! ID: "+studentID);
    }

    @Override
    public void deleteById(Long id) {
        String sql = "delete from students where id =?";
        phoneRepository.deleteById(id);
        jdbcTemplate.update(sql, id);
    }
    private Students checkForUpdate(Students students, Students existedStudents) throws IllegalAccessException {
        var fields = students.getClass().getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);
            var value = field.get(students);
            if(value==null||value.equals(0)){
                var source = field.get(existedStudents);
                field.set(students, source);
            }
        }
        return students;
    }
}

package com.example.sdutentDB.listener;

import com.example.sdutentDB.dto.StudentPhones;
import com.example.sdutentDB.dto.Students;
import com.example.sdutentDB.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.awt.desktop.AppReopenedEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseTaskCreator {
    private final TaskService taskService;
//    @EventListener(ApplicationStartedEvent.class)
    public void  createTaskData(){
        log.debug("Calling creator");
        Students students =new Students();
        List<StudentPhones> phones = new ArrayList<>();
        for(int i =0; i<3;i++){
            StudentPhones studentPhones = new StudentPhones();
            studentPhones.setPhone("+99890940515"+i);
            phones.add(studentPhones);
        }
        students.setFirstName("Ivan");
        students.setLastName("Ivanov");
        students.setEmail("ivan@gmail.com");
        students.setStudentPhonesList(phones);
        Path path = Paths.get("src/main/resources/static/blood.jpg");
        try {
            byte[] imageBytes = Files.readAllBytes(path);
            students.setImage(imageBytes);
            log.info("not null "+ students.getImage().length);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        taskService.save(students);
    }
}

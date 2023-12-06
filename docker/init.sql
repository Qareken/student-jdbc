CREATE SCHEMA IF NOT EXISTS student_schema;

CREATE TABLE IF NOT EXISTS student_schema.students (
    id BIGSERIAL PRIMARY KEY , -- Используйте соответствующий синтаксис для вашей СУБД для автоинкрементного поля
    firstName VARCHAR(50),
    lastName VARCHAR(50),
    email VARCHAR(100),
    image BYTEA -- Поле для хранения изображений в виде BLOB
    );

CREATE TABLE IF NOT EXISTS student_schema.phones (
     id BIGSERIAL PRIMARY KEY ,
     student_id BIGSERIAL,
     phone VARCHAR(20),
    FOREIGN KEY (student_id) REFERENCES student_schema.students(id)
    );



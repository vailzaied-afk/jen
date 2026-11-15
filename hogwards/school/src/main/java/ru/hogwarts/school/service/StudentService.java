package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        logger.info("Was invoked method for create student");

        return studentRepository.save(student);
    }

    public Student getStudentById(Long studentId) {
        logger.info("Was invoked method for get student by id = {}", studentId);
        return studentRepository.findById(studentId).orElseGet(() -> {
            logger.warn("Student with id = {} not found", studentId);
            return null;
        });

    }

    public Student updateStudent(Long studentId, Student student) {
        logger.info("Was invoked method for update student with id = {}", studentId);
        if (!studentRepository.existsById(studentId)) {
            logger.error("There is not student with id = " + studentId);
            return null;
        }
        student.setId(studentId);
        return studentRepository.save(student);
    }


    public void deleteStudent(Long studentId) {
        logger.info("Was invoked method for delete student with id = {}", studentId);
        studentRepository.deleteById(studentId);
    }

    public Collection<Student> getAllStudents() {
        logger.info("Was invoked method for get all students");

        return studentRepository.findAll();
    }

    public List<Student> filterByAge(int age) {
        logger.debug("Filtering students by age = {}", age);

        return studentRepository.findByAge(age);
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        logger.debug("Filtering students by age between {} and {}", min, max);
        return studentRepository.findByAgeBetween(min, max);
    }

}

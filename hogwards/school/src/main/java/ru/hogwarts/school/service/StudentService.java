package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student){
        return studentRepository.save(student);
    }
    public Student getStudentById(Long studentId) {
        return studentRepository.findById(studentId).orElse(null);
    }

    public Student updateStudent(Long studentId, Student student) {
        if (!studentRepository.existsById(studentId)) {
            return null;
        }
        student.setId(studentId);
        return studentRepository.save(student);
    }


    public void deleteStudent(Long studentId) {
       studentRepository.deleteById(studentId);
    }
    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    public List<Student> filterByAge(int age) {
        return studentRepository.findByAge(age);
    }

}

package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
        assertThat(studentController).isNotNull();
    }

    @Test
    public void testCreateStudent() {
        Student student = new Student();
        student.setName("Олина Полина");
        student.setAge(15);

        ResponseEntity<Student> response = this.restTemplate.postForEntity("/students", student, Student.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Олина Полина");
    }

    @Test
    public void testGetStudentInfo() {
        Student student = new Student();
        student.setName("Гермиона Грейнджер");
        student.setAge(12);
        Student createdStudent = this.restTemplate.postForObject("/students", student, Student.class);

        assertThat(createdStudent).isNotNull();

        ResponseEntity<Student> response = this.restTemplate.getForEntity("/students/" + createdStudent.getId(), Student.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Гермиона Грейнджер");
    }

    @Test
    public void testEditStudent() {
        Student student = new Student();
        student.setName("Рон Уизли");
        student.setAge(12);
        Student createdStudent = this.restTemplate.postForObject("/students", student, Student.class);

        assertThat(createdStudent).isNotNull();
        createdStudent.setAge(13);

        HttpEntity<Student> requestEntity = new HttpEntity<>(createdStudent);
        ResponseEntity<Student> response = this.restTemplate.exchange(
                "/students/" + createdStudent.getId(),
                HttpMethod.PUT,
                requestEntity,
                Student.class
        );
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getAge()).isEqualTo(13);
    }

    @Test
    public void testDeleteStudent() {
        Student student = new Student();
        student.setName("Драко Малфой");
        student.setAge(12);
        Student createdStudent = this.restTemplate.postForObject("/students", student, Student.class);

        assertThat(createdStudent).isNotNull();

        ResponseEntity<Void> response = this.restTemplate.exchange("/students/" + createdStudent.getId(), HttpMethod.DELETE, null, Void.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

        ResponseEntity<Student> getResponse = this.restTemplate.getForEntity("/students/" + createdStudent.getId(), Student.class);

        if (getResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
            assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        } else {
            assertThat(getResponse.getBody()).isNull();
        }
    }

    @Test
    public void testFindStudentsByAge() {
        ResponseEntity<String> response = this.restTemplate.getForEntity("/students?age=12", String.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
    }
}

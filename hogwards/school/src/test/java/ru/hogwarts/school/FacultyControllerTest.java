package ru.hogwarts.school;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;

    @BeforeEach
    public void initPath() {
        ResponseEntity<String> checkSingle = this.restTemplate.getForEntity("/faculties", String.class);
        if (checkSingle.getStatusCode() != HttpStatus.NOT_FOUND) {
            baseUrl = "/faculties";
        } else {
            baseUrl = "/faculty";
        }
    }

    @Test
    public void testCreateFaculty() {
        Faculty faculty = new Faculty();
        faculty.setName("Гриффиндор");
        faculty.setColor("Красный");

        ResponseEntity<Faculty> response = this.restTemplate.postForEntity(baseUrl, faculty, Faculty.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Гриффиндор");
    }

    @Test
    public void testGetFacultyInfo() {
        Faculty faculty = new Faculty();
        faculty.setName("Слизерин");
        faculty.setColor("Зеленый");
        Faculty createdFaculty = this.restTemplate.postForObject(baseUrl, faculty, Faculty.class);
        assertThat(createdFaculty).isNotNull();

        ResponseEntity<Faculty> response = this.restTemplate.getForEntity(baseUrl + "/" + createdFaculty.getId(), Faculty.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Слизерин");
    }

    @Test
    public void testEditFaculty() {
        Faculty faculty = new Faculty();
        faculty.setName("Когтевран");
        faculty.setColor("Синий");
        Faculty createdFaculty = this.restTemplate.postForObject(baseUrl, faculty, Faculty.class);
        assertThat(createdFaculty).isNotNull();

        createdFaculty.setColor("Серый");

        HttpEntity<Faculty> requestEntity = new HttpEntity<>(createdFaculty);
        ResponseEntity<Faculty> response = this.restTemplate.exchange(
                baseUrl + "/" + createdFaculty.getId(),
                HttpMethod.PUT,
                requestEntity,
                Faculty.class
        );
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getColor()).isEqualTo("Серый");
    }

    @Test
    public void testDeleteFaculty() {
        Faculty faculty = new Faculty();
        faculty.setName("Пуффендуй");
        faculty.setColor("Желтый");
        Faculty createdFaculty = this.restTemplate.postForObject(baseUrl, faculty, Faculty.class);
        assertThat(createdFaculty).isNotNull();

        ResponseEntity<Void> response = this.restTemplate.exchange(baseUrl + "/" + createdFaculty.getId(), HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

        ResponseEntity<Faculty> getResponse = this.restTemplate.getForEntity(baseUrl + "/" + createdFaculty.getId(), Faculty.class);
        assertThat(getResponse.getStatusCode()).isIn(HttpStatus.NOT_FOUND, HttpStatus.NO_CONTENT);
    }

    @Test
    public void testFindFacultiesByColor() {
        ResponseEntity<String> response = this.restTemplate.getForEntity(baseUrl + "?color=Зеленый", String.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }
}

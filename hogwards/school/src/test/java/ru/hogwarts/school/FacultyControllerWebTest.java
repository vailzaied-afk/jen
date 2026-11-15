
package ru.hogwarts.school;

        import org.json.JSONObject;
        import org.junit.jupiter.api.Test;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
        import org.springframework.boot.test.mock.mockito.MockBean;
        import org.springframework.boot.test.mock.mockito.SpyBean;
        import org.springframework.http.MediaType;
        import org.springframework.test.web.servlet.MockMvc;
        import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
        import ru.hogwarts.school.controller.FacultyController;
        import ru.hogwarts.school.model.Faculty;
        import ru.hogwarts.school.model.Student;
        import ru.hogwarts.school.repository.FacultyRepository;
        import ru.hogwarts.school.repository.StudentRepository;
        import ru.hogwarts.school.service.FacultyService;
        import ru.hogwarts.school.service.StudentService;

        import java.util.Optional;

        import static org.mockito.ArgumentMatchers.any;
        import static org.mockito.Mockito.doNothing;
        import static org.mockito.Mockito.when;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FacultyController.class)
public class FacultyControllerWebTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private FacultyService facultyService;


    @Test
    public void postFacultyTest() throws Exception {
        final long id = 1L;
        final String name = "Пуффендуй";

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculties")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name));
    }
    @Test
    public void getFacultyTest() throws Exception {
        final long id = 1L;
        final String name = "Пуффендуй";

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);


        when(facultyRepository.findById(id)).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculties/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name));
    }
    @Test
    public void putFacultyTest() throws Exception {
        final long id = 1L;
        final String name = "Пуффендуй";

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", id);
        facultyObject.put("name", name);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculties")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name));
    }
    @Test
    public void deleteFacultyTest() throws Exception {
        final long id = 1L;

        doNothing().when(facultyRepository).deleteById(id);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculties/" + id))
                .andExpect(status().isOk());
    }
    @Test
    public void findFacultiesByColorTest() throws Exception {
        final long id = 1L;
        final String name = "Пуффендуй";
        final String color = "Желтый";

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);

         when(facultyRepository.findByColorIgnoreCase(color)).thenReturn(java.util.List.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculties")
                        .param("color", color) // Передаем цвет как Query-параметр (?color=Желтый)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(name));
    }





}

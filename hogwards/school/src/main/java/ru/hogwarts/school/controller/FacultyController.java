package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;

@RequestMapping("faculties")
@RestController
public class FacultyController {
    public final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculties) {
        Faculty createdFaculty = facultyService.createFaculty(faculties);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFaculty);

    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty faculties = facultyService.getFacultyById(id);
        if (faculties == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculties);

    }
    @PutMapping("{id}")
    public ResponseEntity<Faculty> updateFaculty(@PathVariable Long id, @RequestBody Faculty faculties){
        Faculty updateFaculty = facultyService.updateFaculty(faculties.getId(), faculties);
        if (updateFaculty == null){
            return ResponseEntity.notFound().build();

        }
        return ResponseEntity.ok(updateFaculty);
    }
    @DeleteMapping("{id}")
    public  ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id){
        if (facultyService.getFacultyById(id) == null){
            return ResponseEntity.notFound().build();
        }
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> getFaculties(@RequestParam(required = false) String color) {
         if (color != null && !color.isBlank()) {
            return ResponseEntity.ok(facultyService.filterByColor(color));
        }
         return ResponseEntity.ok(facultyService.getAllFaculties());
    }


}


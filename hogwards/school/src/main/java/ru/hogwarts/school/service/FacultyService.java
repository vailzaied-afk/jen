package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.List;
@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    @Autowired
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);

    }

    public Faculty getFacultyById(Long facutyId) {
        return facultyRepository.findById(facutyId).orElse(null);
    }

    public Faculty updateFaculty(Long facutyId, Faculty faculty) {

        if (!facultyRepository.existsById(facutyId)) {
            return null;
        }
        faculty.setId(facutyId);
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long facultyId) {
        facultyRepository.deleteById(facultyId);
    }
    public List<Faculty> filterByColor(String color) {
        return facultyRepository.findByColorIgnoreCase(color);
    }

    public Collection<Faculty> getAllFaculties(){
        return facultyRepository.findAll();
    }

}

package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private static final Logger logger = LoggerFactory.getLogger(FacultyService.class);


    @Autowired
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was invoked method for create faculty");


        return facultyRepository.save(faculty);

    }

    public Faculty getFacultyById(Long facutyId) {
        logger.info("Was invoked method for get faculty by id = {}", facutyId);
        return facultyRepository.findById(facutyId).orElseGet(() -> {
            logger.warn("Faculty with id = {} not found", facutyId);
            return null;
        });
    }


    public Faculty updateFaculty(Long facutyId, Faculty faculty) {
        logger.info("Was invoked method for update faculty with id = {}", facutyId);

        if (!facultyRepository.existsById(facutyId)) {
            logger.error("There is not faculty with id = " + facutyId);

            return null;
        }
        faculty.setId(facutyId);
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long facultyId) {
        logger.info("Was invoked method for delete faculty with id = {}", facultyId);

        facultyRepository.deleteById(facultyId);
    }

    public List<Faculty> filterByColor(String color) {
        logger.debug("Filtering faculty by color = {}", color);

        return facultyRepository.findByColorIgnoreCase(color);
    }

    public List<Faculty> filterByName(String name) {
        logger.debug("Filtering faculty by name = {}", name);

        return facultyRepository.findByNameIgnoreCase(name);
    }

    public Collection<Faculty> getAllFaculties() {
        logger.info("Was invoked method for get all faculties");

        return facultyRepository.findAll();
    }

    public String getNameLongFaculty() {
        logger.info("Was invoked method for get longest faculty name");
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .orElse("");
    }

}

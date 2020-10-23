package ru.specialist.DAO;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> findAll();
    Optional<Course> findById(int id);
    Course save(Course course);
    void delete(int id);
}

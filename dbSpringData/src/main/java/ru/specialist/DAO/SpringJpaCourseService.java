package ru.specialist.DAO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("springJpaCourseService")
@Repository
@Transactional
public class SpringJpaCourseService implements  CourseService {

    private static final Log LOG  = LogFactory.getLog(SpringJpaCourseService.class);

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public CourseRepository getCourseRepository() {
        return courseRepository;
    }

    @Override
    public List<Course> findAll() {
        return new ArrayList<Course>(courseRepository.findAll());
    }
}

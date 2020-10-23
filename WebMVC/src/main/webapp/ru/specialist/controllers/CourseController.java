package ru.specialist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.specialist.DAO.Course;
import ru.specialist.DAO.CourseService;

import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private CourseService courseService;

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model uiModel){
        List<Course> courses = courseService.findAll();
        uiModel.addAttribute("courses", courses);
        return "courses/list";
    }
}

package ru.specialist.DAO;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class App {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        CourseDAO courseDAO = context.getBean(CourseDAO.class);

        System.out.println(courseDAO.findById(5));

        List<Course> courses = courseDAO.findAll();

        for (Course course: courses) {
            System.out.println(course);
        }
        context.close();
    }
}

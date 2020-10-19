package ru.specialist.dbJPA;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.specialist.DAO.Course;
import ru.specialist.DAO.CourseService;

public class App {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        CourseService courseDAO = context.getBean("springJpaCourseService", CourseService.class);

        for (Course course: courseDAO.findAll()) {
            System.out.println(course);
        }

        context.close();
    }
}

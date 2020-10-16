package ru.specialist.dbHibernate;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class App {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        CourseDAO courseDAO = context.getBean(CourseDAO.class);

        System.out.println(courseDAO.findById(5));



        for (Course course: courseDAO.findByTitle("web")) {
            System.out.println(course);
        }


//        Course spring = new Course();
//        spring.setTitle("Spring");
//        spring.setDescription("Spring framework and Spring MVC");
//        courseDAO.insert(spring);

        // courseDAO.delete(8);

        for (Course course: courseDAO.findAll()) {
            System.out.println(course);
        }

        context.close();
    }
}

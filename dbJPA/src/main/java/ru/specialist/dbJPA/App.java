package ru.specialist.dbJPA;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.specialist.DAO.Course;
import ru.specialist.DAO.CourseDAO;

public class App {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        CourseDAO courseDAO = context.getBean("jpaCourseService", CourseDAO.class);





        for (Course course: courseDAO.findByTitle("web")) {
            System.out.println(course);
        }

        System.out.println(courseDAO.findById(5));

//        Course spring = new Course();
//        spring.setTitle("Spring");
//        spring.setLength(40);
//        spring.setDescription("Spring framework and Spring MVC");
//        courseDAO.insert(spring);


//        courseDAO.delete(9);

        for (Course course: courseDAO.findAll()) {
            System.out.println(course);
        }

        context.close();
    }
}

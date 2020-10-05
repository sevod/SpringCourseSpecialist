package ru.specialist.springhello;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class  App {
    public static void main (String[] args)
    {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppContext.class);
        //ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        PersonBean person = context.getBean(PersonBean.class);

        System.out.printf("It's I: %s - %d\n", person.getName(), person.getAge());

        context.close();
    }
}

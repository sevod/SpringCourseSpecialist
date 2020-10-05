package ru.specialist.springhello;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main (String[] args)
    {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        PersonBean person = context.getBean("iam", PersonBean.class);

        System.out.printf("It's I: %s - %d\n", person.getName(), person.getAge());

        context.close();
    }
}

package ru.specialist.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Window window = context.getBean("windowBean", Window.class);

        new House(window).view();
        context.close();
    }
}

package ru.specialist.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        //Window window = context.getBean("windowBean", Window.class);
        //new House(window).view();
        House house = context.getBean("houseBean", House.class);

        house.buildWall();
        house.view();

        System.out.printf("House height: %d\n",house.getHeight());

        context.close();
    }
}

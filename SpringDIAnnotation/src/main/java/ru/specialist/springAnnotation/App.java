package ru.specialist.springAnnotation;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class App {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        //Window window = context.getBean("windowBean", Window.class);
        //new House(window).view();
        House house = context.getBean(House.class);

        house.buildWall();
//        house.installDoors();
        house.view();
        System.out.println(house.getHeight());

 //       System.out.printf("House height: %d\n",house.getHeight());

//        MainWindow mainWindow = context.getBean(MainWindow.class);
//        mainWindow.show();

        context.close();
    }
}

package ru.specialist.springJava;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.specialist.builder.BuilderConfig;
import ru.specialist.builder.House;

public class App {
    public static void main(String[] args) {

       AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BuilderConfig.class);

        House house = context.getBean(House.class);

        house.buildWall();

        house.view();

 //       context.close();
    }
}

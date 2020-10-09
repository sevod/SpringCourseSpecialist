package ru.specialist.builder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class BuilderConfig {

    @Bean("brick")
    @Scope("prototype")
    public Brick bricks(){
        return new Brick();
    }

    @Bean("wood")
    @Scope("prototype")
    public Wood wood(){
        return new Wood();
    }

    @Bean
    public Window woodFrameWindow(){
        return new WoodFrameWindow();
    }

    @Bean
    public House house(){
        House house = new House(woodFrameWindow());
        house.setHeight(2);
        return house;
    }
}

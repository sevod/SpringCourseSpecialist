package ru.specialist.springAnnotation;

import org.springframework.stereotype.Component;


public class Brick implements Material {
    public void cover() {
        System.out.println("Класть кирпич");
    }
}

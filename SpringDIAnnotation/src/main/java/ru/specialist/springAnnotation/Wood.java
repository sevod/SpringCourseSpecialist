package ru.specialist.springAnnotation;

import org.springframework.stereotype.Component;

@Component("wood")
public class Wood implements Material {
    public void cover() {
        System.out.println("Класть бревна");
    }
}

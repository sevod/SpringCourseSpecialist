package ru.specialist.springAnnotation;

import org.springframework.stereotype.Component;

public class WoodFrameWindow implements Window{
    public void open() {
        System.out.println("WoodFrame window opend");
    }
}

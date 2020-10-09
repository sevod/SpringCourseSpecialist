package ru.specialist.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;


public class House {

    private Window window;


    private int height;
    @Autowired
    @Qualifier("brick")
    private Material wall;
    private Map<String, Door> doors;

    public House() {
    }


    public House(Window window) {
        this.window = window;
        //this.height = height;
    }

    public Map<String, Door> getDoors() {
        return doors;
    }

    public void setDoors(Map<String, Door> doors) {
        this.doors = doors;
    }

    public void installDoors(){
        for (Map.Entry<String, Door> e: doors.entrySet()) {
            System.out.printf("Ключ %s. ", e.getKey());
            e.getValue().install();
        }
    }

    public Material getWall() {
        return wall;
    }

    public void setWall(Material wall) {
        this.wall = wall;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void view() {
        window.open();
    }

    public void buildWall() {
        for (int i = 1; i <= getHeight(); i++) {
            System.out.printf("Этаж %d. ", i);
            getWall().cover();
        }
    }

    public Window getWindow() {
        return window;
    }

    public void setWindow(Window window) {
        this.window = window;
    }
}

package ru.specialist.spring;

import java.util.Collection;

public class House {
    private Window window;
    private int height;
    private Material wall;
    private Collection<Door> doors;

    public House(Window window) {
        this.window = window;
        //this.height = height;
    }

    public void installDoors(){
        for (Door door : doors)
            door.install();
    }

    public Collection<Door> getDoors() {
        return doors;
    }

    public void setDoors(Collection<Door> doors) {
        this.doors = doors;
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

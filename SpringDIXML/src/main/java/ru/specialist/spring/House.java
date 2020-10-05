package ru.specialist.spring;

public class House {
    private Window window;
    private int height;
    private Material wall;

    public Material getWall() {
        return wall;
    }

    public void setWall(Material wall) {
        this.wall = wall;
    }

    public House(Window window){
        this.window = window;
        //this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void view(){
        window.open();
    }

    public void buildWall(){
        for (int i=1; i <= getHeight(); i++){
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

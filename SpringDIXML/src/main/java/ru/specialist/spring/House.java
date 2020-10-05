package ru.specialist.spring;

public class House {
    private Window window;
    private int height;

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

    public Window getWindow() {
        return window;
    }

    public void setWindow(Window window) {
        this.window = window;
    }
}

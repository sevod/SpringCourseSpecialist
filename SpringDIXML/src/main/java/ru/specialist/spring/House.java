package ru.specialist.spring;

public class House {
    private Window window;
    public House(Window window){
        this.window = window;
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

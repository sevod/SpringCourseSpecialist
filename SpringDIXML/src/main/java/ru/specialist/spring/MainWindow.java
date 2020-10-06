package ru.specialist.spring;

public class MainWindow {
    private MainWindow(){}

    private static class MainWindowHolder{
        static MainWindow instance = new MainWindow();
    }

    public static MainWindow getInstance(){
        return MainWindowHolder.instance;
    }

    public void show(){
        System.out.println("Show main window");
    }

    public void openConnection(){
        System.out.println("Main window open connection");
    }
}

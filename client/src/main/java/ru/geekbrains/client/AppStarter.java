package ru.geekbrains.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.nio.file.Path;


public class AppStarter extends Application {

    private static Stage primaryStage;
    private static String previousWindow;
    private static Path currentFolder;


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/ru/geekbrains/client/welcomeWindow.fxml"));
        this.primaryStage = primaryStage;
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
    }


    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static String getPreviousWindow() {
        return previousWindow;
    }

    public static void setPreviousWindow(String window) {
        previousWindow = window;
    }

    public static Path getCurrentFolder() {
        return currentFolder;
    }

    public static void setCurrentFolder(Path folder) {
        currentFolder = folder;
    }

}



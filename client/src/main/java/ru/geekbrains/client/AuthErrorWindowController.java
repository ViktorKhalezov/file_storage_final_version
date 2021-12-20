package ru.geekbrains.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AuthErrorWindowController implements Initializable {

    private static final ClientNet clientNet = ClientNet.getClientNet();
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    Label label;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        label.setText(null);
        String authCommand = clientNet.getAuthCommand();
        if(authCommand.equals("userAlreadyLogged")) {
            label.setText("Пользователь с таким именем уже вошел в программу.");
        }
        if(authCommand.equals("incorrectCredentials")){
            label.setText("Вы ввели неверный логин или пароль.");
        }
        clientNet.setAuthCommand(null);
    }


    public void okButton(ActionEvent actionEvent) {
        try {
            root = FXMLLoader.load(getClass().getResource("/ru/geekbrains/client/welcomeWindow.fxml"));
            stage = AppStarter.getPrimaryStage();
            scene = new Scene(root);
            stage.setScene(scene);
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

}

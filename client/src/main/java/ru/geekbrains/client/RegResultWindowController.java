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

public class RegResultWindowController implements Initializable {
    private static final ClientNet clientNet = ClientNet.getClientNet();
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    Label label;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        label.setText(null);
        String command = clientNet.getRegCommand();
        if(command.equals("incorrectInput")) {
            label.setText("Вы ввели некорректные данные. Повторите попытку.");
        }
        if(command.equals("incorrectSigns")) {
            label.setText("Логин не должен содержать знаков: \\ / : * ? \" < > |");
        }
        if(command.equals("incorrectPasswords")) {
            label.setText("Пароли в обоих полях должны совпадать. Пожалуйста, повторите ввод.");
        }
        if(command.equals("userExists")) {
            label.setText("Пользователь с таким логином уже существует. Используйте другой логин.");
        }
        if(command.equals("regConfirmed")) {
            label.setText("Регистрация прошла успешно.");
        }
    }

    public void okButton(ActionEvent actionEvent) {
        String command = clientNet.getRegCommand();
        if(command.equals("regConfirmed")) {
            clientNet.setRegCommand(null);
            try {
                root = FXMLLoader.load(getClass().getResource("/ru/geekbrains/client/welcomeWindow.fxml"));
                stage = AppStarter.getPrimaryStage();
                scene = new Scene(root);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            clientNet.setRegCommand(null);
            try {
                root = FXMLLoader.load(getClass().getResource("/ru/geekbrains/client/registrationWindow.fxml"));
                stage = AppStarter.getPrimaryStage();
                scene = new Scene(root);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

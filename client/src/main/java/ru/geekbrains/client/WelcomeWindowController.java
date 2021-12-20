package ru.geekbrains.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.geekbrains.common.AuthMessage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class WelcomeWindowController implements Initializable {

    private static final ClientNet clientNet = ClientNet.getClientNet();
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public TextField login;

    @FXML
    public PasswordField password;


    public void enterButton(ActionEvent actionEvent) {
        if(login.getText() == null || password.getText() == null
                || login.getText().equals("") || password.getText().equals("")
        || login.getText().equals(" ") || password.equals(" ")) {
            clientNet.setAuthCommand("incorrectCredentials");
            try {
                root = FXMLLoader.load(getClass().getResource("/ru/geekbrains/client/authErrorWindow.fxml"));
                stage = AppStarter.getPrimaryStage();
                scene = new Scene(root);
                stage.setScene(scene);
            }catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            clientNet.sendMessage(new AuthMessage(login.getText(), password.getText()));
            new Thread(() -> {
                while (true) {
                    if (clientNet.getAuthCommand() != null) {
                        break;
                    }
                }
            }).start();
            try {
                Thread.currentThread().sleep(400);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
            if (clientNet.getAuthCommand().equals("authConfirmed")) {
                enterMainWindow();
            } else {
                try {
                    root = FXMLLoader.load(getClass().getResource("/ru/geekbrains/client/authErrorWindow.fxml"));
                    stage = AppStarter.getPrimaryStage();
                    scene = new Scene(root);
                    stage.setScene(scene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            clientNet.setAuthCommand(null);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AppStarter.setPreviousWindow("welcomeWindow");
    }


    public void enterMainWindow() {
        try {
            root = FXMLLoader.load(getClass().getResource("/ru/geekbrains/client/mainWindow.fxml"));
            stage = AppStarter.getPrimaryStage();
            scene = new Scene(root);
            stage.setScene(scene);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registrationButton(ActionEvent actionEvent) {
        try {
            root = FXMLLoader.load(getClass().getResource("/ru/geekbrains/client/registrationWindow.fxml"));
            stage = AppStarter.getPrimaryStage();
            scene = new Scene(root);
            stage.setScene(scene);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}

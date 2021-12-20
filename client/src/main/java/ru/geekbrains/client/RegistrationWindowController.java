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
import ru.geekbrains.common.RegistrationMessage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationWindowController implements Initializable {
    private static final ClientNet clientNet = ClientNet.getClientNet();
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public TextField login;

    @FXML
    public PasswordField password1;

    @FXML
    public PasswordField password2;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void registrationButton(ActionEvent actionEvent) {
        if(login.getText() == null || password1.getText() == null || password2.getText() == null
        || login.getText().equals("") || password1.getText().equals("") || password2.getText().equals("")
                || login.getText().equals(" ") || password1.getText().equals(" ") || password2.equals(" ")) {
            clientNet.setRegCommand("incorrectInput");
            enterRegResultWindow();
            return;
        }
        if(!password1.getText().equals(password2.getText())) {
            clientNet.setRegCommand("incorrectPasswords");
            enterRegResultWindow();
            return;
        }
        if(login.getText().contains("\\") || login.getText().contains("/") || login.getText().contains(":") || login.getText().contains("*")
                || login.getText().contains("?") || login.getText().contains("\"") || login.getText().contains("<")
                || login.getText().contains(">") || login.getText().contains("|")) {
            clientNet.setRegCommand("incorrectSigns");
           enterRegResultWindow();
            return;
        }
        clientNet.sendMessage(new RegistrationMessage(login.getText(), password1.getText()));
        new Thread(() -> {
            while (true) {
                if(clientNet.getRegCommand() != null) {
                    break;
                }
            }
        }).start();
        try {
            Thread.currentThread().sleep(400);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
        if(clientNet.getRegCommand().equals("regConfirmed")) {
            try {
                root = FXMLLoader.load(getClass().getResource("/ru/geekbrains/client/regResultWindow.fxml"));
                stage = AppStarter.getPrimaryStage();
                scene = new Scene(root);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            clientNet.setRegCommand("userExists");
            enterRegResultWindow();
        }
    }

    public void returnButton(ActionEvent actionEvent) {
        try {
            root = FXMLLoader.load(getClass().getResource("/ru/geekbrains/client/welcomeWindow.fxml"));
            stage = AppStarter.getPrimaryStage();
            scene = new Scene(root);
            stage.setScene(scene);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void enterRegResultWindow() {
        try {
            root = FXMLLoader.load(getClass().getResource("/ru/geekbrains/client/regResultWindow.fxml"));
            stage = AppStarter.getPrimaryStage();
            scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

package ru.geekbrains.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.geekbrains.common.CreateDirectoryMessage;
import ru.geekbrains.common.RenameFileMessage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewObjectNameWindowController implements Initializable {
    private static final ClientNet clientNet = ClientNet.getClientNet();

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public Label label;

    @FXML
    public TextField newName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String command = clientNet.getCommand();
        if(command.equals("renameFile")) {
           label.setText("Введите новое имя файла:");
        }
        if(command.equals("createDirectory")) {
            label.setText("Введите имя для новой директории:");
        }
    }


    public void okButton(ActionEvent actionEvent) {
        String name = newName.getText();
        if(name.equals(null) || name.equals("") || name.equals(" ")){
            clientNet.setIncorrectNameCommand("emptyField");
            try {
                root = FXMLLoader.load(getClass().getResource("/ru/geekbrains/client/incorrectNameWindow.fxml"));
                stage = AppStarter.getPrimaryStage();
                scene = new Scene(root);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        if(name.contains("\\") || name.contains("/") || name.contains(":") || name.contains("*") || name.contains("?")
                || name.contains("\"") || name.contains("<") || name.contains(">") || name.contains("|")) {
            clientNet.setIncorrectNameCommand("incorrectSigns");
            try {
                root = FXMLLoader.load(getClass().getResource("/ru/geekbrains/client/incorrectNameWindow.fxml"));
                stage = AppStarter.getPrimaryStage();
                scene = new Scene(root);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
            String command = clientNet.getCommand();
            if (command.equals("renameFile")) {
                clientNet.sendMessage(new RenameFileMessage(clientNet.getFileForRename(), name));
            }
            if (command.equals("createDirectory")) {
                clientNet.sendMessage(new CreateDirectoryMessage(name));
            }
            new Thread( () -> {
                while (true) {
                    if (clientNet.isOperationConfirmed() != null) {
                        break;
                    }
                }
            }).start();
            try {
                Thread.currentThread().sleep(200);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
            if(clientNet.isOperationConfirmed() == true) {
                try {
                    clientNet.setFileForRename(null);
                    root = FXMLLoader.load(getClass().getResource("/ru/geekbrains/client/mainWindow.fxml"));
                    stage = AppStarter.getPrimaryStage();
                    scene = new Scene(root);
                    stage.setScene(scene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                clientNet.setOperationConfirmed(null);
            } else {
                clientNet.setIncorrectNameCommand("objectExists");
                try {
                    root = FXMLLoader.load(getClass().getResource("/ru/geekbrains/client/incorrectNameWindow.fxml"));
                    stage = AppStarter.getPrimaryStage();
                    scene = new Scene(root);
                    stage.setScene(scene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                clientNet.setOperationConfirmed(null);
            }
    }

}

package ru.geekbrains.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AuthService {
   private ArrayList<String> loggedUsers;
   private Connection connection;

    public AuthService() {
        this.loggedUsers = new ArrayList<>();
    }


    public boolean isUserLogged(String login) {
        if(loggedUsers.contains(login)) {
            return true;
        }
        return false;
    }

    private boolean userExists(String login, Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE login = ?");
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            String dbLogin = resultSet.getString("login");
            if(dbLogin.equals(login)) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }


    public boolean authorization(String login, String password) {
        try {
            connection = DataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE login = ? AND password = ?");
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            String dbLogin = resultSet.getString("login");
            String dbPassword = resultSet.getString("password");
            if(dbLogin.equals(login) && dbPassword.equals(password)) {
                connection.close();
                return true;
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public boolean registration(String login, String password) {
        try {
            connection = DataSource.getConnection();
            if(userExists(login, connection) == false) {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (login, password) VALUES (?, ?)");
                preparedStatement.setString(1, login);
                preparedStatement.setString(2, password);
                preparedStatement.executeUpdate();
                connection.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public ArrayList<String> getLoggedUsers() {
        return loggedUsers;
    }

}


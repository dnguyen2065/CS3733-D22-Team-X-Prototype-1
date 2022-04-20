package edu.wpi.cs3733.D22.teamX.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import edu.wpi.cs3733.D22.teamX.App;
import edu.wpi.cs3733.D22.teamX.ConnectionSingleton;
import edu.wpi.cs3733.D22.teamX.DatabaseCreator;
import edu.wpi.cs3733.D22.teamX.LoginManager;
import edu.wpi.cs3733.D22.teamX.exceptions.loadSaveFromCSVException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class LoginScreenController implements Initializable {
  @FXML private VBox serverVBox;
  @FXML private JFXRadioButton optionEmbedded;
  @FXML private JFXRadioButton optionClient;
  @FXML private PasswordField password;
  @FXML private TextField username;
  @FXML private JFXButton loginButton, exitButton;
  @FXML private Label message;
  public static String currentUsername;

  @FXML
  public void validLogin() throws IOException {
    if (LoginManager.getInstance()
        .isValidLogin(username.getText(), password.getText().hashCode())) {
      currentUsername = username.getText();
      if (!ConnectionSingleton.getConnectionSingleton().isConnectionEstablished()) {
        if (optionEmbedded.isSelected()) {
          try {
            DatabaseCreator.initializeDB();
            ConnectionSingleton.getConnectionSingleton().setEmbedded();
            System.out.println("Apache Derby connection established :D");
          } catch (loadSaveFromCSVException e) {
            e.printStackTrace();
            System.exit(1);
          }
        }
        if (optionClient.isSelected()) {
          try {
            DatabaseCreator.initializeDB();
            ConnectionSingleton.getConnectionSingleton().setClient();
            System.out.println("Apache Derby connection established :D");
          } catch (loadSaveFromCSVException e) {
            e.printStackTrace();
            System.exit(1);
          }
        }
      }
      App.switchRoot();
      password.setOnKeyPressed(
          event -> {
            if (event.getCode() == KeyCode.ENTER) {
              try {
                App.switchRoot();
              } catch (IOException e) {
                e.printStackTrace();
              }
            }
          });
    } else {
      message.setText("    Your username or password is incorrect");
      message.setTextFill(Color.rgb(210, 39, 30));
      message.setTextAlignment(TextAlignment.CENTER);
    }
  }

  public void disableLoginButton() {
    loginButton.setDisable(username.getText().isEmpty() || password.getText().isEmpty());
  }

  @FXML
  void ExitApplication() {
    Platform.exit();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    if (ConnectionSingleton.getConnectionSingleton().isConnectionEstablished()) {
      serverVBox.setDisable(true);
      serverVBox.setVisible(false);
    }
  }
}

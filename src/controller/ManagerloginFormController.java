package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;


public class ManagerloginFormController {
    public  JFXTextField txtUserName;
    public JFXPasswordField pwdPassword;
    public AnchorPane loginContext;


    public CheckBox checkBtnShowpw;
    public JFXTextField TxtShowPw;
    
    
    public Label lblAddress;
    public Label lblContact;
    public Label lblEmail;
    public Label lblWhatsApp;
    public Button btnLogout;

    public Button btnlogin;


    public void loginOnaction(ActionEvent actionEvent) throws IOException {
            login();

    }
    private void setUI(String location) throws IOException {
        Stage stage=(Stage)  loginContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.setTitle("AdminForm");
    }
    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUBI("Dashboard");
    }
    private void setUBI(String location) throws IOException {
        Stage stage=(Stage)  loginContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
    }

    public void ShowPasswordOnAction(ActionEvent actionEvent) {
      if(checkBtnShowpw.isSelected()){
          pwdPassword.setVisible(false);
          TxtShowPw.setVisible(true);
          TxtShowPw.setText(pwdPassword.getText());
      }
      else{
          pwdPassword.setVisible(true);
          TxtShowPw.setVisible(false);
      }

    }
    private void login() throws IOException {
        String userName=txtUserName.getText();
        String pawd=pwdPassword.getText();
        if(userName.equalsIgnoreCase("gihan")&&pawd.equalsIgnoreCase("123")){
            setUI("AdminForm");
        }
        else{
            new Alert(Alert.AlertType.WARNING,"please try again").show();
        }
    }

    public void EnterOnAction(ActionEvent actionEvent) throws IOException {
       // login();
    }

    public void logoutOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        stage.close();
    }

    private Object Validate(){
        Pattern usernamePattern = Pattern.compile("^gihan$");
        Pattern passwordPattern = Pattern.compile("^123$");

        if(!usernamePattern.matcher(txtUserName.getText()).matches()) {
            //if the input is not matching
            addError(txtUserName);
            return txtUserName;
        }
        else{
            removeError(txtUserName);
            if(!passwordPattern.matcher(pwdPassword.getText()).matches()){
                addError(pwdPassword);
                return pwdPassword;
            }
            else {
                removeError(pwdPassword);

            }
        }
        return true;
    }
    private void addError(TextField txtField) {
        if (txtField.getText().length() > 0) {
           txtField.setStyle("-fx-border-color: red");
            // txtField.getParent().setStyle("-fx-background-color: red");
        }
        btnlogin.setDisable(true);
    }
    private void removeError(TextField txtField) {
           txtField.setStyle("-fx-border-color: green");
       //    txtField.getParent().setStyle("-fx-background-color: green");
        btnlogin.setDisable(false);
    }

    public void textFields_Key_Released(KeyEvent keyEvent) throws IOException {
        Validate();
     if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response = Validate();
            //if the response is a text field
            //that means there is a error
            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();// if there is a error just focus it
            } else if (response instanceof Boolean) {
              login();
            }
        }
    }
}

package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Pattern;

public class MainLoginFormController {
    public PasswordField pwdPassword;
    public TextField txtUserName;


    public AnchorPane mainLoginContext;

    public CheckBox chkShowPassword;
    public TextField txtShowPassword;
    public Button btnlogin;


    public void LoginOnAction(ActionEvent actionEvent) throws IOException {
        login();
    }
    private void setUI(String location) throws IOException {
        Stage stage=(Stage)  mainLoginContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.setTitle("Admin Form");
    }

    public void ShowPasswordOnAction(ActionEvent actionEvent) {
        if(chkShowPassword.isSelected()){
            pwdPassword.setVisible(false);
           txtShowPassword.setText( pwdPassword.getText());
           txtShowPassword.setVisible(true);
        }
        else{
            txtShowPassword.setVisible(false);
            pwdPassword.setVisible(true);
        }
    }

    public void EnterOnAction(ActionEvent actionEvent) throws IOException {
        login();
    }
    private void login() throws IOException {
        String UserName= txtUserName.getText();
        String password= pwdPassword.getText();
        if(UserName.equalsIgnoreCase("Kottulabs")&& password.equalsIgnoreCase ("123")){
            setUI("Dashboard");
        }
        else {
            new Alert(Alert.AlertType.WARNING,"Please Try Again").show();
        }
    }

    private Object Validate(){
        Pattern usernamePattern = Pattern.compile("^Kottulabs$");
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

        }
        return true;
    }
    private void addError(TextField txtField) {
        if (txtField.getText().length() > 0) {
            //txtField.setStyle("-fx-border-color: red");
            txtField.getParent().setStyle("-fx-background-color: red");
        }
        btnlogin.setDisable(true);
    }
    private void removeError(TextField txtField) {
       // txtField.setStyle("-fx-border-color: green");
        txtField.getParent().setStyle("-fx-background-color: green");
        btnlogin.setDisable(false);
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        Validate();
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response = Validate();
            //if the response is a text field
            //that means there is a error
            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();// if there is a error just focus it
            } else if (response instanceof Boolean) {

            }

        }
    }
}

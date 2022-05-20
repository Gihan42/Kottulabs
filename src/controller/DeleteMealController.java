package controller;

import CrudUtil.CrudUtil;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class DeleteMealController {

    public JFXTextField txtCode;
    public JFXTextField txtm_name;
    public JFXTextField txtdescription;
    public JFXTextField txtUnitPric;
    public Button btndelete;

    public void DeleteOnAction(ActionEvent actionEvent) {
        try{
            if(CrudUtil.execute
                    ("DELETE  FROM  Meals WHERE code=?",txtCode.getText())){
                new Alert(Alert.AlertType.CONFIRMATION,"DELETED").show();
            }
            else{
                new Alert(Alert.AlertType.WARNING,"SOMETHING WENT WRONG").show();
            }
        }
        catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        clearText();
    }

    public void SearchOnAction(ActionEvent actionEvent) {
        search();
    }
    private void search() {
        try {
            ResultSet resultSet = CrudUtil.execute
                    ("SELECT * FROM  Meals WHERE code=?",txtCode.getText());
            if (resultSet.next()) {
                txtm_name.setText(resultSet.getString(2));
                txtdescription.setText(resultSet.getString(3));
                txtUnitPric.setText(resultSet.getString(4));
            } else {
                new Alert(Alert.AlertType.ERROR, "EMPTY RESULT").show();
            }
        }
        catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    private void clearText(){
        txtCode.clear();
        txtm_name.clear();
        txtdescription.clear();
        txtUnitPric.clear();

    }

    private Object Validate(){
        Pattern idPattern = Pattern.compile("^(M00-)[0-9]{3,5}$");

        if(!idPattern.matcher(txtCode.getText()).matches()) {
            //if the input is not matching
            addError(txtCode);
            return txtCode;
        }
        else{
            removeError(txtCode);

        }
        return true;
    }
    private void addError(TextField txtField) {
        if (txtField.getText().length() > 0) {
            txtField.setStyle("-fx-border-color: red");
            txtField.getParent().setStyle("-fx-background-color: red");
        }
        btndelete.setDisable(true);
    }
    private void removeError(TextField txtField) {
        txtField.setStyle("-fx-border-color: green");
        txtField.getParent().setStyle("-fx-background-color: green");
        btndelete.setDisable(false);
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

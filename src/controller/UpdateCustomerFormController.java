package controller;

import CrudUtil.CrudUtil;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Customer;

import java.sql.*;
import java.util.regex.Pattern;

public class UpdateCustomerFormController {
    public JFXTextField txtUcusId;
    public JFXTextField txUcusName;
    public JFXTextField txtUCusAddress;
    public JFXTextField txtUCusPhoneno;
    public Button btnUpdate;

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        update();
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM  Customer WHERE c_nic=?",txtUcusId.getText());
            if (resultSet.next()) {
                txUcusName.setText(resultSet.getString(2));
                txtUCusAddress.setText(resultSet.getString(3));
                txtUCusPhoneno.setText(resultSet.getString(4));
            } else {
                new Alert(Alert.AlertType.ERROR, "EMPTY RESULT").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void clearText(){
        txtUcusId.clear();
        txUcusName.clear();
        txtUCusAddress.clear();
        txtUCusPhoneno.clear();

    }
private void update(){
    Customer c=new Customer(
            txtUcusId.getText(),
            txUcusName.getText(),
            txtUCusAddress.getText(),
            txtUCusPhoneno.getText()
    );
    try{

        boolean isUpdated=CrudUtil.execute
                ("UPDATE  Customer SET c_name=? , c_address=? , C_contact_no=? WHERE c_nic=?",
                        c.getCName(),c.getCAddress(),c.getCphnNo(),c.getCid());

        if(isUpdated){
            new Alert(Alert.AlertType.CONFIRMATION,"UPDATED !").show();
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
    private Object Validate(){
        Pattern idPattern = Pattern.compile("^[0-9]{11}[a-z]{1}|[0-9]{12}$");
        Pattern namePattern = Pattern.compile("^[A-z ]{3,30}$");
        Pattern addressPattern = Pattern.compile("^[A-z0-9 ,/]{4,100}$");
        Pattern phoneNumPattern = Pattern.compile("^(0)[0-9]{9}$");

        if(!idPattern.matcher(txtUcusId.getText()).matches()) {
            //if the input is not matching
            addError(txtUcusId);
            return txtUcusId;
        }
        else{
            removeError(txtUcusId);
            if (!namePattern.matcher(txUcusName.getText()).matches()) {
                //if the input is not matching
                addError(txUcusName);
                return txUcusName;
            }
            else{
                removeError(txUcusName);
                if(! addressPattern.matcher(txtUCusAddress.getText()).matches()){
                    addError(txtUCusAddress);
                    return txtUCusAddress;
                }else{
                    removeError(txtUCusAddress);
                    if(! phoneNumPattern.matcher(txtUCusPhoneno.getText()).matches()){
                        addError(txtUCusPhoneno);
                        return txtUCusPhoneno;
                    }
                    else{
                        removeError(txtUCusPhoneno);
                    }
                }
            }
        }
        return true;
    }
    private void addError(TextField txtField) {
        if (txtField.getText().length() > 0) {
            txtField.setStyle("-fx-border-color: red");
        }
        btnUpdate.setDisable(true);
    }
    private void removeError(TextField txtField) {
        txtField.setStyle("-fx-border-color: green");
        btnUpdate.setDisable(false);
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

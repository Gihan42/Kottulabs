package controller;

import CrudUtil.CrudUtil;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import db.DataSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Customer;

import java.io.IOException;
import java.sql.*;
import java.util.regex.Pattern;

public class SaveCustomerController {
    public JFXTextField txtCustomerId;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerAddress;
    public JFXTextField txxtCustomerPhnNo;
    public Button btnsaveCustomer;

    public void SaveCustomerOnAction(ActionEvent actionEvent)  {

        saveCustomer();

        clearText();
       /* Customer customer=new Customer(
                txtCustomerId.getText(),
                txtCustomerName.getText(),
                txtCustomerAddress.getText(),
                txxtCustomerPhnNo.getText()
        );
        if(DataSet.customerTable.add(customer)) {
            new Alert(Alert.AlertType.CONFIRMATION, "SAVE CUSTOMER").show();
        }
        else{
            new Alert(Alert.AlertType.ERROR, "Try Again").show();
        }
        System.out.println(customer);
        clearText();*/
    }
    private void clearText(){
        txtCustomerId.clear();
        txtCustomerName.clear();
        txtCustomerAddress.clear();
        txxtCustomerPhnNo.clear();

    }
    private void saveCustomer(){
        Customer c=new Customer(
                txtCustomerId.getText(),
                txtCustomerName.getText(),
                txtCustomerAddress.getText(),
                txxtCustomerPhnNo.getText()
        );
       /* Customer customer = new Customer();
        customer.getCid()*/

        try {
            if(CrudUtil.execute ("INSERT INTO Customer VALUES (?,?,?,?)",c.getCid(),c.getCName(),c.getCAddress(),c.getCphnNo())){
                new Alert(Alert.AlertType.CONFIRMATION,"Customer Saved").show();

            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Please Try Again").show();
        }
    }

    private Object Validate(){
        Pattern idPattern = Pattern.compile("^[0-9]{11}[a-z]{1}|[0-9]{12}$");
        Pattern namePattern = Pattern.compile("^[A-z ]{3,30}$");
        Pattern addressPattern = Pattern.compile("^[A-z0-9 ,/]{4,100}$");
        Pattern phoneNumPattern = Pattern.compile("^(0)[0-9]{9}$");

        if(!idPattern.matcher(txtCustomerId.getText()).matches()) {
            //if the input is not matching
            addError(txtCustomerId);
            return txtCustomerId;
        }
        else{
            removeError(txtCustomerId);
            if (!namePattern.matcher(txtCustomerName.getText()).matches()) {
                //if the input is not matching
                addError(txtCustomerName);
                return txtCustomerName;
            }
            else{
                removeError(txtCustomerName);
                if(! addressPattern.matcher(txtCustomerAddress.getText()).matches()){
                    addError(txtCustomerAddress);
                    return txtCustomerAddress;
                }else{
                    removeError(txtCustomerAddress);
                    if(! phoneNumPattern.matcher(txxtCustomerPhnNo.getText()).matches()){
                        addError(txxtCustomerPhnNo);
                        return txxtCustomerPhnNo;
                    }
                    else{
                        removeError(txxtCustomerPhnNo);
                    }
                }
                }
        }
        return true;
    }
    private void addError(TextField txtField) {
        if (txtField.getText().length() > 0) {
            txtField.getParent().setStyle("-fx-border-color: red");
            txtField.setStyle("-fx-border-color: red");
        }
        btnsaveCustomer.setDisable(true);
    }
    private void removeError(TextField txtField) {
        txtField.getParent().setStyle("-fx-border-color: green");
        txtField.setStyle("-fx-border-color: green");
        btnsaveCustomer.setDisable(false);
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
                System.out.println("Work");
                saveCustomer();
            }

        }
    }
}

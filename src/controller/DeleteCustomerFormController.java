package controller;

import CrudUtil.CrudUtil;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import db.DataSet;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import model.Customer;

import java.sql.*;
import java.util.regex.Pattern;

public class DeleteCustomerFormController {
    public JFXTextField txtDCustomerId;
    public JFXTextField txtDCustomerName;
    public JFXTextField txtDCustomerAddress;
    public JFXTextField txtDCustomerPhnNo;
    public Button btnDelete;


    public void DeleteOnAction(ActionEvent actionEvent) {

        try{
            if(CrudUtil.execute
                    ("DELETE  FROM  Customer WHERE c_nic=?",txtDCustomerId.getText())){
                new Alert(Alert.AlertType.CONFIRMATION,"DELETED").show();
            }
            else{
                new Alert(Alert.AlertType.WARNING,"SOMETHING WENT WRONG").show();
            }
        }
        catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void btnDeleteCustomerIdOnAction(ActionEvent actionEvent) {
        search();
    }

    private void search() {
        try {
          ResultSet resultSet=  CrudUtil.execute("SELECT * FROM Customer where c_nic=?",txtDCustomerId.getText());
          if(resultSet.next()){
              txtDCustomerName.setText(resultSet.getString(2));
              txtDCustomerAddress.setText(resultSet.getString(3));
              txtDCustomerPhnNo.setText(resultSet.getString(4));

          }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Object Validate(){
        Pattern idPattern = Pattern.compile("^[0-9]{11}[a-z]{1}|[0-9]{12}$");

        if(!idPattern.matcher(txtDCustomerId.getText()).matches()) {
            //if the input is not matching
            addError(txtDCustomerId);
            return txtDCustomerId;
        }else{
            removeError(txtDCustomerId);

        }
        return true;
    }
    private void addError(TextField txtField) {
        if (txtField.getText().length() > 0) {
            txtField.getParent().setStyle("-fx-border-color: red");
            txtField.setStyle("-fx-border-color: red");
        }
        btnDelete.setDisable(true);
    }
    private void removeError(TextField txtField) {
        txtField.getParent().setStyle("-fx-border-color: green");
        txtField.setStyle("-fx-border-color: green");

        btnDelete.setDisable(false);
    }
    public void textFields_Key_Released(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
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


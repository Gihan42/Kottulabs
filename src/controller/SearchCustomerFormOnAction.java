package controller;

import CrudUtil.CrudUtil;
import classcontroller.CustomerDataController;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import db.DataSet;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Customer;

import java.sql.*;
import java.util.regex.Pattern;

public class SearchCustomerFormOnAction {
    public JFXTextField txtSCustomerName;
    public JFXTextField txtSCustomerAddress;
    public JFXTextField txtCustomerid;
    public Button btnseacrh;
    public JFXTextField txtCustomerPhnNo;
    public JFXTextField txtCcustomerid;


    public void SearchCustomerOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        try {
            search();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void txtSeachOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        try {
            search();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void search() throws ClassNotFoundException, SQLException {

        try {
            ResultSet resultSet = CrudUtil.execute
                    ("SELECT * FROM  Customer WHERE c_nic=?", txtCcustomerid.getText());
            if (resultSet.next()) {
                txtSCustomerName.setText(resultSet.getString(2));
                txtSCustomerAddress.setText(resultSet.getString(3));
                txtCustomerPhnNo.setText(resultSet.getString(4));
            } else {
                new Alert(Alert.AlertType.ERROR, "EMPTY RESULT").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        Validate();

        /*for (Customer temp: DataSet.customerTable) {
                if(temp.getCid().equals(txtScustomerid.getText())){
                    txtSCustomerName.setText(temp.getCName());
                    txtSCustomerAddress.setText(temp.getCAddress());
                    txtSCustomerPhnNo.setText(temp.getCphnNo());
                    return;
                }
        }
        new Alert(Alert.AlertType.WARNING,"WRONG ID");*/
    }

    private Object Validate(){
        Pattern idPattern = Pattern.compile("^[0-9]{11}[a-z]{1}|[0-9]{12}$");

        if(!idPattern.matcher(txtCcustomerid.getText()).matches()) {
            //if the input is not matching
            addError(txtCcustomerid);
            return txtCcustomerid;
        }else{
            removeError(txtCcustomerid);

        }
        return true;
    }
    private void addError(TextField txtField) {
        if (txtField.getText().length() > 0) {
            txtField.getParent().setStyle("-fx-border-color: red");
            txtField.setStyle("-fx-border-color: red");
        }
        btnseacrh.setDisable(true);
    }
    private void removeError(TextField txtField) {
        txtField.getParent().setStyle("-fx-border-color: green");
        txtField.setStyle("-fx-border-color: green");
        btnseacrh.setDisable(false);
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


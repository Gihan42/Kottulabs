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
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.regex.Pattern;

public class SearchEmployeeController {

    public JFXTextField txtSearchEmployeeid;
    public JFXTextField txtEmployeeName;
    public JFXTextField txtEmployeeAddress;
    public JFXTextField txtEmployeePhoneNo;
    public Button txtSearchOnAction;
    public JFXTextField txtEmployeeSalary;
    public JFXTextField txtPostion;
    public Button btnSearch;

    public void txtSearchOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        try {
            search();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void SearchOnAction(ActionEvent actionEvent) throws ClassNotFoundException {

        try {
            search();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        clear();
    }

    private void search() throws SQLException, ClassNotFoundException {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM  Employee WHERE nic=?", txtSearchEmployeeid.getText());
            if (resultSet.next()) {
                txtEmployeeName.setText(resultSet.getString(2));
                txtEmployeeAddress.setText(resultSet.getString(3));
                txtEmployeePhoneNo.setText(resultSet.getString(4));
//                txtPostion.setText(resultSet.getString(5));
                txtEmployeeSalary.setText(resultSet.getString(5));
            } else {
                new Alert(Alert.AlertType.ERROR, "EMPTY RESULT").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Object Validate() {
        Pattern idPattern = Pattern.compile("^[0-9]{11}[a-z]{1}|[0-9]{12}$");

        if (!idPattern.matcher(txtSearchEmployeeid.getText()).matches()) {
            //if the input is not matching
            addError(txtSearchEmployeeid);
            return txtSearchEmployeeid;
        } else {
            removeError(txtSearchEmployeeid);

        }
        return true;
    }

    private void addError(TextField txtField) {
        if (txtField.getText().length() > 0) {
            txtField.getParent().setStyle("-fx-border-color: red");
            txtField.setStyle("-fx-border-color: red");
        }
        btnSearch.setDisable(true);
    }

    private void removeError(TextField txtField) {
        txtField.getParent().setStyle("-fx-border-color: green");
        txtField.setStyle("-fx-border-color: green");
        btnSearch.setDisable(false);
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

    private void clear() {
        txtSearchEmployeeid.clear();
        txtEmployeeName.clear();
        txtPostion.clear();
        txtEmployeeAddress.clear();
        txtEmployeePhoneNo.clear();
        txtEmployeeSalary.clear();
    }


}

package controller;

import CrudUtil.CrudUtil;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Customer;
import model.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class UpdateEmployeeController {
    public JFXTextField txtUemployeeNic;
    public JFXTextField txtUpdateEmployeeName;
    public JFXTextField txtUEmployeeAddress;
    public JFXTextField txtUEmployeePhoneNo;
    public JFXTextField txtUEmployeeSalary;
    public JFXTextField txtPostion;
    public ComboBox cmbPosition;

    static   String SD_ID = null;
    public Button bttnUpdate;

    public void initialize(){
        cmbPosition.getItems().add("Chef");
        cmbPosition.getItems().add("Waiter");
        cmbPosition.getItems().add("Cashier");
    }
    public void UpdateOnAction(ActionEvent actionEvent) {
        update();

    }

    public void txtSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        search();
    }

    public void PosistionOnAction(ActionEvent actionEvent) {
        txtPostion.setText(String.valueOf(cmbPosition.getValue()));
    }
    private void update() {
      //  Double salary = Double.parseDouble(txtUEmployeeSalary.getText());


        Employee e = new Employee(
                txtUemployeeNic.getText(),
                txtUpdateEmployeeName.getText(),
                txtUEmployeeAddress.getText(),
                txtUEmployeePhoneNo.getText(),
                Double.parseDouble(txtUEmployeeSalary.getText()),
                txtPostion.getText(),
                SD_ID

        );
        try {

            boolean isUpdated = CrudUtil.execute
                    ("UPDATE  Employee SET e_name=? , e_address=? , e_contactNo=?,e_salary=? WHERE nic=?",
                            e.getE_name(), e.getE_address(), e.getE_contactNo(),e.getE_salary(), e.getNic());

            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "UPDATED !").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "SOMETHING WENT WRONG").show();
            }
        } catch (SQLException | ClassNotFoundException c) {
            c.printStackTrace();
        }

        clearText();
    }

    private void search() throws SQLException, ClassNotFoundException {
        if(txtPostion.equals("SD001")){
            SD_ID="Waiter";
            txtPostion.setText(SD_ID);
        }
        else if(txtPostion.equals("SD002")){
            SD_ID="Cashier";
            txtPostion.setText(SD_ID);
        }
        else if(txtPostion.equals("SD003")){
            SD_ID="Chefe";
            txtPostion.setText(SD_ID);
        }
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM  Employee WHERE nic=?", txtUemployeeNic.getText());
            if (resultSet.next()) {
                txtUpdateEmployeeName.setText(resultSet.getString(2));
                txtUEmployeeAddress.setText(resultSet.getString(3));
                txtUEmployeePhoneNo.setText(resultSet.getString(4));
                txtUEmployeeSalary.setText(resultSet.getString(5));

                txtPostion.setText(resultSet.getString(6));
            } else {
                new Alert(Alert.AlertType.ERROR, "EMPTY RESULT").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void clearText(){
        txtUemployeeNic.clear();
        txtUpdateEmployeeName.clear();
        txtUEmployeeAddress.clear();
        txtUEmployeePhoneNo.clear();
        txtUEmployeeSalary.clear();
        txtPostion.clear();

    }

    private Object Validate(){
        Pattern idPattern = Pattern.compile("^[0-9]{11}[a-z]{1}|[0-9]{12}$");
        Pattern namePattern = Pattern.compile("^[A-z ]{3,30}$");
        Pattern addressPattern = Pattern.compile("^[A-z0-9 ,/]{4,50}$");
        Pattern phoneNumPattern = Pattern.compile("^(0)[0-9]{9}$");
        Pattern salaryPattern = Pattern.compile("^[1-9][0-9]*(.[0-9]{2})?$");

        if(!idPattern.matcher(txtUemployeeNic.getText()).matches()) {
            //if the input is not matching
            addError(txtUemployeeNic);
            return txtUemployeeNic;
        }
        else{
            removeError(txtUemployeeNic);
            if (!namePattern.matcher(txtUpdateEmployeeName.getText()).matches()) {
                //if the input is not matching
                addError(txtUpdateEmployeeName);
                return txtUpdateEmployeeName;
            }
            else{
                removeError(txtUpdateEmployeeName);
                if(! addressPattern.matcher(txtUEmployeeAddress.getText()).matches()){
                    addError(txtUEmployeeAddress);
                    return txtUEmployeeAddress;
                }else{
                    removeError(txtUEmployeeAddress);
                    if(! phoneNumPattern.matcher(txtUEmployeePhoneNo.getText()).matches()){
                        addError(txtUEmployeePhoneNo);
                        return txtUEmployeePhoneNo;
                    }
                    else{
                        removeError(txtUEmployeePhoneNo);
                        if(! salaryPattern.matcher(txtUEmployeeSalary.getText()).matches()){
                            addError(txtUEmployeeSalary);
                            return txtUEmployeeSalary;
                        }
                        else{
                            removeError(txtUEmployeeSalary);
                        }
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
        bttnUpdate.setDisable(true);
    }
    private void removeError(TextField txtField) {
        txtField.getParent().setStyle("-fx-border-color: green");
        txtField.setStyle("-fx-border-color: green");
        bttnUpdate.setDisable(false);
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
                update();
            }

        }
    }
}

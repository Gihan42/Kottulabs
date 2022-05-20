package controller;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Customer;
import model.Employee;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class SaveEmployeeController {
    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtContactNo;
    public JFXTextField txtSalary;
    public ComboBox <String>cmbPosition;
    public JFXTextField txtPostion;
    public JFXTextField txtSalaryDetail;
    public Button btnSave;

    public void initialize(){
        cmbPosition.getItems().add("Chef");
        cmbPosition.getItems().add("Waiter");
        cmbPosition.getItems().add("Cashier");
}
    public void btnSaveOnAction(ActionEvent actionEvent) {
        Double salary =  Double.parseDouble(txtSalary.getText());
        String SD_ID = null;
        if(salary>=10000){
            if(salary<=30000){
                SD_ID="SD001";
            }
            else if(salary>=30000 & salary<=40000){
                SD_ID="SD002";
            }
            else{
                SD_ID="SD003";
            }
        }
        Employee em=new Employee(
                txtId.getText(),txtName.getText(), txtAddress.getText(),txtContactNo.getText(),
               salary,SD_ID,txtPostion.getText()
        );
        //load driver
        try {

            //write sql queery
            String sql="INSERT INTO Employee VALUES (?,?,?,?,?,?,?)";
            //create statement (data pass krann )
            PreparedStatement stm= DBConnection.getInstance().getConnection().prepareStatement(sql);
            stm.setString(1,em.getNic());
            stm.setString(2,em.getE_name());
            stm.setString(3,em.getE_address());
            stm.setString(4,em.getE_contactNo());
            stm.setDouble(5,em.getE_salary());
            stm.setString(6,em.getSalary_detailId());
            stm.setString(7,em.getPosition());
            //execute query
            if( stm.executeUpdate()>0){
                new Alert(Alert.AlertType.CONFIRMATION,"Employee Saved").show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Please Try Again").show();
        }
        clear();
    }

    public void PosistionOnAction(ActionEvent actionEvent) {
        txtPostion.setText(cmbPosition.getValue());
    }

    private Object Validate(){
        Pattern idPattern = Pattern.compile("^[0-9]{11}[a-z]{1}|[0-9]{12}$");
        Pattern namePattern = Pattern.compile("^[A-z ]{3,30}$");
        Pattern addressPattern = Pattern.compile("^[A-z0-9 ,/]{4,50}$");
        Pattern phoneNumPattern = Pattern.compile("^(0)[0-9]{9}$");
        Pattern salaryPattern = Pattern.compile("^[1-9][0-9]*(.[0-9]{2})?$");

        if(!idPattern.matcher(txtId.getText()).matches()) {
            //if the input is not matching
            addError(txtId);
            return txtId;
        }
        else{
            removeError(txtId);
            if (!namePattern.matcher(txtName.getText()).matches()) {
                //if the input is not matching
                addError(txtName);
                return txtName;
            }
            else{
                removeError(txtName);
                if(! addressPattern.matcher(txtAddress.getText()).matches()){
                    addError(txtAddress);
                    return txtAddress;
                }else{
                    removeError(txtAddress);
                    if(! phoneNumPattern.matcher(txtContactNo.getText()).matches()){
                        addError(txtContactNo);
                        return txtContactNo;
                    }
                    else{
                        removeError(txtContactNo);
                        if(! salaryPattern.matcher(txtSalary.getText()).matches()){
                            addError(txtSalary);
                            return txtSalary;
                        }
                        else{
                            removeError(txtSalary);
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
        btnSave.setDisable(true);
    }
    private void removeError(TextField txtField) {
        txtField.getParent().setStyle("-fx-border-color: green");
        txtField.setStyle("-fx-border-color: green");
        btnSave.setDisable(false);
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

            }

        }
    }
    private void clear(){
        txtId.clear();
        txtName.clear();
        txtPostion.clear();
        txtAddress.clear();
        txtSalary.clear();
        txtContactNo.clear();
    }
}

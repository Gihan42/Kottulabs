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

public class DeleteEmployeeController {

    public JFXTextField txtEmployeeNic;
    public JFXTextField txtEmployeeName;
    public JFXTextField txtEmployeeAddress;
    public JFXTextField txtEmployeeNo;
    public JFXTextField txtEmployeeSALARY;
    public Button btnDelete;

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        try {
          if(CrudUtil.execute("DELETE  FROM  Employee WHERE nic=?", txtEmployeeNic.getText())){
              new Alert(Alert.AlertType.CONFIRMATION,"Employee Is Deleted").show();
            }
            else{
              new Alert(Alert.AlertType.ERROR,"Something Went Wrong").show();
            }

        }catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        clear();
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {search(); }
    private void search(){
        try {
            ResultSet resultSet=CrudUtil.execute("SELECT * FROM  Employee WHERE nic=?",txtEmployeeNic.getText());
            if(resultSet.next()){
                txtEmployeeName.setText(resultSet.getString(2));
                txtEmployeeAddress.setText(resultSet.getString(3));
                txtEmployeeNo.setText(resultSet.getString(4));
                txtEmployeeSALARY.setText(resultSet.getString(5));
            }
            else{
                new Alert(Alert.AlertType.WARNING,"Empty Result Set").show();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Object Validate(){
        Pattern idPattern = Pattern.compile("^[0-9]{11}[a-z]{1}|[0-9]{12}$");

        if(!idPattern.matcher(txtEmployeeNic.getText()).matches()) {
            //if the input is not matching
            addError(txtEmployeeNic);
            return txtEmployeeNic;
        }else{
            removeError(txtEmployeeNic);

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
    private void clear(){
        txtEmployeeNic.clear();
        txtEmployeeName.clear();
        txtEmployeeAddress.clear();
        txtEmployeeNo.clear();
        txtEmployeeSALARY.clear();
    }
}

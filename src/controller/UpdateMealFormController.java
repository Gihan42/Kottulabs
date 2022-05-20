package controller;

import CrudUtil.CrudUtil;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.Customer;
import model.Meals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class UpdateMealFormController {
    public JFXTextField txtm_code;
    public JFXTextField txtm_name;
    public JFXTextField txtdescription;
    public JFXTextField txtUnitprice;
    public CheckBox cheHalf;
    public CheckBox chefull;
    public JFXTextField txtfoodCode;
    public Button btnUpdate;


    public void updateOnAction(ActionEvent actionEvent) {
        Meals m=new Meals(
                txtm_code.getText(),
                txtm_name.getText(),
                txtdescription.getText(),
                Double.parseDouble(txtUnitprice.getText()),
                txtfoodCode.getText()
        );
        try{

            boolean isUpdated=CrudUtil.execute
                    ("UPDATE  Meals SET name=? , description=? , unitPrice=?,food_code=? WHERE code=?",
                            m.getM_name(),m.getDescription(),m.getUnitPrice(),m.getFoodCategory(),m.getM_code());

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

    public void cheHalfOnAction(ActionEvent actionEvent) {
        if(cheHalf.isSelected()){
            txtdescription.setText(cheHalf.getText());
        }
    }

    public void fullOnAction(ActionEvent actionEvent) {
        if (chefull.isSelected()){
            txtdescription.setText(chefull.getText());
        }
    }

    public void SearchOnAction(ActionEvent actionEvent) {
        search();
    }
    private void search() {
       try {
            ResultSet resultSet = CrudUtil.execute
                    ("SELECT * FROM  Meals WHERE code=?",txtm_code.getText());
            if (resultSet.next()) {
                txtm_name.setText(resultSet.getString(2));
                txtdescription.setText(resultSet.getString(3));
                txtUnitprice.setText(resultSet.getString(4));
                txtfoodCode.setText(resultSet.getString(5));

            } else {
                new Alert(Alert.AlertType.ERROR, "EMPTY RESULT").show();
            }
        }
        catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void cheFullOnAction(MouseEvent mouseEvent) {

        }
    private void clearText(){
        txtm_code.clear();
        txtm_name.clear();
        txtdescription.clear();
        txtUnitprice.clear();
        txtfoodCode.clear();

    }

    private Object Validate() {
        Pattern idPattern = Pattern.compile("^(M00-)[0-9]{3,5}$");
        Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
        Pattern pricePattern = Pattern.compile("^[1-9][0-9]*(.[0-9]{2})?$$");


        if (!idPattern.matcher(txtm_code.getText()).matches()) {
            //if the input is not matching
            addError(txtm_code);
            return txtm_code;
        } else {
            removeError(txtm_code);
            if (!namePattern.matcher(txtm_name.getText()).matches()) {
                addError(txtm_name);
                return txtm_name;
            } else {
                removeError(txtm_name);
                if (!pricePattern.matcher(txtUnitprice.getText()).matches()) {
                    addError(txtUnitprice);
                    return txtUnitprice;
                } else {
                    removeError(txtUnitprice);
                }
            }

        }
        return true;
    }
    private void addError(TextField txtField) {
        if (txtField.getText().length() > 0) {
            txtField.setStyle("-fx-border-color: red");
            txtField.getParent().setStyle("-fx-background-color: red");
        }
        btnUpdate.setDisable(true);
    }
    private void removeError(TextField txtField) {
        txtField.setStyle("-fx-border-color: green");
        txtField.getParent().setStyle("-fx-background-color: green");
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


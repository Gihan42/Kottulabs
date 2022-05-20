package controller;

import CrudUtil.CrudUtil;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Customer;
import model.Meals;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class SavemealController {

    public JFXTextField txtMealsCode;
    public JFXTextField txtMealsName;
    public JFXTextField txtDescription;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtfoodCode;

    public CheckBox cheHalf;
    public CheckBox cheFull;
    public Button btnSave;


    public void mealsSaveOnAction(ActionEvent actionEvent) {
        Meals m=new Meals(
                txtMealsCode.getText(),
                txtMealsName.getText(),
                txtDescription.getText(),
                Double.parseDouble(txtUnitPrice.getText()) ,
                txtfoodCode.getText()
        );
        //load driver
        try {
            if(CrudUtil.execute ("INSERT INTO Meals VALUES (?,?,?,?,?)",
                    m.getM_code(),m.getM_name(),m.getDescription(),m.getUnitPrice(),m.getFoodCategory())){
                new Alert(Alert.AlertType.CONFIRMATION,"Meal Saved").show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Please Try Again").show();
        }
        clearText();
    }
    public void halfOnAction(ActionEvent actionEvent) {
        if(cheHalf.isSelected()){
            txtDescription.setText(cheHalf.getText());
        }
    }

    public void fullOnAction(ActionEvent actionEvent) {
            if (cheFull.isSelected()){
            txtDescription.setText(cheFull.getText());
        }
    }
    private void clearText(){
        txtMealsCode.clear();
        txtMealsName.clear();
        txtDescription.clear();
        txtUnitPrice.clear();
        txtfoodCode.clear();
    }

    private Object Validate(){
        Pattern idPattern = Pattern.compile("^(M00-)[0-9]{3,5}$");
        Pattern namePattern=Pattern.compile("^[A-z ]{3,20}$");
        Pattern pricePattern=Pattern.compile("^[1-9][0-9]*(.[0-9]{2})?$$");


        if(!idPattern.matcher(txtMealsCode.getText()).matches()) {
            //if the input is not matching
            addError(txtMealsCode);
            return txtMealsCode;
        }
        else{
            removeError(txtMealsCode);
            if(!namePattern.matcher(txtMealsName.getText()).matches()){
                addError(txtMealsName);
                return txtMealsName;
            }
            else{
                removeError(txtMealsName);
                if(!pricePattern.matcher(txtUnitPrice.getText()).matches()){
                    addError(txtUnitPrice);
                    return txtUnitPrice;
                }
                else{
                    removeError(txtUnitPrice);
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
        btnSave.setDisable(true);
    }
    private void removeError(TextField txtField) {
        txtField.setStyle("-fx-border-color: green");
        txtField.getParent().setStyle("-fx-background-color: green");
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

            }

        }
    }
}


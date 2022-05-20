package controller;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BackgroundImage;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController  {
   public AnchorPane slideMenuContext;
   public AnchorPane dashBoardContext;
    public AnchorPane slideContext;
    public Button btnMenu;

   private void setRUI(String location) throws IOException {
        Stage stage=(Stage)  dashBoardContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.setTitle("Login Form");
    }

    public void slideMenuOnAction(ActionEvent actionEvent) {

    }

    public void AdminOnAction(ActionEvent actionEvent) throws IOException {
       setRUI("ManagerloginForm");
    }

    public void MenuOnAction(ActionEvent actionEvent) throws IOException {
       setMUI("MenuForm");
    }
    private void setMUI(String location) throws IOException {
        Stage stage=(Stage)  dashBoardContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.setTitle("Menu Form");
    }


    public void CashierOnAction(ActionEvent actionEvent) throws IOException {
       setCUI("PlaceOrderForm");
    }

    private void setCUI(String location) throws IOException {
        Stage stage=(Stage)  dashBoardContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.setTitle("Place Order");
    }
}

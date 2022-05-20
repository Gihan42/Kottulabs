package controller;

import CrudUtil.CrudUtil;
import classcontroller.CustomerDataController;
import classcontroller.MealsDataController;
import classcontroller.OrderDataController;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import db.DataSet;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Meals;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MenuFormController {
    public AnchorPane menucontext;
    public AnchorPane kottuContext;
    public Label lblDate;
    public Label lblTime;
    public AnchorPane riceContext;
    public AnchorPane softDrinkContext;
    public ComboBox <String>cmbkottu;
    public ComboBox<String> cmbFriedRice;
    public ComboBox <String>cmbSoftDrink;


    public JFXTextField txtdescription;

    public Label lblname;
    public Label lbldescription;
    public Label lblprice;
    public TableView tblMenu;
    public TableColumn colName;
    public TableColumn colDescription;
    public TableColumn colPrice;
    public TableColumn colmId;
    public TableColumn colMname;
    public TableColumn colMUnitPrice;

    public void initialize() {
        generateDateTime();
        try {
            loadAllMenu();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        setKottuIds();
        setTbaleStyle();
        colmId.setCellValueFactory(new PropertyValueFactory<>("m_code"));
        colMname.setCellValueFactory(new PropertyValueFactory<>("m_name"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    public void logoutOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) menucontext.getScene().getWindow();
        stage.close();
    }
    public void HomeOnAction(ActionEvent actionEvent) throws IOException {
        setUI("Dashboard");
    }
    private void setUI(String location) throws IOException {
        Stage stage=(Stage)  menucontext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.setTitle("Place Order");
    }
    private void generateDateTime() {
        lblDate.setText(LocalDate.now().toString());

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            lblTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void AddMealOnAction(ActionEvent actionEvent) throws IOException {
        setUi("SavemealForm");

    }

    
    private void setUi(String URI) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/" + URI + ".fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.setTitle(URI);
        stage.show();
    }

    public void KottuOnAction(ActionEvent actionEvent) {
        kottuContext.setVisible(true);
        riceContext.setVisible(false);
        softDrinkContext.setVisible(false);
        setData(cmbkottu);
    }

    public void riceOnAction(ActionEvent actionEvent) {
        riceContext.setVisible(true);
        kottuContext.setVisible(false);
        softDrinkContext.setVisible(false);
        setData(cmbFriedRice);
    }

    public void SoftDrinkOnAction(ActionEvent actionEvent) {
        softDrinkContext.setVisible(true);
        kottuContext.setVisible(false);
        riceContext.setVisible(false);
        setData(cmbSoftDrink);
    }

    public void OrderOnAction(ActionEvent actionEvent) throws IOException {
        setOUI("PlaceOrderForm");
    }
    private void setOUI(String location) throws IOException {
        Stage stage=(Stage)  menucontext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.setTitle("Place Order");
    }

    private void setKottuIds() {

        DataSet.mealsTable.clear();

        try {
            ResultSet resultSet= CrudUtil.execute("SELECT * FROM Meals");
            while(resultSet .next()){
                db.DataSet.mealsTable.add(new  Meals (
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4),
                        resultSet.getString(5)
                )
                );
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ObservableList<String> kottu = FXCollections.observableArrayList();
        ObservableList<String> rice = FXCollections.observableArrayList();
        ObservableList<String> softd = FXCollections.observableArrayList();
        for (Meals m: DataSet.mealsTable) {
            String fc = m.getFoodCategory();
          
            if(fc.equals("Kottu")){
                kottu.add(m.getM_code());
            }else if(fc.equals("Rice")){
                rice.add(m.getM_code());
            }else{
                softd.add(m.getM_code());
            }
        }
        cmbkottu.getItems().clear();
        cmbFriedRice.getItems().clear();
        cmbSoftDrink.getItems().clear();
        cmbkottu.setItems(kottu);
        cmbFriedRice.setItems(rice);
        cmbSoftDrink.setItems(softd);
        
}
private void setData(ComboBox box){
    for (Meals m:
            DataSet.mealsTable) {
        if(box.getValue().equals(m.getM_code())){
            lblname.setText(m.getM_name());
            lbldescription.setText(m.getDescription());
            lblprice.setText(String.valueOf(m.getUnitPrice()));
            PlaceOrderFormController.idfromMenu = m.getM_code();
        }
    }
}

    public void refreshOnAction(ActionEvent actionEvent) {
        try {
            loadAllMenu();
            setKottuIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
        //setKottuIds();

    private void setTbaleStyle(){
        colMname.setStyle("-fx-border-color: #33393C");
        colmId.setStyle("-fx-border-color: #33393C");
        colDescription.setStyle("-fx-border-color: #33393C");

    }
    private void loadAllMenu() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Meals";
        PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ObservableList<Meals> oblist;
        try (ResultSet resultSet = statement.executeQuery()) {

            oblist = FXCollections.observableArrayList();
            while (resultSet.next()) {
                oblist.add(
                        new Meals(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getDouble(4),
                                resultSet.getString(5)
                        )
                );

            }
        }
        tblMenu.setItems(oblist);
        tblMenu.refresh();
    }
}

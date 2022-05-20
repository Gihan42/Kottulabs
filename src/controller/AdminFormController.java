package controller;

import CrudUtil.CrudUtil;
import com.jfoenix.controls.JFXTextField;
import com.mysql.cj.xdevapi.Result;
import db.DBConnection;
import javafx.animation.TranslateTransition;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Customer;
import model.Employee;
import model.Meals;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.regex.Pattern;

public class AdminFormController implements Initializable {
    public Button btnAddmeal;
    public Button btnSaveCustomer;

    public AnchorPane tableViewContext;

    public TableView<Employee> tblEmployeeTable;
    public TableColumn colEid;//employees
    public TableColumn colEname;
    public TableColumn colEaddress;
    public TableColumn colContact;
    public TableColumn colEsalary11;

    public AnchorPane adminContext;
    public AnchorPane imageContext;

    public Label lblTitle;
    public AnchorPane slideContext;
    public Label lblName;
    public Button btnIncome;

    public TableView<Customer> tblCustomer;
    public TableColumn colCusId;
    public TableColumn colCusName;
    public TableColumn colCusAddress;
    public TableColumn colCusContact;

    public TextField txtSearch;
    public JFXTextField txtCustomerID;
    public JFXTextField txtAddress;
    public JFXTextField txtContactNo;
    public JFXTextField txtCustomerName;

    public TableView<Meals> tblMealTable;
    public TableColumn colMname;
    public TableColumn colDescription;
    public TableColumn colMUnitPrice;
    public TableColumn colmId;


    public AnchorPane mealcontext;
    public JFXTextField txtMealCode;
    public JFXTextField txtDescription;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtmealName;
    public JFXTextField txtcateogory;
    public TextField txtSearchmeals;
    public AnchorPane customerContext;

    public void initialize(URL location, ResourceBundle resources) {

        colCusId.setCellValueFactory(new PropertyValueFactory<>("Cid"));
        colCusName.setCellValueFactory(new PropertyValueFactory<>("CName"));
        colCusAddress.setCellValueFactory(new PropertyValueFactory<>("CAddress"));
        colCusContact.setCellValueFactory(new PropertyValueFactory<>("CphnNo"));

        colEid.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colEname.setCellValueFactory(new PropertyValueFactory<>("e_name"));
        colEaddress.setCellValueFactory(new PropertyValueFactory<>("e_address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("e_contactNo"));
        colEsalary11.setCellValueFactory(new PropertyValueFactory<>("e_salary"));

        colmId.setCellValueFactory(new PropertyValueFactory<>("m_code"));
        colMname.setCellValueFactory(new PropertyValueFactory<>("m_name"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colMUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));


        adminContext.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(adminContext);

            slide.setToX(0);
            slide.play();

            adminContext.setTranslateX(-176);


            slide.setOnFinished((ActionEvent e) -> {
                adminContext.setVisible(true);
                slideContext.setVisible(false);

            });
        });
        try {
            loadAllCustomer();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        try {
            LoadAllmeals();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void LoadALLCustomerOnAction(ActionEvent actionEvent) {
        setlblCostomer();

        tblMealTable.setVisible(false);
        tblEmployeeTable.setVisible(false);
        imageContext.setVisible(false);
        slideContext.setVisible(false);
        mealcontext.setVisible(false);
        customerContext.setVisible(true);
    }

    private void loadAllCustomer() throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM Customer";
        PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        ObservableList<Customer> oblist = FXCollections.observableArrayList();
        while (resultSet.next()) {
            oblist.add(
                    new Customer(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4)
                    )
            );

        }
        tblCustomer.setItems(oblist);
        mealcontext.setVisible(false);
        customerContext.setVisible(true);
    }

    private void setlblCostomer() {
        lblTitle.setText("Load All Customer");
        lblTitle.setVisible(true);
        tblCustomer.setVisible(true);


    }

    ////////////////////////////////////
    public void LoadAllEmployeeOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        setlblTitleEmployee();
        tblMealTable.setVisible(false);
        imageContext.setVisible(false);
        slideContext.setVisible(false);
        tblCustomer.setVisible(false);
        loadAllEmployee();
        mealcontext.setVisible(false);
        customerContext.setVisible(true);
    }

    private void loadAllEmployee() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Employee";
        PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        ObservableList<Employee> oblist = FXCollections.observableArrayList();
        while (resultSet.next()) {
            oblist.add(
                    new Employee(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getDouble(5),
                            resultSet.getString(6),
                            resultSet.getString(7)

                    )
            );
        }
        tblEmployeeTable.setItems(oblist);
        tblEmployeeTable.refresh();

    }

    public void SaveEmployeeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("SaveEmployee");
        setlblTitleEmployee();
        slideContext.setVisible(false);
        mealcontext.setVisible(false);
        customerContext.setVisible(true);
        tblEmployeeTable.setVisible(true);
        tblMealTable.setVisible(false);
        tblCustomer.setVisible(false);
        tblEmployeeTable.refresh();
    }

    public void SearchEmployeeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("SearchEmployee");
        setlblTitleEmployee();
        slideContext.setVisible(false);
        mealcontext.setVisible(false);
        customerContext.setVisible(true);
        tblEmployeeTable.setVisible(true);
        tblMealTable.setVisible(false);
        tblCustomer.setVisible(false);
        tblEmployeeTable.refresh();
    }

    public void UpdateEmployeeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("UpdateEmployee");
        setlblTitleEmployee();
        slideContext.setVisible(false);
        mealcontext.setVisible(false);
        customerContext.setVisible(true);
        tblEmployeeTable.setVisible(true);
        tblMealTable.setVisible(false);
        tblCustomer.setVisible(false);
        tblEmployeeTable.refresh();

    }

    public void DeleteEmployeeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DeleteEmployee");
        setlblTitleEmployee();
        slideContext.setVisible(false);
        mealcontext.setVisible(false);
        customerContext.setVisible(true);
        tblEmployeeTable.setVisible(true);
        tblMealTable.setVisible(false);
        tblCustomer.setVisible(false);
        tblEmployeeTable.refresh();
    }

    public void SalaryManageOnAction(ActionEvent actionEvent) throws IOException {
        setUi("salaryManagement");
        slideContext.setVisible(false);
        customerContext.setVisible(true);
        mealcontext.setVisible(false);
        tblEmployeeTable.setVisible(true);
        tblMealTable.setVisible(false);
        tblCustomer.setVisible(false);
        tblEmployeeTable.refresh();
    }


    public void IncomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("IncomeForm");
        slideContext.setVisible(false);
        customerContext.setVisible(true);
        mealcontext.setVisible(false);

    }

    private void setlblTitleEmployee() {
        lblTitle.setText("Load All Employee");
        lblTitle.setVisible(true);
        tblEmployeeTable.setVisible(true);
    }

    ///////////////////////////////////////
    public void LoadmealOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        LoadAllmeals();
        lblTitle.setText("Load All Meals");
        lblTitle.setVisible(true);

        tblMealTable.setVisible(true);
        tblCustomer.setVisible(false);
        tblEmployeeTable.setVisible(false);
        imageContext.setVisible(false);
        mealcontext.setVisible(true);
        customerContext.setVisible(false);
        //slideContext.setVisible(true);

    }

    private void LoadAllmeals() throws SQLException, ClassNotFoundException {
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
        tblMealTable.setItems(oblist);
        tblMealTable.refresh();
    }

    public void SaveMealOnAction(ActionEvent actionEvent) throws IOException {
        setUi("SavemealForm");
        tblMealTable.setVisible(true);
        tblEmployeeTable.setVisible(false);
        tblCustomer.setVisible(false);
        mealcontext.setVisible(true);
        customerContext.setVisible(false);
    }

    public void UpdateMealOnAction(ActionEvent actionEvent) throws IOException {
        setUi("UpdateMealForm");
        tblMealTable.setVisible(true);
        tblEmployeeTable.setVisible(false);
        tblCustomer.setVisible(false);
        mealcontext.setVisible(true);
        customerContext.setVisible(false);

    }

    public void DeleteMealOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DeleteMeal");
        tblMealTable.setVisible(true);
        tblEmployeeTable.setVisible(false);
        tblCustomer.setVisible(false);
        mealcontext.setVisible(true);
        customerContext.setVisible(false);

    }

    private void setUI(String location) throws IOException {
        Stage stage = (Stage) adminContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUI("Mainlogin");
    }

    public void HomeOnAction(ActionEvent actionEvent) throws IOException {
        setHUI("Dashboard");
    }

    private void setHUI(String location) throws IOException {
        Stage stage = (Stage) adminContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
    }

    public void SlideOnAction(ActionEvent actionEvent) {
        slideContext.setTranslateX(-176);
        slideContext.setVisible(true);

        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(slideContext);

        slide.setToX(0);
        slide.play();

        slideContext.setTranslateX(-176);
    }

    /*private void setHeUI(String location) throws IOException {
        Stage stage=(Stage)  adminContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
    }*/
    private void setUi(String URI) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/" + URI + ".fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.setTitle(URI);
        stage.setResizable(false);
        stage.show();
    }

    public void LogOutOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) adminContext.getScene().getWindow();
        stage.close();
    }

    public void refreshOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        loadAllEmployee();
        loadAllCustomer();
        LoadAllmeals();
        clear();
    }

    public void searchOnACTION(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        search();
    }

    public void txtseOnAction(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
        search();
    }

    private void search() throws ClassNotFoundException, SQLException {

        ResultSet resultSet = CrudUtil.execute
                ("SELECT * FROM  Customer WHERE c_nic=?", txtSearch.getText());
        if (resultSet.next()) {
            txtCustomerID.setText(resultSet.getString(1));
            txtCustomerName.setText(resultSet.getString(2));
            txtAddress.setText(resultSet.getString(3));
            txtContactNo.setText(resultSet.getString(4));
        } else {
            new Alert(Alert.AlertType.ERROR, "EMPTY RESULT").show();
        }
    }

    public void searchOrderDetailsOnActon(ActionEvent actionEvent) throws IOException {
        setUi("OrderDetailsForm");
    }

    public void txtealsOnActon(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Mealsearch();
    }

    private void Mealsearch() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = CrudUtil.execute
                ("SELECT * FROM  Meals WHERE code=?", txtSearchmeals.getText());
        if (resultSet.next()) {
            txtmealName.setText(resultSet.getString(2));
            txtDescription.setText(resultSet.getString(3));
            txtUnitPrice.setText(resultSet.getString(4));
            txtcateogory.setText(resultSet.getString(5));

        } else {
            new Alert(Alert.AlertType.ERROR, "EMPTY RESULT").show();
        }
    }

    private void clear() {
        txtSearchmeals.clear();
        txtmealName.clear();
        txtDescription.clear();
        txtUnitPrice.clear();
        txtcateogory.clear();

        txtCustomerID.clear();
        txtCustomerName.clear();
        txtAddress.clear();
        txtSearch.clear();
        txtContactNo.clear();

    }

    private Object Validatecustomer() {
        Pattern idPattern = Pattern.compile("^[0-9]{11}[a-z]{1}|[0-9]{12}$");

        if (!idPattern.matcher(txtSearch.getText()).matches()) {
            //if the input is not matching
            addError(txtSearch);
            return txtSearch;
        } else {
            removeError(txtSearch);

        }
        return true;
    }

    private void addError(TextField txtField) {
        if (txtField.getText().length() > 0) {
            txtField.getParent().setStyle("-fx-border-color: red");
        }
    }

    private void removeError(TextField txtField) {
        txtField.getParent().setStyle("-fx-border-color: green");
    }

    public void textFields_Key_Released(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        Validatecustomer();
        // ValidateMeals();
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response = Validatecustomer();
            //if the response is a text field
            //that means there is a error
            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();// if there is a error just focus it
            } else if (response instanceof Boolean) {

            }

        }
    }

    private Object ValidateMeals() {
        Pattern idPattern = Pattern.compile("^(M00-)[0-9]{3,5}$");

        if (!idPattern.matcher(txtMealCode.getText()).matches()) {
            //if the input is not matching
            addError(txtMealCode);
            return txtMealCode;
        } else {
            removeError(txtMealCode);

        }
        return true;
    }



    public void textFields_Key_Released_Meals(KeyEvent keyEvent) {
     /*   ValidateMeals();
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response = ValidateMeals();
            //if the response is a text field
            //that means there is a error
            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();// if there is a error just focus it
            } else if (response instanceof Boolean) {

            }*/

        }


    public void printEmployeedetailsOnAction(ActionEvent actionEvent) throws IOException {
        setUi("EmployeeIdForm");
        slideContext.setVisible(false);
    }
}



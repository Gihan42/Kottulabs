package controller;

import CrudUtil.CrudUtil;
import classcontroller.CustomerDataController;
import classcontroller.MealsDataController;
import classcontroller.OrderDataController;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import tm.CartTm;
//

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {
    public AnchorPane slideContext;
    public Button btnSlideBar;
    public AnchorPane placeOrderContext;
    public AnchorPane totalContext;

    public Label lblDate;
    public Label lblTime;
    public Button btnLogOut;

    public ComboBox<String> cmbCustomerId;
    public JFXTextField txrCustomerName;
    public JFXTextField txtCustomerAddress;
    public JFXTextField txtCustomerPhoneNo;

    public ComboBox<String> cmbMeals;
    public JFXTextField txtMName;
    public JFXTextField txtQtyaOnHand;
    public JFXTextField txtUPrice;
    public JFXTextField txtQty;
    public JFXTextField txtDescription;

    public JFXTextField txtQtyOnHand;

    public TableView<CartTm> tblcart;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colqTY;
    public TableColumn colToatl;
    public TableColumn colbutton;
    public TableColumn colBalance;

    public JFXTextField txtbalance2;
    public JFXTextField txtTotal;
    public Button btnDineIN;
    public CheckBox cheTakeAway;
    public CheckBox cheDainIN;
    public Button btnAddToCart;


    public Label lblTotal2;
    public Label lbltToatal1;
    public Button btnplaceOrder;

    public Label lblOrderd;
    private String orderId;
    private String incomeid;

    static String idfromMenu = null;

    public void slideBarOnAction(ActionEvent actionEvent) {
        System.out.println("ok");
        slideContext.setTranslateX(-176);
        slideContext.setVisible(true);


        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(slideContext);

        slide.setToX(0);
        slide.play();

        slideContext.setTranslateX(-176);
        totalContext.setVisible(true);
        lblTotal2.setText(lbltToatal1.getText());
    }
    private void coloumnBorder(){
        colCode.setStyle("-fx-border-color: #989da3");
        colbutton.setStyle("-fx-border-color:  #989da3 ");
        colBalance.setStyle("-fx-border-color:  #989da3");
        colqTY.setStyle("-fx-border-color:  #989da3");
        colDescription.setStyle("-fx-border-color:  #989da3");
        colToatl.setStyle("-fx-border-color:  #989da3");
        colUnitPrice.setStyle("-fx-border-color:  #989da3");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       // tableStyle();
        coloumnBorder();
        if (idfromMenu != null) {
            cmbMeals.setValue(idfromMenu);
            setMealDetail(idfromMenu);
        }

        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colqTY.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colToatl.setCellValueFactory(new PropertyValueFactory<>("total"));
        colbutton.setCellValueFactory(new PropertyValueFactory<>("btn"));
        colBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));


        placeOrderContext.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();

            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(placeOrderContext);

            slide.setToX(0);
            slide.play();

            placeOrderContext.setTranslateX(-176);


            slide.setOnFinished((ActionEvent e) -> {
                // slideContext.setVisible(true);
                slideContext.setVisible(false);
                totalContext.setVisible(false);


            });

        });

        generateDateTime();
        setCustomerIds();
        setMeals();
        setOrderId();


        //lisner
        setIds();

       /* tblcart.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    removeFromCart(newValue);
                });*/


    }

    public String generateOrderId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT id FROM orders ORDER BY id DESC LIMIT 1");
        if (resultSet.next()) {
            String oldId = resultSet.getString(1);
            String substring = oldId.substring(1, 4);
            int intId = Integer.parseInt(substring);

            intId = intId + 1;
            if (intId < 10) {
                return "O00" + intId;
            } else if (intId < 100) {
                return "O0" + intId;
            } else if (intId < 1000) {
                return "O" + intId;
            }
        }
        return "O001";
    }

    private void setOrderId() {
        try {
            orderId = generateOrderId();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setMealDetail(String selectetMealcode) {
        try {
            Meals i = MealsDataController.getMealCode(selectetMealcode);
            if (i != null) {
                txtMName.setText(i.getM_name());
                txtDescription.setText(i.getDescription());
                txtUPrice.setText(String.valueOf(i.getUnitPrice()));
            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Resultset").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setMeals() {
        try {
            cmbMeals.setItems(FXCollections.observableArrayList(MealsDataController.getMealsCode()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setCustomerDetails(String selectedCustomewrId) {
        try {
            Customer cus = CustomerDataController.getCustomer(selectedCustomewrId);
            //id samana customer knek netnm data tika set wenna
            if (cus != null) {
                txrCustomerName.setText(cus.getCName());
                txtCustomerAddress.setText(cus.getCAddress());
                txtCustomerPhoneNo.setText(cus.getCphnNo());
            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }
        } catch (SQLException throwables) {

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    //combobox data pass
    private void setCustomerIds() {
        try {
            ObservableList<String> cusIdList = FXCollections.observableArrayList(
                    CustomerDataController.getCustomerId()
            );
            cmbCustomerId.setItems(cusIdList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

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

    public void SaveCustomerOnAction(ActionEvent actionEvent) throws IOException {
        setUi("SaveCustomerForm");
        slideContext.setVisible(false);
        totalContext.setVisible(false);
    }

    public void SearchCustomerOnAction(ActionEvent actionEvent) throws IOException {
        setUi("SearchCustomerForm");
        slideContext.setVisible(false);
        totalContext.setVisible(false);
    }

    public void UpdateCustomerOnAction(ActionEvent actionEvent) throws IOException {
        setUi("UpdateCustomerForm");
        slideContext.setVisible(false);
        totalContext.setVisible(false);
    }

    public void DeleteCustomerOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DeleteCustomerForm");
        slideContext.setVisible(false);
        totalContext.setVisible(false);
    }

    public void LoadAllCustomerOnAction(ActionEvent actionEvent) throws IOException {
        setUi("LoadAllCustomerForm");
        slideContext.setVisible(false);
        totalContext.setVisible(false);
    }

    private void setUi(String URI) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/" + URI + ".fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.setTitle(URI);
        stage.setResizable(false);
        stage.show();
    }

    public void backtoDOnAction(ActionEvent actionEvent) throws IOException {
        setUBI("Dashboard");
    }

    private void setUBI(String location) throws IOException {
        Stage stage = (Stage) placeOrderContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
    }

    public void loginOnAction(ActionEvent actionEvent) throws IOException {
        setLUBI("Mainlogin");
    }

    private void setLUBI(String location) throws IOException {
        Stage stage = (Stage) placeOrderContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
    }

    public void logoutOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) btnLogOut.getScene().getWindow();
        stage.close();
    }

    public void incomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("IncomeForm");
        //System.out.println("income");
    }

    ObservableList<CartTm> tmList = FXCollections.observableArrayList();
   // static double totalcost;

    public void AddToCartOnAction(ActionEvent actionEvent) {
        setValues();
    }

    private void clearText() {
        txtbalance2.clear();
        txtQtyaOnHand.clear();
        txtQty.clear();
        txtMName.clear();
        txtUPrice.clear();
        txtCustomerPhoneNo.clear();
        txtCustomerAddress.clear();
        txtDescription.clear();

    }


    private void setValues() {
        double totalcost=0;
       // calculateTotal();
        double unitPirce = Double.parseDouble(txtUPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());
        double qtyOnhand = Double.parseDouble(txtQtyOnHand.getText());
        //double balance = qtyOnhand - totalcost;
      //  String bl = String.valueOf(balance);
       // txtbalance2.setText(bl);
        if (qtyOnhand < unitPirce) {

            new Alert(Alert.AlertType.WARNING, "Please check the customer on hand again!").show();

        }
        else {

            if (cheDainIN.isSelected()) {
                new Alert(Alert.AlertType.CONFIRMATION, "Add to 5%").show();
                double db = unitPirce * qty;
                totalcost = db + db * 5 / 100;

            } else if (cheTakeAway.isSelected()) {
                totalcost = unitPirce * qty;
            }
            double balance = qtyOnhand - totalcost;
            String bl = String.valueOf(balance);

            txtbalance2.setText(bl);


      /*  if (qtyOnhand < unitPirce) {
            btnplaceOrder.setDisable(true);
            btnAddToCart.setDisable(true);
            new Alert(Alert.AlertType.WARNING, "Please check the customer's money again!").show();

        }*/

            CartTm isExists = isExists(cmbMeals.getValue());
            if (isExists != null) {
                for (CartTm temp : tmList
                ) {
                    temp.setQty(temp.getQty() + qty);
                    temp.setTotal(temp.getTotal() + totalcost);
                    //qtyon hand ekt ona
                    temp.setBalance(temp.getBalance() - totalcost);
                }
            }
            //assign new value
            else {
                Button btn = new Button("delete");
                CartTm tm = new CartTm(
                        cmbMeals.getValue(),
                        txtDescription.getText(),
                        unitPirce,
                        qty,
                        totalcost,
                        btn,
                        balance
                );
                tmList.add(tm);
                tblcart.setItems(tmList);
                ssetCartButton();

                btn.setOnAction(e -> {
                    tmList.remove(tm);
                });
            }
            }

            tblcart.refresh();
            calculateTotal();


    }

    private void ssetCartButton() {

        if (cheDainIN.isSelected()) {
            btnAddToCart.setDisable(false);
        } else if (cheTakeAway.isSelected()) {
            btnAddToCart.setDisable(false);
        }
    }

    private CartTm isExists(String itemCode) {
        for (CartTm tm : tmList
        ) {
            if (tm.getCode().equals(itemCode)) {
                return tm;
            }
        }
        return null;
    }


    private void calculateTotal() {
        double tot=0;
        for (CartTm tm : tmList) {
            tot = tot + tm.getTotal();
        }
        lbltToatal1.setText(String.valueOf(tot));
        System.out.println(tot);
    }

    public void clearOnAction(ActionEvent actionEvent) {
        try {
            clearText();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }

    public void placeOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Order order = new Order(
                orderId,
                lblDate.getText(),
                cmbCustomerId.getValue(),
                Double.parseDouble(lbltToatal1.getText())
        );
        ArrayList<OrderDetails> details = new ArrayList<>();
        for (CartTm tm : tmList) {
            details.add(
                    new OrderDetails(
                            orderId,
                            tm.getCode(),
                            cmbCustomerId.getValue(),
                            tm.getQty(),
                            tm.getUnitPrice(),
                            lblDate.getText(),
                            lblTime.getText()


                    )
            );
        }
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            boolean isOrderSave = new OrderDataController().saveOrder(order);
            // orderdetails ekata data vatilada blnwa

            if (isOrderSave) {
                boolean isdetailSaved = new OrderDataController().saveOrderDetails(details);
                if (isdetailSaved) {
                    connection.commit();
                    new Alert(Alert.AlertType.CONFIRMATION, "Order Saved !").show();
                    setOrderId();
                } else {
                    connection.rollback();
                    new Alert(Alert.AlertType.ERROR, "NOT  Saved !").show();
                }
            } else {
                connection.rollback();
                new Alert(Alert.AlertType.ERROR, "NOT  Saved !").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
       // setIncome();
        setIncomeId();
    }

   /* private void setIncome() throws SQLException {
        Order order = new Order(
                orderId,
                lblDate.getText(),
                cmbCustomerId.getValue(),
                Double.parseDouble(lbltToatal1.getText())

        );
        ArrayList<IncomeD> data = new ArrayList<>();
        for (CartTm tm : tmList) {
            data.add(
                    new IncomeD(
                            orderId,
                            lblDate.getText(),
                            cmbMeals.getValue(),
                            lbltToatal1.getText()
                    )
            );
            Connection connection = null;
            try {
                connection = DBConnection.getInstance().getConnection();
                connection.setAutoCommit(false);
                boolean isOrderSave = new OrderDataController().saveOrder(order);
                // orderdetails ekata data vatilada blnwa

                if (isOrderSave) {
                    boolean isIncomesave = new OrderDataController().saveIncome(data);
                    if (isIncomesave) {
                        connection.commit();
                     //   new Alert(Alert.AlertType.CONFIRMATION, " Saved !").show();
                        setOrderId();
                    } else {
                        connection.rollback();
                      //  new Alert(Alert.AlertType.ERROR, "NOT  Saved !").show();
                    }
                } else {
                    connection.rollback();
                    //new Alert(Alert.AlertType.ERROR, "NOT  Saved !").show();
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                connection.setAutoCommit(true);
            }
        }
    }*/
    private String IncomeId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet= CrudUtil.execute("SELECT Iid FROM Income ORDER BY Iid DESC LIMIT 1");
        while (resultSet.next()) {
            String oldincomeId = resultSet.getString(1);
            String Isubstring = oldincomeId.substring(1, 4);
            int intincomeId = Integer.parseInt(Isubstring);

            intincomeId = intincomeId + 1;
            if (intincomeId < 10) {
                return "I00" + intincomeId;
            } else if (intincomeId < 100) {
                return "I0" + intincomeId;
            } else if (intincomeId < 1000) {
                return "I" + intincomeId;
            }
        }
        return "I001";
    }
    private void setIncomeId() {
        try {
            incomeid = IncomeId();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void RefreshOnAction(ActionEvent actionEvent) {
        setIds();
        setCustomerIds();

    }
    //load mcustomer & meals id
    public void setIds(){
        cmbCustomerId.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    setCustomerDetails(newValue);
                });
        cmbMeals.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    setMealDetail(newValue);
                });
    }

    public void printBilOnAction(ActionEvent actionEvent) {
       // System.out.println("print bill");
        double tot=Double.parseDouble(lbltToatal1.getText());
        double cOnhand=Double.parseDouble(txtQtyOnHand.getText());
        HashMap total2=new HashMap();
        total2.put("TotalCost",tot);
        total2.put("CustomerOnHand",cOnhand);

        ObservableList<CartTm> tableRecords = tblcart.getItems(); // bean collection

        try {
            JasperReport compiledReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/view/report/PrintBilljrxml.jasper"));
            ObservableList<CartTm> items = tblcart.getItems();
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport,total2, new JRBeanCollectionDataSource(items));
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
        }

    }

    public void txtQtyEnterOnAction(ActionEvent actionEvent) {
         double uPrice = Double.parseDouble(txtUPrice.getText());
         int qty = Integer.parseInt(txtQty.getText());
         double v = uPrice * (double)qty;
         txtQtyOnHand.setText(String.valueOf(v));
    }
}




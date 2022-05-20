package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.OrderDetails;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDetailsFormController {
    public TableView tblOrderDetails;
    public TableColumn colCId;
    public TableColumn colMId;
    public TableColumn colQty;
    public TableColumn colDate;
    public TableColumn colTime;
    public TableColumn colRemove;

    public void initialize() throws SQLException, ClassNotFoundException {
        loadAllCustomer();
        colCId.setCellValueFactory(new PropertyValueFactory<>("cus_Id"));
        colMId.setCellValueFactory(new PropertyValueFactory<>("M_Code"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
    }

    private void loadAllCustomer() throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM OrderDetail";
        PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        ObservableList<OrderDetails> oblist = FXCollections.observableArrayList();
        while (resultSet.next()) {
            oblist.add(
                    new OrderDetails(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getInt(4),
                            resultSet.getDouble(5),
                            resultSet.getString(6),
                            resultSet.getString(7)


                    )
            );

        }
        tblOrderDetails.setItems(oblist);
    }
}

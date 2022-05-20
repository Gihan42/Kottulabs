package controller;

import CrudUtil.CrudUtil;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoadAllCustomerFormController {

    public TableView tblCustomer;
    public TableColumn colCusId;
    public TableColumn colCusName;
    public TableColumn colCusAddress;
    public TableColumn colCusContact;

    public void initialize() throws SQLException, ClassNotFoundException {
        loadAllCustomer();
        colCusId.setCellValueFactory(new PropertyValueFactory<>("Cid"));
        colCusName.setCellValueFactory(new PropertyValueFactory<>("CName"));
        colCusAddress.setCellValueFactory(new PropertyValueFactory<>("CAddress"));
        colCusContact.setCellValueFactory(new PropertyValueFactory<>("CphnNo"));
    }
    private void loadAllCustomer() throws SQLException, ClassNotFoundException {

        ResultSet resultSet= CrudUtil.execute("SELECT * FROM Customer");

        ObservableList<Customer> oblist = FXCollections.observableArrayList();
        while(resultSet.next()){
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
    }
    }


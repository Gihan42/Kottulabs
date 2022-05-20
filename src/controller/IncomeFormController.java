package controller;

import CrudUtil.CrudUtil;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Income;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IncomeFormController {
    public AnchorPane DailyInComeContext;
    public AnchorPane monthlyInComeContext;

    //monthly barchart
    public BarChart monthlyBarchart;
    public CategoryAxis XAxis;
    public NumberAxis YAxis;
    public Label lblTotal;

    public TableColumn colIncome;
    public TableColumn colDate;
    public TableView tblIncome;

    public Button btnMontlyInCome;
    public Button btnDailyInCome;
    public TableView tblMIncome;
    public TableColumn colDateM;
    public TableColumn colIncomeM;

    public void initialize(){
       // monthlyInCome();
      /*  try {
            loadTotalAmount();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colIncome.setCellValueFactory(new PropertyValueFactory<>("income"));
        try {
            loadIncome();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    public void monthlyIncomeOnAction(ActionEvent actionEvent) {
        DailyInComeContext.setVisible(false);
        monthlyInComeContext.setVisible(true);

    }

    public void DailyIncomeOnAction(ActionEvent actionEvent) {
        DailyInComeContext.setVisible(true);
        monthlyInComeContext.setVisible(false);
    }
 /*   private void monthlyInCome(){

        XYChart.Series<String,Double> series2020 =new XYChart.Series<>();
        series2020.setName("2020");
        series2020.getData().add(new XYChart.Data<>("jan",200.0));
        series2020.getData().add(new XYChart.Data<>("feb",201.0));
        series2020.getData().add(new XYChart.Data<>("march",222.0));
        series2020.getData().add(new XYChart.Data<>("april",200.0));
        series2020.getData().add(new XYChart.Data<>("may",201.0));
        series2020.getData().add(new XYChart.Data<>("june",222.0));
        series2020.getData().add(new XYChart.Data<>("july",200.0));
        series2020.getData().add(new XYChart.Data<>("sep",201.0));
        series2020.getData().add(new XYChart.Data<>("oct",222.0));
        series2020.getData().add(new XYChart.Data<>("no",200.0));
        series2020.getData().add(new XYChart.Data<>("dec",201.0));

        monthlyBarchart.getData().addAll(series2020);
    }*/
    public void setTotal() throws SQLException, ClassNotFoundException {

    }
    private void loadTotalAmount() throws SQLException, ClassNotFoundException {
        PreparedStatement stm= DBConnection.getInstance().getConnection().prepareStatement("SELECT SUM(total) as total FROM Income");
        ResultSet resultSet=stm.executeQuery();
        Double total=0.00;

        while (resultSet.next()){
            total+=total+resultSet.getDouble(1);
            System.out.println(total);
            lblTotal.setText(String.valueOf(total));
        }
    }

    private void loadIncome() throws SQLException, ClassNotFoundException {
        ResultSet resultSet= CrudUtil.execute("SELECT date,SUM(total)FROM orders GROUP BY date");
      ObservableList<Income> oblist=FXCollections.observableArrayList();
      while(resultSet.next()){
          oblist.add(
                  new Income(
                          resultSet.getString(1),
                          resultSet.getDouble(2)
                  )
          );
          tblIncome.setItems(oblist);
          tblIncome.refresh();
      }

    }

    public void chartOnAction(ActionEvent actionEvent) {
        try {

            //catch the report
            JasperDesign load= JRXmlLoader.load(this.getClass().getResourceAsStream("/view/report/Income.jrxml"));
            //compile the report
            Connection connection = DBConnection.getInstance().getConnection();
            JasperReport jasperReport = JasperCompileManager.compileReport(load);//alter + enter

            //fill the information report needed
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null,connection );//alt+enter

            //report view
            JasperViewer.viewReport(jasperPrint,false);

            /*
            Connection connection = DBConnection.getDbConnection().getConnection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, connection);
            JasperViewer.viewReport(jasperPrint, false);
             */

        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}



package controller;

import CrudUtil.CrudUtil;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import model.Employee;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SalaryManageController {
    public JFXTextField txtEmployeeNic;
    public JFXTextField txtsalary;
    public Label lblName;
    public Label lblAddress;
    public Label lblPhonenum;
    public Label lblPosition;

    static   String SD_ID = null;

    public void initialize(){

    }

    public void txtsearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        search();
    }
    private void search() throws SQLException, ClassNotFoundException {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM  Employee WHERE nic=?", txtEmployeeNic.getText());
            if (resultSet.next()) {
                lblName.setText(resultSet.getString(2));
                lblAddress.setText(resultSet.getString(3));
                lblPhonenum.setText(resultSet.getString(4));
                txtsalary.setText(resultSet.getString(5));
                lblPosition.setText(resultSet.getString(6));
            } else {
                new Alert(Alert.AlertType.ERROR, "EMPTY RESULT").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
    }

    public void saveOnAction(ActionEvent actionEvent) {
        update();
    }
    private void update(){
        Double salary = Double.parseDouble(txtsalary.getText());

            Employee e = new Employee(
                    txtEmployeeNic.getText(),
                    lblName.getText(),
                    lblAddress.getText(),
                    lblPhonenum.getText(),
                    salary,
                    lblPosition.getText(),
                    SD_ID

            );
            try {
                boolean isUpdated = CrudUtil.execute
                        ("UPDATE  Employee SET e_salary=?  WHERE nic=?",
                              e.getE_salary(),e.getNic());

                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "UPDATED !").show();
                } else {
                    new Alert(Alert.AlertType.WARNING, "SOMETHING WENT WRONG").show();
                }
            } catch (SQLException | ClassNotFoundException c) {
                c.printStackTrace();
            }
        }

    public void ShowchartOnAction(ActionEvent actionEvent) {
     try {

            //catch the report
          JasperDesign load= JRXmlLoader.load(this.getClass().getResourceAsStream("/view/report/kottulabs.jrxml"));
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

    /*public void PosistionOnAction(ActionEvent actionEvent) {
        lblPosition.setText(cmbPosition.getValue());
    }*/
}


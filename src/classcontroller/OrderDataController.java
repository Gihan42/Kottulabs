package classcontroller;

import CrudUtil.CrudUtil;
import model.IncomeD;
import model.Order;
import model.OrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDataController {

    //data save order
    public boolean saveOrder(Order order) throws SQLException, ClassNotFoundException {
       return CrudUtil.execute("INSERT INTO orders VALUES(?,?,?,?)"
                ,order.getId(),order.getDate(),order.getCustomerId(),order.getTotalcost());
    }
    //datasaveorder details
    public boolean saveOrderDetails(ArrayList<OrderDetails>details) throws SQLException, ClassNotFoundException {
        for (OrderDetails det:details) {
            boolean isDetailsSave=CrudUtil.execute("INSERT INTO orderdetail VALUES(?,?,?,?,?,?,?)",
                    det.getOrderId(),det.getM_Code(),det.getCus_Id(),det.getQty(),det.getUnitPrice(),det.getDate(),det.getTime());
           /* if(isDetailsSave){
                updateqy(det.getM_Code(),det.getQty());
            }*/
        }
        return true;
    }
    public boolean saveIncome(ArrayList<IncomeD> detail) throws SQLException, ClassNotFoundException {
        for(IncomeD income:detail) {
            boolean isIncomeSave = CrudUtil.execute("INSERT INTO Income VALUES(?,?,?,?)",
                    income.getI_code(), income.getDate(), income.getMealCode(), income.getTotalCost());
        }
        return true;
    }

}

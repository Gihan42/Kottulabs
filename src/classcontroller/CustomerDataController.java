package classcontroller;

import CrudUtil.CrudUtil;
import model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDataController {

    public static ArrayList<String> getCustomerId() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT c_nic FROM Customer");
        ArrayList<String> ids = new ArrayList<>();
        while (result.next()) {
            ids.add(result.getString(1));
        }
        return ids;
    }
    public static Customer getCustomer(String id) throws SQLException, ClassNotFoundException {
       ResultSet resulset= CrudUtil.execute("SELECT * FROM Customer WHERE c_nic=?",id);
        if(resulset.next()){
            return new Customer(
            resulset.getString(1),
            resulset.getString(2),
            resulset.getString(3),
            resulset.getString(4)
            );
        }
        return null;
    }
}


package classcontroller;

import CrudUtil.CrudUtil;
import model.Meals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MealsDataController {
    public static ArrayList<String> getMealsCode() throws SQLException, ClassNotFoundException {
      ResultSet result= CrudUtil.execute("SELECT * FROM Meals");
      ArrayList <String> mcode=new ArrayList<>();
      while(result.next()){
            mcode.add(result.getString(1));
      }
      return mcode;
    }
    public static Meals getMealCode(String m_code) throws SQLException, ClassNotFoundException {
        ResultSet resultSet= CrudUtil.execute("SELECT * FROM Meals WHERE code=?",m_code);
        if (resultSet .next()){
            return new  Meals (
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getString(5)
            );
        }
        return null;
    }

    public static ArrayList<String> getKMealsCode() throws SQLException, ClassNotFoundException {
        ResultSet result= CrudUtil.execute("SELECT * FROM Meals");
        ArrayList <String> foodCategory=new ArrayList<>();
        while(result.next()){
            foodCategory.add(result.getString(5));
        }
        return foodCategory;
    }

}

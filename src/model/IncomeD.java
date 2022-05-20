package model;

public class IncomeD {
    private String i_code;
    private String date;
    private String mealCode;
    private String totalCost;


    public String getI_code() {
        return i_code;
    }

    public void setI_code(String i_code) {
        this.i_code = i_code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMealCode() {
        return mealCode;
    }

    public void setMealCode(String mealCode) {
        this.mealCode = mealCode;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public IncomeD(String i_code, String date, String mealCode, String totalCost) {
        this.i_code = i_code;
        this.date = date;
        this.mealCode = mealCode;
        this.totalCost = totalCost;
    }

    public IncomeD() {
    }

    @Override
    public String toString() {
        return "Income{" +
                "i_code='" + i_code + '\'' +
                ", date='" + date + '\'' +
                ", mealCode='" + mealCode + '\'' +
                ", totalCost='" + totalCost + '\'' +
                '}';
    }
}

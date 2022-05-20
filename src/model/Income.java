package model;

public class Income {
    private String Date;
    private double income;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public Income(String date, double income) {
        Date = date;
        this.income = income;
    }

    public Income() {
    }

    @Override
    public String toString() {
        return "Income{" +
                "Date='" + Date + '\'' +
                ", income=" + income +
                '}';
    }
}

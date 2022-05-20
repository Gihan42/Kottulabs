package model;

public class Order {
     private String id;
     private String date;
     private String customerId;
      private double totalcost;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public double getTotalcost() {
        return totalcost;
    }

    public void setTotalcost(double totalcost) {
        this.totalcost = totalcost;
    }

    public Order(String id, String date, String customerId, double totalcost) {
        this.id = id;
        this.date = date;
        this.customerId = customerId;
        this.totalcost = totalcost;
    }

    public Order() {
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", customerId='" + customerId + '\'' +
                ", totalcost=" + totalcost +
                '}';
    }
}

package model;

public class OrderDetails {
    private String  orderId ;
    private String  M_Code ;
    private String cus_Id ;
    private int qty ;
    private double unitPrice ;
    private String date ;
    private String time ;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getM_Code() {
        return M_Code;
    }

    public void setM_Code(String m_Code) {
        M_Code = m_Code;
    }

    public String getCus_Id() {
        return cus_Id;
    }

    public void setCus_Id(String cus_Id) {
        this.cus_Id = cus_Id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public OrderDetails(String orderId, String m_Code, String cus_Id, int qty, double unitPrice, String date, String time) {
        this.orderId = orderId;
        M_Code = m_Code;
        this.cus_Id = cus_Id;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.date = date;
        this.time = time;
    }

    public OrderDetails() {

    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "orderId='" + orderId + '\'' +
                ", M_Code='" + M_Code + '\'' +
                ", cus_Id='" + cus_Id + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}

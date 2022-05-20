package model;

public class Meals {
   private String m_code;
   private String  m_name;
   private String description;
   private double unitPrice;
   private String foodCategory;

    public String getM_code() {
        return m_code;
    }

    public void setM_code(String m_code) {
        this.m_code = m_code;
    }

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(String foodCategory) {
        this.foodCategory = foodCategory;
    }

    public Meals(String m_code, String m_name, String description, double unitPrice, String foodCategory) {
        this.m_code = m_code;
        this.m_name = m_name;
        this.description = description;
        this.unitPrice = unitPrice;
        this.foodCategory = foodCategory;
    }

    public Meals() {
    }

    @Override
    public String toString() {
        return "Meals{" +
                "m_code='" + m_code + '\'' +
                ", m_name='" + m_name + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                ", foodCategory='" + foodCategory + '\'' +
                '}';
    }
}

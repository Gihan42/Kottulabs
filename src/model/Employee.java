package model;

public class Employee {
     private String nic;
     private String e_name;
     private String e_address;
     private String e_contactNo;
     private double e_salary;
     private String  salary_detailId;
     private String Position;

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getE_name() {
        return e_name;
    }

    public void setE_name(String e_name) {
        this.e_name = e_name;
    }

    public String getE_address() {
        return e_address;
    }

    public void setE_address(String e_address) {
        this.e_address = e_address;
    }

    public String getE_contactNo() {
        return e_contactNo;
    }

    public void setE_contactNo(String e_contactNo) {
        this.e_contactNo = e_contactNo;
    }

    public double getE_salary() {
        return e_salary;
    }

    public void setE_salary(double e_salary) {
        this.e_salary = e_salary;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public Employee() {
    }

    public Employee(String nic, String e_name, String e_address, String e_contactNo, double e_salary,String  salary_detailId, String position) {
        this.nic = nic;
        this.e_name = e_name;
        this.e_address = e_address;
        this.e_contactNo = e_contactNo;
        this.e_salary = e_salary;
        this.salary_detailId= salary_detailId;
        Position = position;
    }

    public String getSalary_detailId() {
        return salary_detailId;
    }

    public void setSalary_detailId(String salary_detailId) {
        this.salary_detailId = salary_detailId;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "nic='" + nic + '\'' +
                ", e_name='" + e_name + '\'' +
                ", e_address='" + e_address + '\'' +
                ", e_contactNo='" + e_contactNo + '\'' +
                ", e_salary=" + e_salary +
                ", salary_detailId='" + salary_detailId + '\'' +
                ", Position='" + Position + '\'' +
                '}';
    }
}

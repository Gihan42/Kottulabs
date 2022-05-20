package model;

public class Customer {
    private String Cid;
    private String CName;
    private String CAddress;
    private String CphnNo;

    public String getCid() {
        return Cid;
    }

    public void setCid(String cid) {
        Cid = cid;
    }

    public String getCName() {
        return CName;
    }

    public void setCName(String CName) {
        this.CName = CName;
    }

    public String getCAddress() {
        return CAddress;
    }

    public void setCAddress(String CAddress) {
        this.CAddress = CAddress;
    }

    public String getCphnNo() {
        return CphnNo;
    }

    public void setCphnNo(String cphnNo) {
        CphnNo = cphnNo;
    }

    public Customer() {
    }

    public Customer(String cid, String CName, String CAddress, String cphnNo) {
        Cid = cid;
        this.CName = CName;
        this.CAddress = CAddress;
        CphnNo = cphnNo;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "Cid='" + Cid + '\'' +
                ", CName='" + CName + '\'' +
                ", CAddress='" + CAddress + '\'' +
                ", CphnNo='" + CphnNo + '\'' +
                '}';
    }
}

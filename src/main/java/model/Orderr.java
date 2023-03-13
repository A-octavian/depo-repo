package model;

public class Orderr {
    private int id;
    private String clientName;
    private String productName;
    private int quant;
    private int price;

    public Orderr() {
    }

    public Orderr(int id, String clientName, String productName, int quant, int price) {
        this.id = id;
        this.clientName = clientName;
        this.productName = productName;
        this.quant = quant;
        this.price = price;
    }

    public Orderr(String clientName, String productName, int quant, int price) {
        this.clientName = clientName;
        this.productName = productName;
        this.quant = quant;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuant() {
        return quant;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", clientName='" + clientName + '\'' +
                ", productName='" + productName + '\'' +
                ", quant=" + quant +
                ", price=" + price +
                '}';
    }

}

package model;

public class Product {
    private int id;
    private String name;
    private int quant;
    private int price;

    public Product() {
    }

    public Product(int id, String name, int quant, int price) {
        super();
        this.id = id;
        this.name = name;
        this.quant = quant;
        this.price = price;
    }

    public Product(String name, int quant, int price) {
        super();
        this.name = name;
        this.quant = quant;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuant() {
        return quant;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }


    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", price=" + price + ", quantity=" + quant + "]";
    }

}

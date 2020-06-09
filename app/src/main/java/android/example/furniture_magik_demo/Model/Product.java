package android.example.furniture_magik_demo.Model;

public class Product {
    private long id;
    private String name;
    private String type;
    private float price;
    private String image;

    public Product(String name, String type, float price, float discount_price, String image) {
        this.name = name;
        this.type = type;
        this.price = price;

        this.discount_price = discount_price;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(float discount_price) {
        this.discount_price = discount_price;
    }

    private float discount_price;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

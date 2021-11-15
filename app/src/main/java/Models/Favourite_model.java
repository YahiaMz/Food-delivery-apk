package Models;

public class Favourite_model {
    public int getId() {
        return id;
    }

    private int id;
    private  int image;
    private  String name;
    private  int price;


    public Favourite_model(int image, String name, int price , int product_id) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.id = product_id;
    }

    public int getImage() {
        return image;
    }
    public void setImage(int image) {
        this.image = image;
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
}

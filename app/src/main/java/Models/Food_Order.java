package Models;

public class Food_Order {
    private  String image;
    private String name;

    public Food_Order(String image, String name, int quantity) {
        this.image = image;
        this.name = name;
        this.quantity = quantity;
    }

    private  int quantity;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}

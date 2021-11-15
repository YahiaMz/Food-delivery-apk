package Models;

public class Cart_Item {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private  int id;
    private  String name;
    private  int price;
    private  String  description;
    private  String image;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private  int quantity;


    public Cart_Item(int id, String name, int price, String image, int quantity, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.quantity = quantity;
    }

   /* public Cart_Item(int id, String name, int price, String image, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
    }*/




    public Cart_Item(int id, String name, int price, String image ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.quantity = 1;
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

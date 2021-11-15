package Models;

import javax.xml.namespace.QName;

public class Food {
    public int getId() {
        return id;
    }

    private  int id;
    private  int category_id;
    private String name;
    private String image ;
    private  int price;

    public Food(int category_id, String name, String image, int price , int id) {
        this.category_id = category_id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.id = id;
    }




    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

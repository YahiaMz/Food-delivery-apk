package Models;

public class Category {
    private int id;
    private String name;
    private  int image;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public  Category( ) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Category(String name, int image) {
        this.name = name;
        this.image = image;
        this.id = 5;
    }

    public Category(int id, String name, int image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }
}

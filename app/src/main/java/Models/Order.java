package Models;

import Adapters.Food_order_item_Adapter;

public class Order {

     private  int id;
     private  String address;
     private  int total_price;
     private Food_order_item_Adapter food_order_adapter;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private  String time;



    public Order(String address, int total_price, Food_order_item_Adapter food_order_adapter) {
        this.address = address;
        this.total_price = total_price;
        this.food_order_adapter = food_order_adapter;
    }

    public Order(int id , String address, int total_price, Food_order_item_Adapter food_order_adapter , String time ) {
        this.id = id;

        this.address = address;
        this.total_price = total_price;
        this.food_order_adapter = food_order_adapter;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public Food_order_item_Adapter getFood_order_adapter() {
        return food_order_adapter;
    }

    public void setFood_order_adapter(Food_order_item_Adapter food_order_adapter) {
        this.food_order_adapter = food_order_adapter;
    }
}

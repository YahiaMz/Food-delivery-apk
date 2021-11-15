package com.aplication.dilevery_app;

public class HELPER {

    public  static  String       URL = "http://192.168.1.38:5000";
    public  static  String FOODS_URL = "http://192.168.1.38:5000/api/food/all";
    public  static String LOGIN = URL + "/api/user/login";
    public static final String ADD_TO_CART = URL+"/api/cart/add" ;
    public static final String CART_ITEMS = URL + "/api/cart/all/";
    public static final String CHANGE_QUANTITY_CART_ITEM = URL + "/api/cart/change_quantity";
    public static final String REMOVE_ITEM_FROM_CART =URL+"/api/cart/remove/" ;

}

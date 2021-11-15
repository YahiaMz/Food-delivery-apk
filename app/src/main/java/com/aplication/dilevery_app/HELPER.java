package com.aplication.dilevery_app;

public class HELPER {

    public  static  String URL = "http://192.168.43.32:5000";
    public  static  String FOODS_URL = URL + "/api/food/all";
    public  static String LOGIN = URL + "/api/user/login";
    public static final String ADD_TO_CART = URL+"/api/cart/add" ;
    public static final String CART_ITEMS = URL + "/api/cart/all/";
    public static final String CHANGE_QUANTITY_CART_ITEM = URL + "/api/cart/change_quantity";
    public static final String REMOVE_ITEM_FROM_CART =URL+"/api/cart/remove/" ;
    public static final String CREATE_ORDER_ITEM = URL + "/api/order_item/create";
    public static final String CREATE_ORDER = URL + "/api/order/create" ;
    public static final String DISABLE_ORDER = URL + "/api/order/disable/";
    public static final String ALL_ORDERS = URL + "/api/order/all/" ;
    public  static final  String FOOD_IMAGES = URL + "/images/foods/";
    public  static final  String CATEGORIES_IMAGES = URL + "/images/categories/";
    public static final String ORDER_GET_STATE = URL + "/api/order/get-State/" ;
    public static final String REGISTER = URL + "/api/user/register";


}

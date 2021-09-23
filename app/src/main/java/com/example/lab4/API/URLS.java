package com.example.lab4.API;

public class URLS {
    public static String BASE_URL = "http://192.168.50.144:8080";
    public static String SOCKET_BASE_URL = "192.168.50.144:8080";
    public static String SET_AVAILABILITY = BASE_URL + "/api/v1/chat";
    public static String TOKEN = "";
    public static String username = "";
    public static String MESSAGE_SEND_URL = BASE_URL + "/api/v1/chat/publish";
    //public static String MESSAGE_RECEIVE_URL = BASE_URL + "/api/v1/chat?username=" + URLS.username;
    //public static String ALL_MESSAGES_URL = BASE_URL + "/api/v1/chat?username=" + URLS.username;
    public static String ORDERS_URL = BASE_URL + "/api/v1/orders/";
    public static String LOGIN_URL = BASE_URL + "/login";
    public static String WEBSOCKET_URL = "ws://" + SOCKET_BASE_URL + "/websocket-chat/websocket";

    public static String getSubUrl(){
        return BASE_URL + "/api/v1/chat/subscribe?username=" + URLS.username + "&role=driver";
    }

    public static String getMessageReceiveUrl(){
        return BASE_URL + "/api/v1/chat?username=" + URLS.username;
    }
}

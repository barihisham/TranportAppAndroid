package com.example.lab4.Model.Order;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {

    public Order(OrderInfo pickUpOrder, OrderInfo deliveryOrder, String customerMessage, String internalInfo, List<Package> packageList) {
        this.pickUpOrder = pickUpOrder;
        this.deliveryOrder = deliveryOrder;
        this.customerMessage = customerMessage;
        this.internalInfo = internalInfo;
        this.packageList = packageList;
    }

    public String getToAddress(){
        return deliveryOrder.getAddress().getStreet() +  " " + deliveryOrder.getAddress().getStreetNumber();
    }

    public String getFromAddress(){
        return pickUpOrder.getAddress().getStreet() + " " + pickUpOrder.getAddress().getStreetNumber();
    }

    private String id;
    private OrderStatus orderStatus;
    private String customerUsername;
    private String orderPlaced;
    private OrderInfo pickUpOrder;
    private OrderInfo deliveryOrder;

    private String customerMessage;
    private String internalInfo;
    private String driverUsername;
    private List<Package> packageList;



}

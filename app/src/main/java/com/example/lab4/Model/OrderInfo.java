package com.example.lab4.Model;

import java.io.Serializable;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
public class OrderInfo implements Serializable {
    private String orderNr;
    private String from;
    private String to;
    private String date;
    private float weight;
    private boolean glass;

    public OrderInfo(String orderNr, String from, String to, String date, float weight, boolean glass) {
        this.orderNr = orderNr;
        this.from = from;
        this.to = to;
        this.date = date;
        this.weight = weight;
        this.glass = glass;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public boolean isGlass() {
        return glass;
    }

    public void setGlass(boolean glass) {
        this.glass = glass;
    }


    public String getOrderNr() {
        return orderNr;
    }

    public void setOrderNr(String orderNr) {
        this.orderNr = orderNr;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }


}

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
public class OrderInfo {

    private String name;
    private Address address;
    private List<String> phoneNumbers;
    private String earliestTime;
    private String latestTime;
    private String doorCode;

}

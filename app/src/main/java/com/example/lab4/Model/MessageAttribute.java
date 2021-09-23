package com.example.lab4.Model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MessageAttribute implements Serializable {

    private String sender, receiver , sendersText;


    private String time;

    public MessageAttribute(String senders_name, String sendersText, String time) {
        this.sender = senders_name;
        this.sendersText = sendersText;
        this.time = time;
    }

    public MessageAttribute(String senders_name, String receiver_name, String sendersText, String time) {
        this.sender = senders_name;
        this.receiver = receiver_name;
        this.sendersText = sendersText;
        this.time = time;
    }


}

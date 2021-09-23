package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import static com.example.lab4.MainActivity.buttonPosition;
import static com.example.lab4.MainActivity.orderList;

public class DetailsActivity extends AppCompatActivity {
    private TextView orderNr, date, from, to, isGlass, weight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        orderNr=findViewById(R.id.orderNr);
        date=findViewById(R.id.date);
        from=findViewById(R.id.from);
        to=findViewById(R.id.to);
        isGlass=findViewById(R.id.isGlass);
        weight=findViewById(R.id.weight);

        orderNr.setText("Order number: "+ orderList.get(buttonPosition).getOrderNr());
        date.setText("Date: "+orderList.get(buttonPosition).getDate());
        from.setText("From: "+orderList.get(buttonPosition).getFrom());
        to.setText("To: "+ orderList.get(buttonPosition).getTo());

        if(orderList.get(buttonPosition).isGlass()==false){
            isGlass.setText("Glass: No");
        }
        else{
            isGlass.setText("Glass: Yes");
        }
        weight.setText("Weight: " + orderList.get(buttonPosition).getWeight()+ " kg");
    }

}
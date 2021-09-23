package com.example.lab4;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab4.API.APIRequests;
import com.example.lab4.API.URLS;
import com.example.lab4.Model.OrderInfo;
import com.example.lab4.Model.Order.Order;
import com.example.lab4.Model.Order.OrderList;
import com.example.lab4.ConversationUI.ConversationsActivity;
import com.example.lab4.Service.LocationService;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity  {

    private ImageView messages;

    private String TAG = "DEBUG10";

    static RecyclerView orderRecyclerView;
    static Context mainActivityapplicationContext;
    static int buttonPosition;
    private OrderInfo orderInfo;
    private ImageView home;
    private ImageView archive;

    static List<OrderInfo> orderList;
    static List<OrderInfo> archiveList;
    private OrderAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: ");

        home=findViewById(R.id.home);
        messages=findViewById(R.id.messages);
        archive=findViewById(R.id.archive);
        orderList=new ArrayList<>();
        orderRecyclerView=findViewById(R.id.orderRecyclerView);
        archiveList=new ArrayList<>();

        orderInfo=new OrderInfo();
        buildRecyclerView();


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStart();
            }
        });
        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messagesActivity();
            }
        });
        archive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                archiveActivity();
            }
        });
    }

    public void getOrders(){
        orderList = new ArrayList<>();
        APIRequests.getOrders(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                Gson gson = new Gson();
                OrderList order = gson.fromJson(response.body().string(), OrderList.class);
                for(Order order1: order.getOrders()){
                    orderList.add(new OrderInfo(order1.getId(), order1.getFromAddress(), order1.getToAddress(), "2020-10-10", 123, true));
                }
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       setAdapter(orderList);
                        buildRecyclerView();
                    }
                });
            }
        });
    }
    public void setAdapter(List<OrderInfo> orderInfoList) {
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(mainActivityapplicationContext));
        adapter = new OrderAdapter(orderInfoList);
        orderRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getOrders();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent serviceIntent = new Intent(this, LocationService.class);
        stopService(serviceIntent);
    }

    public void detailsActivity(int position) {
        Intent intent=new Intent(this, DetailsActivity.class);

        startActivity(intent);
    }
    public void messagesActivity()
    {
        Intent intent=new Intent(this, ConversationsActivity.class);

        startActivity(intent);
    }
    public void archiveActivity(){
        Intent intent=new Intent(this, ArchiveActivity.class);

        startActivity(intent);
    }
    private void fromAddress(int position) {
        try {
            Uri uri= Uri.parse("https://www.google.co.in/maps/dir//"+orderList.get(position).getFrom());
            Intent intent =new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }catch (ActivityNotFoundException e){
            Uri uri=Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent intent=new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent );
        }
    }
    private void toAddress(int position) {
        try {
            Uri uri= Uri.parse("https://www.google.co.in/maps/dir//"+orderList.get(position).getTo());
            Intent intent =new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }catch (ActivityNotFoundException e){
            Uri uri=Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent intent=new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent );
        }
    }

    public void buildRecyclerView() {
        orderRecyclerView = findViewById(R.id.orderRecyclerView);
        orderRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        adapter = new OrderAdapter(orderList);
        orderRecyclerView.setLayoutManager(mLayoutManager);
        orderRecyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(new OrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClickTo(int position) {
                toAddress(position);
            }

            @Override
            public void onItemClickFrom(int position) {
                fromAddress(position);
            }

            @Override
            public void onItemClickDetails(int position) {
                buttonPosition=position;
                detailsActivity(position);
            }

            @Override
            public void onItemLongClick(int position) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Oreder number: "+orderList.get(position).getOrderNr());
                builder.setMessage("what do you want to do with this order");
                //Button One : Yes
                builder.setPositiveButton("Archive", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        archiveList.add(orderList.get(position));
                        orderList.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });

                //Button Two : No
                builder.setNegativeButton("Delate", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        orderList.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
                //Button Three : Neutral
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog diag = builder.create();
                diag.show();
            }
        });
    }
}

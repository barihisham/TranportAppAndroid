package com.example.lab4;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.lab4.Model.OrderInfo;
import com.example.lab4.ConversationUI.ConversationsActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.lab4.MainActivity.archiveList;
import static com.example.lab4.MainActivity.orderList;
import static com.example.lab4.MainActivity.buttonPosition;

public class ArchiveActivity extends AppCompatActivity {

    private ImageView messages;
   // private PrintOut printOut;
    private OrderInfo orderInfo;
    private ImageView home;
    private ImageView archive;
    private RecyclerView archiveRecyclerView;
    private List<OrderInfo> newArchiveList;

    private OrderAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);

        home=findViewById(R.id.home);
        messages=findViewById(R.id.messages);
        archive=findViewById(R.id.archive);
        newArchiveList=new ArrayList<>();
        archiveRecyclerView=findViewById(R.id.archiveRecyclerView);

        orderInfo=new OrderInfo();
        hardcode();
        buildRecyclerView();

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity();
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

    @Override
    protected void onStart() {
        super.onStart();
        buildRecyclerView();
    }
    public void hardcode(){
        for(int i=0;i<archiveList.size();i++){
            orderInfo=new OrderInfo();
            orderInfo.setOrderNr(archiveList.get(i).getOrderNr());
            orderInfo.setFrom(archiveList.get(i).getFrom());
            orderInfo.setTo(archiveList.get(i).getTo());
            orderInfo.setDate(archiveList.get(i).getDate());
            orderInfo.setWeight(archiveList.get(i).getWeight());
            orderInfo.setGlass(archiveList.get(i).isGlass());
            newArchiveList.add(orderInfo);
        }
            archiveRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            adapter = new OrderAdapter(newArchiveList);
        archiveRecyclerView.setAdapter(adapter);
        Log.d("new list size ", newArchiveList.size()+"");
        for(int i=0;i<newArchiveList.size();i++){
            Log.d("new list "+i, newArchiveList.get(i).getFrom()+"");

        }
    }

    public void buildRecyclerView() {
        archiveRecyclerView = findViewById(R.id.archiveRecyclerView);
        archiveRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        adapter = new OrderAdapter(newArchiveList);
        archiveRecyclerView.setLayoutManager(mLayoutManager);
        archiveRecyclerView.setAdapter(adapter);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(ArchiveActivity.this);
                builder.setTitle("Oreder number: "+newArchiveList.get(position).getOrderNr());
                builder.setMessage("what do you want to do with this order");
                builder.setPositiveButton("unarchive", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        orderList.add(newArchiveList.get(position));
                        newArchiveList.remove(position);
                        archiveList.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
                //Button Two : No
                builder.setNegativeButton("Delate", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        newArchiveList.remove(position);
                        archiveList.remove(position);
                        adapter.notifyDataSetChanged();
                        //dialog.cancel();
                    }
                });
                //Button Three : Neutral
                builder.setNeutralButton("Cansel", new DialogInterface.OnClickListener() {
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
    public void mainActivity(){
        Intent intent=new Intent(this, MainActivity.class);

        startActivity(intent);
    }

    private void fromAddress(int position) {
        try {
            //when google map is instulled
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
            //when google map is instulled
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
}

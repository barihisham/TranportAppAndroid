package com.example.lab4.LoginUI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lab4.API.APIRequests;
import com.example.lab4.API.URLS;
import com.example.lab4.MainActivity;
import com.example.lab4.R;
import com.example.lab4.Service.LocationService;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private TextView username;
    private TextView password;
    private TextView wrongUP;
    private String TAG = "DEBUG12";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        wrongUP = findViewById(R.id.wrongup);

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                APIRequests.login(username.getText().toString(), password.getText().toString(), new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if(response.code() == 200){
                            String auth2 = response.header("Authorization");
                            if(auth2 != null){
                                String token = auth2;
                                URLS.username = username.getText().toString();
                                URLS.TOKEN = token;
                                next();
                            }
                        }else{
                            LoginActivity.this.runOnUiThread(() -> wrongUP.setVisibility(View.VISIBLE));
                        }
                    }
                });
            }
        });
    }

    public void next(){

        APIRequests.setAvailability(true);
        ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        Intent serviceIntent = new Intent(this, LocationService.class);
        startService(serviceIntent);

        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}

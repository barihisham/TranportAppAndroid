package com.example.lab4.API;

import android.util.Log;

import com.example.lab4.API.URLS;
import com.here.oksse.OkSse;
import com.here.oksse.ServerSentEvent;

import okhttp3.Request;
import okhttp3.Response;

public class Chat {
    private Request request;
    private OkSse okSse;
    public Chat(ServerSentEvent.Listener listener) {
        request = new Request.Builder().url(URLS.getSubUrl())
                .addHeader("Authorization", URLS.TOKEN)
                .build();
        okSse = new OkSse();
        okSse.newServerSentEvent(request, listener);
    }
}

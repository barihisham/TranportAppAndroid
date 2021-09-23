package com.example.lab4.ConversationUI;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab4.API.URLS;
import com.example.lab4.Model.MessageAttribute;
import com.example.lab4.R;

import java.util.List;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ConversationViewHolder> {

    private List<MessageAttribute> messagesList;
    private static String TAG = "DEBUG14";
    private View view;
    private ConversationAdapter.OnItemClickListener mListener;

    public ConversationAdapter(List<MessageAttribute> messagesList) {
        this.messagesList = messagesList;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public ConversationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.conversation, parent, false);



        ConversationViewHolder cvh = new ConversationViewHolder(view, mListener);

        return cvh;
    }

    public void updateOne(MessageAttribute messageAttribute) {
        this.messagesList.add(messageAttribute);
        notifyItemInserted(messagesList.size());
    }

    public void updateData(List<MessageAttribute> messageAttributes){
        this.messagesList = messageAttributes;
        Log.d(TAG, "updateData: " + messagesList);
        notifyDataSetChanged();
    }

    public List<MessageAttribute> getMessagesList() {
        return messagesList;
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationViewHolder holder, int position) {

        MessageAttribute message =messagesList.get(position);

        holder.sendersName.setText(message.getSender());
        holder.sendersText.setText(message.getSendersText());
        holder.time.setText(message.getTime()+"");
        if(URLS.username.equals(message.getSender())){
            holder.relativeLayout.setBackgroundColor(Color.parseColor("#1b9cf7"));
        }else{
            holder.relativeLayout.setBackgroundColor(Color.parseColor("#e3d346"));
        }
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    public interface OnItemClickListener {
    }

    public static class ConversationViewHolder extends RecyclerView.ViewHolder {
        static TextView sendersName, sendersText, time;
        static RelativeLayout relativeLayout;
        public ConversationViewHolder(@NonNull View itemView,final ConversationAdapter.OnItemClickListener listener) {
            super(itemView);
            sendersName=itemView.findViewById(R.id.sender);
            sendersText=itemView.findViewById(R.id.sendersText);
            time=itemView.findViewById(R.id.time);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);

        }
    }
}

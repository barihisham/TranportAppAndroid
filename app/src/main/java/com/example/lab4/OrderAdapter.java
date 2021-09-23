package com.example.lab4;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab4.Model.OrderInfo;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    LayoutInflater inflater;
    private List<OrderInfo> orderList;
    private View view;
    private OnItemClickListener mListener;
    public interface OnItemClickListener{
        void onItemClickTo(int position);

        void onItemClickFrom(int position);

        void onItemClickDetails(int position);

        void onItemLongClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=  listener;
    }

    public OrderAdapter( List<OrderInfo> orderList) {
        //inflater=LayoutInflater.from(c);
        this.orderList=orderList;

    }

    @NonNull
    @Override
    public OrderAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view=LayoutInflater.from(parent.getContext()).inflate(R.layout.order, parent, false);

        OrderViewHolder evh = new OrderViewHolder(view, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderInfo order =orderList.get(position);
        holder.orderNr.setText("Order number: "+order.getOrderNr());
        holder.from.setText("From: "+order.getFrom());
        holder.to.setText("To: "+ order.getTo());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {


        TextView orderNr, from, to;
        ImageView fromMap, toMap;
        Button detailsButton;
        public  OrderViewHolder(@NonNull View view,final OnItemClickListener listener) {
            super(view);
            orderNr =view.findViewById(R.id.orderNr);
            from=view.findViewById(R.id.from);
            to=view.findViewById(R.id.to);

            fromMap=view.findViewById(R.id.googleMaps_icon_from);
            toMap=view.findViewById(R.id.googleMaps_icon_to);
            detailsButton=view.findViewById(R.id.detailsButton);


            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemLongClick(position);
                        }
                    }
                    return false;
                }
            });
            fromMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClickFrom(position);
                        }
                    }
                }
            });
            toMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClickTo(position);
                            //Log.d("clicked", "___________");
                        }
                    }
                }
            });
            detailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClickDetails(position);
                        }
                    }
                }
            });
        }
    }

}

package com.example.demoseminar.Orders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.demoseminar.R;
import com.google.firebase.database.DatabaseReference;

import java.text.DecimalFormat;
import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder> {
    DecimalFormat format = new DecimalFormat("###,###,###");
    private List<DetailOrder> detailOrderList;
    public  void setData(List<DetailOrder> list){
        this.detailOrderList = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_oders,parent,false);
       return new OrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {
        DetailOrder detailOrder  = detailOrderList.get(position);
        if(detailOrder == null) {
            return;
        }
        else {
            Glide.with(holder.imageVieworders.getContext()).load(detailOrder.getHinhanh()).into(holder.imageVieworders);
            holder.tvten.setText(detailOrder.getTensp());
            holder.tvsoluong.setText(String.valueOf(detailOrder.getSoluong()));
            holder.tvgia.setText(format.format(detailOrder.getGiasp()));
            holder.tvtrangthai.setText(detailOrder.getTrangthai());
        }

    }

    @Override
    public int getItemCount() {
        if(detailOrderList.size() != 0){
            return detailOrderList.size();
        }
        else
            return 0;
    }

    public class OrdersViewHolder extends RecyclerView.ViewHolder {
        ImageView imageVieworders;
        TextView tvten , tvsoluong, tvgia, tvtrangthai;
        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            imageVieworders = itemView.findViewById(R.id.imagesp_orders);
            tvten = itemView.findViewById(R.id.tvtenspdongorder);
            tvgia = itemView.findViewById(R.id.tvgiaspdongorder);
            tvsoluong = itemView.findViewById(R.id.tvslspdongorder);
            tvtrangthai = itemView.findViewById(R.id.tvtrangthaidongorder);
        }
    }
}

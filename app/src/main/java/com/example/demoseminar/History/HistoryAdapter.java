package com.example.demoseminar.History;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.demoseminar.MainActivity;
import com.example.demoseminar.Orders.DetailOrder;
import com.example.demoseminar.Orders.Order;
import com.example.demoseminar.R;

import java.text.DecimalFormat;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    List<DetailOrder> detailOrderList;
    List<Order> orderList;
    DecimalFormat format = new DecimalFormat("###,###,###");
    private Order orderf;
    private MainActivity home;
    public void setData(List<DetailOrder> list, List<Order> orders, MainActivity home){
       this.detailOrderList = list;
       this.orderList = orders;
       this.home = home;
       notifyDataSetChanged();
    }
    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_giaodich,parent,false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        DetailOrder detailOrder = detailOrderList.get(position);
        if(detailOrder == null){
            return;
        }
        else {
            Glide.with(holder.imageViewhistory.getContext()).load(detailOrder.getHinhanh()).into(holder.imageViewhistory);
            holder.tvmadon.setText(detailOrder.getMadon());
            holder.tvtensp.setText(detailOrder.getTensp());
            holder.tvsoluongsp.setText(String.valueOf(detailOrder.getSoluong()));
            holder.tvgiasp.setText(format.format(detailOrder.getGiasp()));
            holder.tvtrangthai.setText(detailOrder.getTrangthai());
            for(Order order : orderList){
                if(order.getMadon().equals(detailOrder.getMadon())){
                    holder.tvngaydat.setText(order.getNgaydat());
                    break;
                }
            }
            holder.tvmadon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(Order order : orderList){
                        if(order.getMadon().equals(detailOrder.getMadon())){
                            orderf = order;
                            break;
                        }
                    }
                    for (DetailOrder detailOrder1 : detailOrderList){
                        if(detailOrder.getMadon().equals(detailOrder1.getMadon())){
                            orderf.addListDetailOrder(detailOrder1);
                        }
                    }
                    home.tranOrders(orderf);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(detailOrderList.size() != 0){
            return detailOrderList.size();
        }
        return 0;
    }

    public class HistoryViewHolder  extends RecyclerView.ViewHolder {
        ImageView imageViewhistory;
        TextView tvtensp, tvsoluongsp,tvgiasp,tvngaydat,tvtrangthai,tvmadon;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewhistory = itemView.findViewById(R.id.imagesp_giaodich);
            tvtensp = itemView.findViewById(R.id.tvtenspdonggiaodich);
            tvsoluongsp = itemView.findViewById(R.id.tvslspdonggiaodich);
            tvgiasp = itemView.findViewById(R.id.tvgiaspdonggiaodich);
            tvngaydat = itemView.findViewById(R.id.tvngaydatdonggiaodich);
            tvtrangthai = itemView.findViewById(R.id.tvtrangthaidonggiaodich);
            tvmadon = itemView.findViewById(R.id.tvmadon_donggiaodich);

        }
    }
}

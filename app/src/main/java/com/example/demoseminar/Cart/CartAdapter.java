package com.example.demoseminar.Cart;

import android.icu.lang.UProperty;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.demoseminar.MainActivity;
import com.example.demoseminar.Product.Product;
import com.example.demoseminar.R;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private DecimalFormat format = new DecimalFormat("###,###,###");
    private List<Product> cartlist;
    private int sl;
    private MainActivity home;
    private CartFragment cartFragment;
    public void setData(List<Product> cartlists,MainActivity homes,CartFragment cartFragments){
        this.cartlist = cartlists;
        this.home = homes;
        this.cartFragment = cartFragments;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_giohang,parent,false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = cartlist.get(position);
        if(product == null){
            return;
        }else {
            Glide.with(holder.imageViewcart.getContext()).load(product.getHinhanh()).into(holder.imageViewcart);
            holder.tvtenspcart.setText(product.getTensp());
            holder.tvgiaspcart.setText("Giá: " + format.format(product.getGiasp()) + " Đ");
            int count = product.getSlsp();
            if(count != 0) {
                holder.tvsl.setText(String.valueOf(count));
            }else {
                holder.tvsl.setText(String.valueOf(1));
            }
            holder.imageminus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sl = Integer.parseInt(holder.tvsl.getText().toString());
                    if(sl > 1){
                        sl--;
                        home.setCountProductCart(home.getCount() - 1);
                        home.setCountForProduct(position,sl);
                        cartFragment.setCountForProduct(position,sl);
                        holder.tvsl.setText(String.valueOf(sl));
                        cartFragment.setTongtien(0,1,product.getGiasp());
                        notifyDataSetChanged();
                    }
                }
            });
            holder.imageplus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sl = Integer.parseInt(holder.tvsl.getText().toString());
                    if(sl < 10){
                        sl++;
                        home.setCountProductCart(home.getCount() + 1);
                        home.setCountForProduct(position,sl);
                        cartFragment.setCountForProduct(position,sl);
                        holder.tvsl.setText(String.valueOf(sl));
                        cartFragment.setTongtien(1,1,product.getGiasp());
                        notifyDataSetChanged();
                    }
                }
            });
            holder.imageremove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sl = Integer.parseInt(holder.tvsl.getText().toString());
                    home.setCountProductCart(home.getCount() - sl);
                    cartFragment.setTongtien(0,sl,product.getGiasp());
                    cartlist.remove(position);
                    if(cartlist.size() == 0){
                        cartFragment.setVisibilityEmpty();
                    }
                    notifyDataSetChanged();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if(cartlist != null){
            return cartlist.size();
        }
            return 0;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewcart ;
        TextView tvtenspcart , tvgiaspcart, tvsl;
        ImageView imageminus , imageplus, imageremove;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewcart = itemView.findViewById(R.id.imagesp_cart);
            tvtenspcart = itemView.findViewById(R.id.tvtensp_cart);
            tvgiaspcart = itemView.findViewById(R.id.tvgiasp_cart);
            tvsl = itemView.findViewById(R.id.tvsl_cart);
            imageminus = itemView.findViewById(R.id.minus_sp);
            imageplus = itemView.findViewById(R.id.plus_sp);
            imageremove = itemView.findViewById(R.id.remove_cart);
        }
    }
}

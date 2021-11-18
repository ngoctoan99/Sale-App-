package com.example.demoseminar.Product;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.demoseminar.MainActivity;
import com.example.demoseminar.R;

import java.text.DecimalFormat;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    DecimalFormat format = new DecimalFormat("###,###,###");
    private List<Product> productList;
    private MainActivity main;
    public interface IClickItemProductListener{
        void onClickItemProduct(Product product);
    }
    public void setData(List<Product> lists, MainActivity main){
        this.productList = lists;
        this.main = main;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanpham,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        if(product == null){
            return;
        }else {
            Glide.with(holder.imageViewsp.getContext()).load(product.getHinhanh()).into(holder.imageViewsp);
            holder.txttensp.setText(product.getTensp());
            holder.txtgiasp.setText("Giá: "+ format.format(product.getGiasp()) + " Đ");
            holder.setItemClickListener(new IClickItemProductListener() {
                @Override
                public void onClickItemProduct(Product product) {
                    main.tranDetailProduct(product);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(productList != null){
            return productList.size();
        }
        else
            return 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageViewsp;
        TextView txttensp, txtgiasp;
        IClickItemProductListener iClickItemProductListener;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewsp = itemView.findViewById(R.id.imagesp);
            txttensp = itemView.findViewById(R.id.tv_tensp);
            txtgiasp = itemView.findViewById(R.id.tv_giasp);
            itemView.setOnClickListener(this);
        }
        public void setItemClickListener(IClickItemProductListener itemClickListener){
            this.iClickItemProductListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            this.iClickItemProductListener.onClickItemProduct(productList.get(getAdapterPosition()));
        }
    }
}

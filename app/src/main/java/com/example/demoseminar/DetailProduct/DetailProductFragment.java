package com.example.demoseminar.DetailProduct;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.demoseminar.MainActivity;
import com.example.demoseminar.Product.Product;
import com.example.demoseminar.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DetailProductFragment extends Fragment {
    View view;
    private DecimalFormat format = new DecimalFormat("###,###,###");
    private MainActivity home;
    private boolean dathem;
    private Product detailsp;
    private TextView tvtendetail, tvgiadetail, tvmotadetail;
    private Button btnthem;
    private List<Product> productListcart;
    private ImageView imagedetail;

    public DetailProductFragment() {
    }
    public DetailProductFragment(Product product , List<Product> productListcart){
        this.productListcart = productListcart;
        this.detailsp = product;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detail_product, container, false);
        inits();
        setValue();
        return view;
    }

    private void setValue() {
        if (detailsp != null) {
            tvtendetail.setText(detailsp.getTensp());
            tvgiadetail.setText(format.format(detailsp.getGiasp()) + " Đ");
            tvmotadetail.setText(detailsp.getMota());
            Glide.with(getContext()).load(detailsp.getHinhanh()).into(imagedetail);
            for(int i = 0 ; i < productListcart.size(); i++) {
                if(productListcart.get(i).getTensp().equals(detailsp.getTensp())){
                    dathem = true;
                    btnthem.setText("Đã Mua");
                    btnthem.setBackgroundResource(R.drawable.buttonclick);
                    break;
                }
            }
            btnthem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(dathem){
                        Toast.makeText(getActivity(), "Sản phẩm đã được thêm", Toast.LENGTH_SHORT).show();
                    }else {
                        dathem = true;
                        btnthem.setText("Đã Mua");
                        btnthem.setBackgroundResource(R.drawable.buttonclick);
                        home.addTolistCartProduct(detailsp);
                        home.setCountProductCart(home.getCount() + 1);
                        Toast.makeText(getActivity(),"Đã thêm sản phẩm. Kiểm tra lại  giỏ hàng",Toast.LENGTH_SHORT);

                    }
                }
            });
        }
    }

        private void inits () {
            dathem = false;
            home = (MainActivity) getActivity();
            tvtendetail = view.findViewById(R.id.tvten_detail);
            tvgiadetail = view.findViewById(R.id.tvgia_detail);
            tvmotadetail = view.findViewById(R.id.tvmota_detail);
            imagedetail = view.findViewById(R.id.imagesp_detail);
            btnthem = view.findViewById(R.id.btnaddsp);
            if(productListcart == null){
                productListcart = new ArrayList<>();
            }
        }
    }
package com.example.demoseminar.Cart;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demoseminar.MainActivity;
import com.example.demoseminar.Orders.DetailOrder;
import com.example.demoseminar.Product.Product;
import com.example.demoseminar.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartFragment extends Fragment {
    private int tongtien;
    private View view;
    private DecimalFormat format;
    private MainActivity home;
    private RelativeLayout cartEmpty , cartsp;
    private List<Product> productListcart;
    private RecyclerView recyclerViewcart;
    private TextView tvtongtien;
    private EditText editten, editdiachi, editsdt;
    private Button btnmua;
    private CartAdapter cartAdapter;
    public  CartFragment(List<Product> productListcart){
        this.productListcart = productListcart;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cart,container,false);
        initss();
        setVisibilityView();
        return view;
    }

    public void setVisibilityView() {
        if(productListcart.size() == 0){
            setVisibilityEmpty();
        }
        else {
            setVisibilityCart();
            setDataProductCartAdapter();
        }
    }

    public void setDataProductCartAdapter() {
        cartAdapter.setData(productListcart,home,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(home,RecyclerView.VERTICAL,false);
        recyclerViewcart.setLayoutManager(linearLayoutManager);
        recyclerViewcart.setAdapter(cartAdapter);

    }

    public void setVisibilityCart() {
        cartEmpty.setVisibility(View.GONE);
        cartsp.setVisibility(View.VISIBLE);
        String total = format.format(getTongtien());
        tvtongtien.setText(total + " Đ");
    }

    public int getTongtien() {
        for(Product product : productListcart){
            int giasps = product.getGiasp();
            tongtien = tongtien + giasps * product.getSlsp();
        }
        return tongtien;
    }
    public void setTongtien(int mode, int count, int giasp){
        if(mode == 0){
            tongtien = tongtien - giasp * count;
        }else if(mode == 1 ) {
            tongtien = tongtien + giasp * count;
        }
        tvtongtien.setText(format.format(tongtien) + " Đ");
    }

    public void setVisibilityEmpty() {
        cartEmpty.setVisibility(View.VISIBLE);
        cartsp.setVisibility(View.GONE);

    }

    private void initss() {
        cartAdapter = new CartAdapter();
        cartEmpty = view.findViewById(R.id.cart_emplty);
        cartsp = view.findViewById(R.id.nen_cart);
        recyclerViewcart = view.findViewById(R.id.rc_dscart);
        tvtongtien = view.findViewById(R.id.tongtienhang);
        editten = view.findViewById(R.id.tvtenkh);
        editdiachi = view.findViewById(R.id.tvdiachikh);
        editsdt = view.findViewById(R.id.tvsdt);
        btnmua = view.findViewById(R.id.btnxnmua);
        btnmua.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                addOrder();
            }
        });
        home = (MainActivity) getActivity();
        format = new DecimalFormat("###,###,###");
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addOrder() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Order");
        Map<String,Object> map = new HashMap<>();
        Date date = new Date(System.currentTimeMillis());
        map.put("ngaydat",date.toString());
        map.put("tenkh",editten.getText().toString());
        map.put("diachikh",editdiachi.getText().toString());
        map.put("sdtkh",editsdt.getText().toString());
        int num = 0;
        for (Product product : productListcart){
            num = num + product.getSlsp();
        }
        map.put("soluong",num);
        map.put("tongtien",tongtien);
        map.put("trangthai","Đã gửi yêu cầu");
        String key = databaseReference.push().getKey();
        databaseReference.child(key).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                List<DetailOrder> detailOrderList = makeDetailOrder(key);
                for(DetailOrder detailOrder : detailOrderList){
                    databaseReference.child(key).child("chitiet").child(databaseReference.push().getKey()).setValue(detailOrder).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getContext(),"Mua hàng thành công !!",Toast.LENGTH_SHORT).show();
                            productListcart.clear();
                            setVisibilityEmpty();
                            home.setCountProductCart(0);
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"Mua hàng thất bại. Mời thử lại",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<DetailOrder> makeDetailOrder(String key) {
        List<DetailOrder> detailOrderList = new ArrayList<>();
        for(Product product : home.getListCart()){
            DetailOrder detailOrder = new DetailOrder();
            detailOrder.setMadon(key);
            detailOrder.setTensp(product.getTensp());
            detailOrder.setGiasp(product.getGiasp());
            detailOrder.setHinhanh(product.getHinhanh());
            detailOrder.setSoluong(product.getSlsp());
            detailOrder.setTrangthai("Đã gửi yêu cầu");
            detailOrderList.add(detailOrder);
        }
        return  detailOrderList;
    }

    public void setCountForProduct(int position, int count){
        productListcart.get(position).setSlsp(count);
    }
}
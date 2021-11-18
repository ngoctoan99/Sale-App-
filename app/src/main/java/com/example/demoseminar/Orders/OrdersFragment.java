package com.example.demoseminar.Orders;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.demoseminar.MainActivity;
import com.example.demoseminar.R;

import java.text.DecimalFormat;

public class OrdersFragment extends Fragment {
    View view;
    DecimalFormat format = new DecimalFormat("###,###,###");
    private Order order;
    public static final String TAG = OrdersFragment.class.getName();
    private MainActivity home;
    private OrdersAdapter ordersAdapter;
    private Button btntrove;
    private TextView tvmadon , tvngaydat,tvtenkh,tvdiachi,tvsdt,tvslsp,tvtongtien,tvtrangthai;
    private RecyclerView recyclerView2;

    public OrdersFragment(Order order) {
        this.order = order;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_orders, container, false);
        inits();
        setInOrder();
        setDataAdapter();
        return view;
    }

    private void setDataAdapter() {
        ordersAdapter.setData(order.getDetailOrderList());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(home,RecyclerView.VERTICAL,false);
        recyclerView2.setLayoutManager(linearLayoutManager);
        recyclerView2.setAdapter(ordersAdapter);
    }

    private void setInOrder() {
        tvmadon.setText(order.getMadon().toUpperCase());
        tvtrangthai.setText(order.getTrangthai());
        tvtongtien.setText(format.format(order.getTongtien()) + " Đ");
        tvslsp.setText(String.valueOf(order.getSoluong()));
        tvsdt.setText(order.getSdtkh());
        tvdiachi.setText(order.getDiachikh());
        tvngaydat.setText(order.getNgaydat());
        tvtenkh.setText(order.getTenkh());
    }

    private void inits() {
        recyclerView2 = view.findViewById(R.id.rcv_listorder);
        ordersAdapter = new OrdersAdapter();
        home = (MainActivity) getActivity();
        tvmadon = view.findViewById(R.id.tvmadonod);
        tvngaydat = view.findViewById(R.id.tvngaydatod);
        tvtenkh = view.findViewById(R.id.tvtenorder);
        tvdiachi = view.findViewById(R.id.tvdiachiorder);
        tvsdt = view.findViewById(R.id.tvsdtorder);
        tvslsp = view.findViewById(R.id.tvtongsporder);
        tvtongtien = view.findViewById(R.id.tvtongtienorder);
        tvtrangthai = view.findViewById(R.id.tvtrangthaiorder);
        btntrove = view.findViewById(R.id.btnback);
        btntrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getFragmentManager() != null){
                    getFragmentManager().popBackStack();
                }
            }
        });

    }
}
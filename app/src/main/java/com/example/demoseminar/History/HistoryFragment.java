package com.example.demoseminar.History;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.demoseminar.MainActivity;
import com.example.demoseminar.Orders.DetailOrder;
import com.example.demoseminar.Orders.Order;
import com.example.demoseminar.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {
    View view;
    private MainActivity home;
    private List<Order> orderList;
    private List<DetailOrder> detailOrderList;
    EditText editText;
    Button btntimkiem;
    RecyclerView recyclerView3;
    private HistoryAdapter historyAdapter;

    public HistoryFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!editText.getText().toString().trim().isEmpty()){
            findOrder();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_history, container, false);
        inits();
        return view;
    }

    private void inits() {
        orderList = new ArrayList<>();
        detailOrderList = new ArrayList<>();
        historyAdapter = new HistoryAdapter();
        home = (MainActivity) getActivity();
        editText = view.findViewById(R.id.editgiaodich);
        recyclerView3 = view.findViewById(R.id.rc_history);
        btntimkiem = view.findViewById(R.id.btnlichsugiaodich);
        btntimkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findOrder();
            }
        });
    }

    private void findOrder() {
        orderList.clear();
        detailOrderList.clear();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Order");

        databaseReference.orderByChild("sdtkh").equalTo(editText.getText().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                historyAdapter.notifyDataSetChanged();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Order order = dataSnapshot.getValue(Order.class);
                    order.setMadon(dataSnapshot.getKey());
                    orderList.add(order);
                }
                if(orderList.size() > 0){
                    findDetail(databaseReference);
                }else {
                    Toast.makeText(getContext(),"Không có lịch sử giao dịch",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Không lấy được dữ liêu",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void findDetail(DatabaseReference databaseReference) {
        if(orderList.size() > 0){
            for(int i =0 ; i < orderList.size(); i++){
                Order order = orderList.get(i);
                databaseReference.child(order.getMadon()).child("chitiet").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            historyAdapter.notifyDataSetChanged();
                            DetailOrder detailOrder = dataSnapshot.getValue(DetailOrder.class);
                            detailOrderList.add(detailOrder);
                        }
                        if(detailOrderList.size() > 0){
                            setHistoryAdapter();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(),"Không lấy được đơn hàng",Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }
    }

    private void setHistoryAdapter() {
        historyAdapter.setData(detailOrderList,orderList,home);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(home,RecyclerView.VERTICAL,false);
        recyclerView3.setLayoutManager(linearLayoutManager);
        recyclerView3.setAdapter(historyAdapter);
    }
}
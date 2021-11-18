package com.example.demoseminar.Product;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.demoseminar.MainActivity;
import com.example.demoseminar.R;
import com.example.demoseminar.QC.SlidePhoto;
import com.example.demoseminar.QC.SlidePhotoAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class ProductFragment extends Fragment {
   View view;
   RecyclerView recyclerViewsp;
   ViewPager viewPager;
   CircleIndicator circleIndicator;
   AutoCompleteTextView searchsp;
   ProductAdapter productAdapter;
   List<Product> allproductList;
   List<SlidePhoto> allslidePhotoList;
   SlidePhotoAdapter slidePhotoAdapter;
   MainActivity home;
   Timer timer;

    public ProductFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_product, container,false);
        inits();
        setDataSlidePhotoAdapter();
        setDataProductAdapter();
        return view;
    }

    private void setDataSearchAdapter(List<Product> list) {
        SearchAdapter searchAdapter = new SearchAdapter(home,R.layout.dong_timkiem,list);
        searchsp.setAdapter(searchAdapter);
        searchsp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                home.tranDetailProduct(list.get(position));
            }
        });
    }

    private void setDataProductAdapter() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(home,2);
        recyclerViewsp.setLayoutManager(gridLayoutManager);
        productAdapter = new ProductAdapter();
        productAdapter.setData(allproductList,home);
        recyclerViewsp.setAdapter(productAdapter);
    }

    private void setDataSlidePhotoAdapter() {
        slidePhotoAdapter = new SlidePhotoAdapter(allslidePhotoList,this);
        viewPager.setAdapter(slidePhotoAdapter);
        circleIndicator.setViewPager(viewPager);
        slidePhotoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        autoTransation();
    }

    private void autoTransation() {
        if(allslidePhotoList == null || allslidePhotoList.isEmpty()||viewPager == null){
            return;
        }
        if(timer == null){
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = viewPager.getCurrentItem();
                        int totalItem = allproductList.size() - 1;
                        if(currentItem < totalItem){
                            currentItem++;
                            viewPager.setCurrentItem(currentItem);
                        }else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        },500,3000);
    }

    private void inits() {
        recyclerViewsp = view.findViewById(R.id.dssp);
        viewPager = view.findViewById(R.id.vp_photo);
        circleIndicator = view.findViewById(R.id.circle);
        searchsp = view.findViewById(R.id.search);
        searchsp.setInputType(InputType.TYPE_NULL);
        allproductList = getDataProduct();
        allslidePhotoList = getListSlidePhoto();
        home = (MainActivity) getActivity();
    }

    private List<Product> getDataProduct() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("DbProduct");
        List<Product> list = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productAdapter.notifyDataSetChanged();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Product product = dataSnapshot.getValue(Product.class);
                    product.setId(dataSnapshot.getKey());
                    list.add(product);
                }
                setDataSearchAdapter(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(),"Không tải được dữ liệu" + error.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        return list;
    }

    private List<SlidePhoto> getListSlidePhoto() {
        List<SlidePhoto> list = new ArrayList<>();
        list.add(new SlidePhoto(R.drawable.slide1));
        list.add(new SlidePhoto(R.drawable.slide2));
        list.add(new SlidePhoto(R.drawable.slide3));
        return list;
    }

}
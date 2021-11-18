package com.example.demoseminar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toolbar;
import android.widget.ViewFlipper;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.example.demoseminar.Cart.CartFragment;
import com.example.demoseminar.DetailProduct.DetailProductFragment;
import com.example.demoseminar.History.HistoryFragment;
import com.example.demoseminar.Orders.Order;
import com.example.demoseminar.Orders.OrdersFragment;
import com.example.demoseminar.Product.Product;
import com.example.demoseminar.Product.ProductFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Product> carttList;
    private AHBottomNavigation ahBottomNavigation;
    FragmentTransaction fragmentTransaction;
    private int sl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inits();
        setDataforahbottom();
    }

    private void setDataforahbottom() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Sản phẩm",R.drawable.home,R.color.teal_200);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Giỏ hàng",R.drawable.cart,R.color.purple_200);
        AHBottomNavigationItem item3= new AHBottomNavigationItem("Giao dịch",R.drawable.history,R.color.purple_500);
        ahBottomNavigation.addItem(item1);
        ahBottomNavigation.addItem(item2);
        ahBottomNavigation.addItem(item3);
        ahBottomNavigation.setColored(false);
        ahBottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.white));
        ahBottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position){
                    case 0:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.content_fragment,new ProductFragment());
                        fragmentTransaction.commit();
                        break;
                    case 1:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.content_fragment,new CartFragment(carttList));
                        fragmentTransaction.commit();
                        break;
                    case 2:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.content_fragment,new HistoryFragment());
                        fragmentTransaction.commit();
                        break;
                }
                return true;
            }
        });

    }

    private void inits() {
        ahBottomNavigation = findViewById(R.id.ahnav_main);
        if(carttList == null) {
            carttList = new ArrayList<>();
        }
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_fragment,new ProductFragment());
        fragmentTransaction.commit();
    }
    public void tranDetailProduct(Product product){
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_fragment, new DetailProductFragment(product,carttList));
        fragmentTransaction.commit();
    }
    public void tranOrders(Order order){
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_fragment,new OrdersFragment(order));
        fragmentTransaction.addToBackStack(OrdersFragment.TAG);
        fragmentTransaction.commit();
    }
    public void setCountProductCart(int count){
        sl = count ;
        AHNotification notification = new AHNotification.Builder()
                .setText(String.valueOf(count))
                .setBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.purple_700))
                .setTextColor(ContextCompat.getColor(MainActivity.this,R.color.white))
                .build();
        ahBottomNavigation.setNotification(notification,1);
    }
    public void addTolistCartProduct(Product product){
        carttList.add(product);
    }
    public List<Product> getListCart(){
        return carttList;
    }
    public int getCount(){
        return sl;
    }
    public void setCountForProduct(int position, int count){
        carttList.get(position).setSlsp(count);
    }

}
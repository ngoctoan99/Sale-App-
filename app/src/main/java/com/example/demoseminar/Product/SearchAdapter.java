package com.example.demoseminar.Product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.demoseminar.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends ArrayAdapter<Product> {
    private DecimalFormat format = new DecimalFormat("###,###,###");
    private List<Product> searchList;

    public SearchAdapter(@NonNull Context context, int resource, @NonNull List<Product> objects) {
        super(context, resource, objects);
        searchList = new ArrayList<>(objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_timkiem,parent,false);
        }
        ImageView imagesearch = convertView.findViewById(R.id.image_search);
        TextView tvname = convertView.findViewById(R.id.tv_tensearch);
        TextView tvgia = convertView.findViewById(R.id.tv_giasearch);
        Product product = getItem(position);
        Glide.with(imagesearch.getContext()).load(product.getHinhanh()).into(imagesearch);
        tvname.setText(product.getTensp());
        tvgia.setText("Giá: " + format.format(product.getGiasp()) + " Đ");
        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Product> list = new ArrayList<>();
                if(constraint == null || constraint.length() == 0){
                    list.addAll(searchList);
                }
                else {
                    String filter = constraint.toString().toLowerCase().trim();
                    for(Product sp : searchList){
                        if(sp.getTensp().toLowerCase().contains(filter)){
                            list.add(sp);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = list;
                filterResults.count = list.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clear();
                addAll((List<Product>)results.values);
                notifyDataSetInvalidated();
            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                return ((Product)resultValue).getTensp();
            }
        };
    }
}

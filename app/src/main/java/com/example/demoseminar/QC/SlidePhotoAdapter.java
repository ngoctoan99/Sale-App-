package com.example.demoseminar.QC;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.demoseminar.QC.SlidePhoto;
import com.example.demoseminar.R;

import java.util.List;

public class SlidePhotoAdapter extends PagerAdapter {
    private List<SlidePhoto> slidePhotoList;
    private Fragment fragment;

    public SlidePhotoAdapter(List<SlidePhoto> slidePhotoList, Fragment fragment) {
        this.slidePhotoList = slidePhotoList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.dong_quangcao,null);
        ImageView imageView = view.findViewById(R.id.img_slide_photo);
        SlidePhoto slidePhoto = slidePhotoList.get(position);
        if(slidePhotoList != null){
            Glide.with(fragment).load(slidePhoto.getResourceid()).into(imageView);
        }
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        if(slidePhotoList != null){
            return slidePhotoList.size();
        }
        else
            return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}

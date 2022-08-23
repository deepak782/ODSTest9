package com.example.odstest9;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends BaseAdapter {
    Context context;
    List<PhotoModel> photoModelList=new ArrayList<>();
    LayoutInflater layoutInflater;

    public PhotoAdapter(Context context, List<PhotoModel> photoModelList) {
        this.context = context;
        this.photoModelList = photoModelList;
        layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return photoModelList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View root=layoutInflater.inflate(R.layout.custom_photo,null);
        ImageView imageView=root.findViewById(R.id.img1);
        TextView textView=root.findViewById(R.id.filename);

        textView.setText(photoModelList.get(i).getName());
        Glide.with(context)
                .load(photoModelList.get(i).getImage())
                .centerCrop()
                .placeholder(R.drawable.loading)
                .into(imageView);
        return root;
    }
}

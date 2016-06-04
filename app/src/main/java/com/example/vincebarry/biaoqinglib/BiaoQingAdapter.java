package com.example.vincebarry.biaoqinglib;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.LruCache;

/**
 * Created by VinceBarry on 2016/6/3.
 */
public class BiaoQingAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    private LruCache lruCache;

    public BiaoQingAdapter(LruCache lruCache, Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.lruCache = lruCache;
        this.context = context;
    }

    @Override
    public int getCount() {
        return lruCache.size();
    }

    @Override
    public Object getItem(int position) {
        return lruCache.get(position+"");
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_gridview,null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.item_iv);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Bitmap bitmap = lruCache.get(position+"");
        viewHolder.imageView.setImageBitmap(bitmap);
        return convertView;
    }
    class ViewHolder{
        ImageView imageView;
    }
}

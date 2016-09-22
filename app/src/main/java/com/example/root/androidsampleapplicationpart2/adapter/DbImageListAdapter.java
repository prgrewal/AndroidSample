package com.example.root.androidsampleapplicationpart2.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.androidsampleapplicationpart2.R;

import java.util.ArrayList;

/**
 * Created by root on 8/19/16.
 */
public class DbImageListAdapter extends BaseAdapter {

    private Context context;
    ArrayList<Bitmap> queryResults;

    public DbImageListAdapter(Context context, ArrayList<Bitmap> queryResults) {
        this.context = context;
        this.queryResults = queryResults;
    }

    @Override
    public int getCount() {
        return queryResults.size();
    }

    @Override
    public Object getItem(int position) {
        return queryResults.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.query_item_image, null);
        }

        ImageView queryItem = (ImageView) convertView.findViewById(R.id.cameraPic);

        queryItem.setImageBitmap(queryResults.get(position));


        return convertView;
    }

}

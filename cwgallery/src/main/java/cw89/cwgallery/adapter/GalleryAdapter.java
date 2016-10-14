/*
 * Copyright 2016 "KimChangWan <cwank89@gmail.com>"
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cw89.cwgallery.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

import cw89.cwgallery.R;
import cw89.cwgallery.item.GalleryItem;
import cw89.cwgallery.view.CWGallery;
import cw89.cwgallery.view.Gallery;

/**
 * Created by chang-wan on 2016. 9. 22..
 */
public class GalleryAdapter extends BaseAdapter {
    private static final String TAG = GalleryAdapter.class
            .getSimpleName();
    private Activity mActivity;
    private Gallery mcGallery;
    private ArrayList<GalleryItem> mArrData = new ArrayList<GalleryItem>();
    private LinearLayout parentView;


    private class ViewHolder {

        public ImageView imageView;
        public CheckBox cBox;

    }

    public GalleryAdapter(Activity activity, Gallery mcGallery) {
        super();
        this.mActivity = activity;
        this.mcGallery = mcGallery;
    }

    @Override
    public int getCount() {
        return mArrData.size();
    }

    @Override
    public Object getItem(int position) {
        return mArrData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addData(ArrayList<GalleryItem> thumbImageInfoList) {
        this.mArrData = thumbImageInfoList;
    }

    public void dataChange() {
        notifyDataSetChanged();

    }

    public void setChecked(int position) {


        mArrData.get(position).setCheckedState(!mArrData.get(position).getCheckedState());
        CheckBox cbox = (CheckBox) mActivity.findViewById(position);
        cbox.setChecked(mArrData.get(position).getCheckedState());
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;


        if (convertView == null) {
            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) mActivity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_gallery,
                    null);


            parentView = (LinearLayout) convertView
                    .findViewById(R.id.parentView);


            holder.imageView = (ImageView) convertView.findViewById(R.id.img);
            holder.cBox = (CheckBox) convertView
                    .findViewById(R.id.check_box);


            Drawable drawable = CWGallery.getInstance(mActivity).getSettings().getGalleryCheckBoxResource();
            if(drawable !=null) {
                holder.cBox.setButtonDrawable(drawable);
                holder.cBox.setBackgroundColor(Color.TRANSPARENT);

            }


            int size = CWGallery.getInstance(mActivity).getSettings().getGalleryCheckBoxSize();
            int margins = CWGallery.getInstance(mActivity).getSettings().getGalleryCheckBoxMargins();
            LinearLayout.LayoutParams params_check = new LinearLayout.LayoutParams(size, size);
            params_check.rightMargin =margins;
            params_check.topMargin = margins;
            holder.cBox.setLayoutParams(params_check);

            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        parentView = (LinearLayout) convertView.findViewById(R.id.parentView);
        parentView.setTag(position);

        parentView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub


            }
        });


        holder.cBox.setId(position);

        boolean isCheck = mArrData.get(position).getCheckedState();
        holder.cBox.setChecked(isCheck);
        holder.cBox.setClickable(false);

        if ((mArrData.get(position)).getCheckedState()) {
            holder.cBox.setChecked(isCheck);
        }


        Glide.with(mActivity).load(new File(mArrData.get(position).getPath())).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                try {
                    int position = (Integer) ((View) v.getParent().getParent()).getTag();
                    // TODO Auto-generated method stub


                    mcGallery.select(position);
                } catch (NullPointerException e) {
                    e.getStackTrace();
                }

            }
        });
        return convertView;
    }


}
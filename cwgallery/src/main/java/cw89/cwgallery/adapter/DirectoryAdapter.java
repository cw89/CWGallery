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

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

import cw89.cwgallery.CWGalleryActivity;
import cw89.cwgallery.R;
import cw89.cwgallery.item.DirectoryItem;
import cw89.cwgallery.view.CWGallery;
import cw89.cwgallery.view.Directory;

/**
 * Created by chang-wan on 2016. 9. 22..
 */
public class DirectoryAdapter extends BaseAdapter {
    private static final String TAG = DirectoryAdapter.class
            .getSimpleName();
    private CWGalleryActivity mActivity;
    private ArrayList<DirectoryItem> mArrData = new ArrayList<DirectoryItem>();
    private LinearLayout parentView;


    private class ViewHolder {

        public ImageView imageView;
        public TextView txt_title;
        public TextView txt_count;

    }

    private Directory mDirectory;

    public DirectoryAdapter(CWGalleryActivity activity, Directory directory) {
        super();
        this.mActivity = activity;
        this.mDirectory = directory;
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

    public void addData(ArrayList<DirectoryItem> directoryList) {
        this.mArrData = directoryList;
    }

    public void dataChange() {
        notifyDataSetChanged();

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) mActivity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_directory,
                    null);


            parentView = (LinearLayout) convertView
                    .findViewById(R.id.parentView);


            int height = CWGallery.getInstance(mActivity).getSettings().getDirectoryListItemHeight();

            int titleColor =  CWGallery.getInstance(mActivity).getSettings().getDirectoryListItemTitleTextColor();
            float titleSize =  CWGallery.getInstance(mActivity).getSettings().getDirectoryListItemTitleTextSize();
            int countColor =  CWGallery.getInstance(mActivity).getSettings().getDirectoryListItemCountTextColor();
            float countSize =  CWGallery.getInstance(mActivity).getSettings().getDirectoryListItemCountTextSize();

            parentView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height));
            holder.imageView = (ImageView) convertView.findViewById(R.id.img);
            holder.txt_title = (TextView) convertView.findViewById(R.id.txt_title);
            holder.txt_count = (TextView) convertView.findViewById(R.id.txt_count);
            holder.txt_title.setTextColor(titleColor);
            holder.txt_title.setTextSize(TypedValue.COMPLEX_UNIT_PX,titleSize);
            holder.txt_count.setTextColor(countColor);
            holder.txt_count.setTextSize(TypedValue.COMPLEX_UNIT_PX,countSize);

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
                int currentID = (Integer) v.getTag();
                String id = mArrData.get(currentID).getId();
                String directoryName = mArrData.get(currentID).getDirectoryName();


               mActivity.openGallery(id,directoryName);


            }
        });

        String path = mArrData.get(position).getPath();
        Glide.with(mActivity).load(new File(path)).into(holder.imageView);
        String directoryName = mArrData.get(position).getDirectoryName();
        holder.txt_title.setText(directoryName);
        String count = mArrData.get(position).getCount() + "";
        holder.txt_count.setText(count);


        return convertView;
    }


}
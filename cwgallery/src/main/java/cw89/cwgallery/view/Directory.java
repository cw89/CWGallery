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

package cw89.cwgallery.view;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cw89.cwgallery.CWGalleryActivity;
import cw89.cwgallery.R;
import cw89.cwgallery.adapter.DirectoryAdapter;
import cw89.cwgallery.common.CWGallerySettings;
import cw89.cwgallery.common.Util;
import cw89.cwgallery.item.DirectoryItem;

/**
 * Created by chang-wan on 2016. 9. 22..
 */
public class Directory {

    private static final String TAG = Directory.class.getSimpleName();

    private CWGalleryActivity mActivity;
    private ImageButton btn_back;
    private View parentView = null;
    private RelativeLayout header;
    private TextView txt_title;
    private ListView listView;
    private DirectoryAdapter mListAdapter;
    private ArrayList<DirectoryItem> directoryList = new ArrayList<DirectoryItem>();

    private CWGallerySettings settings;

    private boolean isDirectroyCamera = false;
    private final String SAVE_FOLDER = "/DCIM";

    public Directory(CWGalleryActivity activity) {

        super();

        this.mActivity = activity;
        settings = CWGallery.getInstance(mActivity).getSettings();
        init();
    }

    private void init()
    {
        directoryList = new ArrayList<DirectoryItem>();
    }

    public View createView() {

        parentView = mActivity.getLayoutInflater().inflate(
                R.layout.cw_directory, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        mActivity.addContentView(parentView, params);

        onFindView();
        setClick();

        initList();

        updateList();

        return parentView;

    }

    private void onFindView() {
        btn_back = (ImageButton) parentView.findViewById(R.id.btn_back);
        header = (RelativeLayout) parentView.findViewById(R.id.header);

        txt_title = (TextView) parentView.findViewById(R.id.txt_title);
        listView = (ListView) parentView.findViewById(R.id.listView);

        settingUI();
    }

    private void settingUI() {
        header.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, settings.getHeaderHeight()));
        header.setBackgroundColor(settings.getHeaderBackgroundColor());

        if (settings.getHeaderBackgroundResource() != 0) {
            try {
                header.setBackgroundResource(settings.getHeaderBackgroundResource());
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
        btn_back.setImageResource(settings.getHeaderBackBtnResource());

        txt_title.setTextColor(settings.getHeaderTextColor());
        txt_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, settings.getHeaderTextSize());
        txt_title.setText(settings.getTitle());
        listView.setDivider(settings.getDivider());
        listView.setDividerHeight(settings.getDividerHeight());

        listView.setBackgroundColor(settings.getDirectoryListItemBackgroundColor());
        if (settings.getDirectoryListItemBackgroundResource() != 0) {
            try {
                listView.setBackgroundResource(settings.getDirectoryListItemBackgroundResource());
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
    }

    private void setClick() {
        parentView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mActivity.onBackPressed();
            }
        });

    }





    private void getTotalGallery() {
        String directoryName = mActivity.getString(R.string.cw_title_directory_total).toString();


        String[] projection = {
                MediaStore.Images.Media.DATA, MediaStore.Images.ImageColumns.ORIENTATION};
        String ORDER_BY = MediaStore.Images.Media.DATE_ADDED + " desc";
        Cursor imageCursor = mActivity.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection, null, null, ORDER_BY);

        if (imageCursor != null && imageCursor.getCount() > 0) {

            imageCursor.moveToFirst();
            int count = imageCursor.getCount();

            addData("ALL",imageCursor.getString(imageCursor
                    .getColumnIndex(MediaStore.Images.Media.DATA)),directoryName,count);
        }
        imageCursor.close();

        updateList();
    }
    private void addData(String id, String path, String directoryName, int count)
    {
        DirectoryItem thumbInfo = new DirectoryItem();
        thumbInfo.setId(id);
        thumbInfo.setPath(path);
        thumbInfo.setDirectoryName(directoryName);
        thumbInfo.setCount(count);


        if (Util.checkImage(path)) {
            directoryList.add(thumbInfo);

        }
        CWGallery.getInstance(mActivity).directoryListCallBack(directoryList);
        mActivity.directoryListCallBack(directoryList);
    }

    private void getDirectoryData(String directoryName, String directoryPath) {


        String[] projectionCamera = {MediaStore.Images.Media.BUCKET_ID, MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA};
        String ORDER_BY = MediaStore.Images.Media.DATE_TAKEN + " desc";
        Cursor imageCursor = mActivity.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projectionCamera, directoryPath, null,
                ORDER_BY);

        if (imageCursor != null && imageCursor.getCount() > 0) {

            imageCursor.moveToFirst();
            int count = imageCursor.getCount();
            addData(imageCursor.getString(imageCursor
                    .getColumnIndex(MediaStore.Images.Media.BUCKET_ID)),imageCursor.getString(imageCursor
                    .getColumnIndex(MediaStore.Images.Media.DATA)),directoryName,count);
        }
        imageCursor.close();
        updateList();


    }

    public ArrayList<DirectoryItem> requestData() {

        mActivity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStorageDirectory() + SAVE_FOLDER)));
        isDirectroyCamera = false;


        String[] projection = {MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.DATA, MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME};

        String BUCKET_GROUP_BY =
                "1) GROUP BY 1,(2";
        String BUCKET_ORDER_BY = MediaStore.Images.Media.BUCKET_DISPLAY_NAME;

        Cursor imageCursor = mActivity.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, BUCKET_GROUP_BY, null, BUCKET_ORDER_BY);

        getTotalGallery();


        if (!isDirectroyCamera) {
            String directoryName = "Camera";
            String directroyPath = "bucket_display_name=\'" + directoryName + "\'";
            getDirectoryData(directoryName, directroyPath);
            isDirectroyCamera = true;
        }

        try {
            int imageIDCol = imageCursor
                    .getColumnIndex(MediaStore.Images.Media.BUCKET_ID);
            int imageDirectoryCol = imageCursor
                    .getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
            int totalCount = imageCursor.getCount();
            imageCursor.moveToFirst();


            for (int i = 0; i < totalCount; i++) {

                String id = imageCursor.getString(imageIDCol);
                String directoryName = imageCursor.getString(imageDirectoryCol);
                String directoryPath = "bucket_id=\'" + id + "\'";

                if (!"Camera".equals(directoryName))
                    getDirectoryData(directoryName, directoryPath);


                imageCursor.moveToNext();

            }
            imageCursor.close();
        } catch (NullPointerException e) {
            e.getStackTrace();
        } catch (Exception e) {
            e.getStackTrace();
        }


        mActivity.stopManagingCursor(imageCursor);

        return directoryList;
    }

    private void initList() {

        mListAdapter = new DirectoryAdapter(mActivity, this);
        listView.setAdapter(mListAdapter);
    }



    private void updateList() {

        try {
            mListAdapter.addData(directoryList);
            mListAdapter.dataChange();
        }catch (Exception e)
        {
            e.getStackTrace();
        }
    }

    public void removeView() {

        if (parentView != null) {
            ((ViewManager) parentView.getParent()).removeView(parentView);
            parentView = null;
        }
    }

}
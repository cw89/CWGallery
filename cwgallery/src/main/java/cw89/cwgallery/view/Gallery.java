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

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cw89.cwgallery.CWGalleryActivity;
import cw89.cwgallery.R;
import cw89.cwgallery.adapter.GalleryAdapter;
import cw89.cwgallery.common.CWGallerySettings;
import cw89.cwgallery.common.Util;
import cw89.cwgallery.item.GalleryItem;

/**
 * Created by chang-wan on 2016. 9. 22..
 */
public class Gallery {

    private static final String TAG = Gallery.class.getSimpleName();

    private CWGalleryActivity mActivity;

    private View parentView = null;

    private GridView mGvImageList;
    private GalleryAdapter listAdapter;

    private ImageButton btn_back;
    private ImageButton btn_check;
    private RelativeLayout header;
    private TextView txt_title;
    private TextView txt_count;

    private boolean lastitemVisibleFlag = false;

    private ArrayList<GalleryItem> thumbList = new ArrayList<GalleryItem>();
    private ArrayList<Uri> uriList = new ArrayList<Uri>();

    private long returnValue;
    private int requesPictureCount;
    private int page ;
    private int total ;
    private boolean totalCheck = false;


    private String _id = "";
    private String _name = "";


    private CWGallerySettings settings;

    public Gallery(CWGalleryActivity activity) {

        super();

        this.mActivity = activity;
        settings = CWGallery.getInstance(mActivity).getSettings();
        init();

    }


    private void updateCount(int count) {

        if (settings.getGalleryCheckCountStatus()) {
            txt_count.setVisibility(View.VISIBLE);
        } else {
            txt_count.setVisibility(View.GONE);
        }

        String countString = count + "/" + settings.getMax();
        txt_count.setText(countString);
        txt_count.setTextColor(settings.getGalleryCheckCountStatusTextColor());
        txt_count.setTextSize(TypedValue.COMPLEX_UNIT_PX, settings.getGalleryCheckCountStatusTextSize());
    }


    public void addUri(Uri uri) {
        uriList.add(uri);
        updateCount(uriList.size());
    }

    public void removeUri(Uri uri) {


        uriList.remove(uri);
        updateCount(uriList.size());
    }


    private void init() {

        total = 0;
        totalCheck = false;
        returnValue = 0;
        page = 1;
        requesPictureCount = settings.getRequestGalleryPictureCount();
        thumbList = new ArrayList<GalleryItem>();
        uriList = new ArrayList<Uri>();


    }

    public View createView(String id, String name) {
        this._id = id;
        this._name = name;

        parentView = mActivity.getLayoutInflater().inflate(
                R.layout.cw_gallery, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        mActivity.addContentView(parentView, params);

        onFindView();
        setClick();
        updateCount(settings.getCurrent());


        initList();
        updateList();

        return parentView;

    }

    private void onFindView() {
        btn_back = (ImageButton) parentView.findViewById(R.id.btn_back);
        btn_check = (ImageButton) parentView.findViewById(R.id.btn_check);
        header = (RelativeLayout) parentView.findViewById(R.id.header);
        txt_count = (TextView) parentView.findViewById(R.id.txt_count);
        txt_title = (TextView) parentView.findViewById(R.id.txt_title);

        mGvImageList = (GridView) parentView.findViewById(R.id.gallerylist);

        settingUI();
    }

    private void settingUI() {
        header.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, settings.getHeaderHeight()));
        header.setBackgroundColor(settings.getHeaderBackgroundColor());
        btn_back.setImageResource(settings.getHeaderBackBtnResource());
        btn_check.setImageResource(settings.getHeaderCheckBtnResource());
        txt_title.setTextColor(settings.getHeaderTextColor());
        txt_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, settings.getHeaderTextSize());
        txt_title.setText(_name);

        mGvImageList.setBackgroundColor(settings.getGalleryBackgroundColor());
        if (settings.getGalleryBackgroundResource() != 0) {
            try {
                mGvImageList.setBackgroundResource(settings.getGalleryBackgroundResource());
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
        btn_check.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (uriList.size() > 0) {

                    CWGallery.getInstance(mActivity).resultListCallBack(uriList);

                    mActivity.resultListCallBack(uriList);

                }


            }
        });
        mGvImageList.setOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub
                if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
                        && lastitemVisibleFlag) {

                    if (totalCheck) {
                        return;
                    } else {
                        requestData(_id);

                    }
                    lastitemVisibleFlag = false;

                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                // TODO Auto-generated method stub
                lastitemVisibleFlag = (totalItemCount > 0)
                        && (firstVisibleItem + visibleItemCount >= totalItemCount);
            }
        });
    }


    public void requestData(String _id) {


        try {
            String directroyPath = "bucket_id=\'" + _id + "\'";
            Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            String[] projection = {MediaStore.Images.Media._ID,
                    MediaStore.Images.Media.DATA};
            String ORDER_BY = MediaStore.Images.Media.DATE_TAKEN + " desc";

            Cursor imageCursor = mActivity.getContentResolver().query(uri,
                    projection, directroyPath, null,
                    ORDER_BY);
            if ("ALL".equals(_id)) {
                imageCursor = mActivity.getContentResolver().query(uri,
                        projection, null, null,
                        ORDER_BY);
            }
            if (imageCursor != null && imageCursor.getCount() > 0) {


                int imageIDCol = imageCursor
                        .getColumnIndex(MediaStore.Images.Media._ID);
                int imageDataCol = imageCursor
                        .getColumnIndex(MediaStore.Images.Media.DATA);


                total = imageCursor.getCount();
                imageCursor.move((int) returnValue);
                while (imageCursor.moveToNext()) {
                    GalleryItem thumbInfo = new GalleryItem();
                    String path = imageCursor.getString(imageDataCol);
                    thumbInfo.setId(imageCursor.getString(imageIDCol));
                    thumbInfo.setData(path);
                    thumbInfo.setPath(path);
                    thumbInfo.setUri(Uri.parse(path));

                    thumbInfo.setCheckedState(false);

                    if (Util.checkImage(path)) {
                        thumbList.add(thumbInfo);
                        CWGallery.getInstance(mActivity).galleryListCallBack(thumbList);
                        mActivity.galleryListCallBack(thumbList);

                        returnValue++;


                        if (returnValue >= total) {
                            totalCheck = true;
                        }
                        if (returnValue == requesPictureCount * page) {

                            page++;
                            break;
                        }
                        updateList();

                    }

                }
            }
            imageCursor.close();
            mActivity.stopManagingCursor(imageCursor);
        } catch (Exception e) {
            e.getStackTrace();
        }



    }


    private void initList() {
        listAdapter = new GalleryAdapter(mActivity, this);
        mGvImageList.setAdapter(listAdapter);
    }

    private void updateList() {
        try {
            listAdapter.addData(thumbList);
            listAdapter.dataChange();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }


    public void select(int position) {


        if (thumbList.get(position).getCheckedState()) {

            removeUri(thumbList.get(position).getUri());
            listAdapter.setChecked(position);
        } else {
            if (uriList.size() < settings.getMax() - settings.getCurrent()) {

                listAdapter.setChecked(position);

                addUri(thumbList.get(position).getUri());


            }
        }

        int current = uriList.size() + settings.getCurrent();
        int max = settings.getMax();

        CWGallery.getInstance(mActivity).checkStatusCallback(current, max);
        mActivity.checkStatusCallback(current, max);

    }

    public void removeView() {


        if (parentView != null) {
            ((ViewManager) parentView.getParent()).removeView(parentView);
            parentView = null;
        }

    }
}
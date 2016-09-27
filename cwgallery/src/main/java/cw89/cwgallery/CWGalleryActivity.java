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

package cw89.cwgallery;

import android.app.Activity;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;

import java.util.ArrayList;

import cw89.cwgallery.callback.OnGalleryCallBackListener;
import cw89.cwgallery.common.Config;
import cw89.cwgallery.item.DirectoryItem;
import cw89.cwgallery.item.GalleryItem;
import cw89.cwgallery.view.Directory;
import cw89.cwgallery.view.Gallery;

/**
 * Created by chang-wan on 2016. 9. 21..
 */
public class CWGalleryActivity extends Activity implements OnGalleryCallBackListener {


    private static final String TAG = CWGalleryActivity.class.getSimpleName();

    protected  CWGalleryActivity mActivity;
    private Directory mDirectory = null;
    private Gallery mGallery = null;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (false && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll().penaltyDialog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll().penaltyDeath().build());
        }
        mActivity = this;



        try{
            boolean isShow = getIntent().getExtras().getBoolean(Config.INTENT_REQUEST_CWGALLERY_SHOW,false);

            if(isShow) {
                show();
            }
        }catch (Exception e)
        {
            e.getStackTrace();
        }



    }


    protected void show() {
        openDirectory();
    }


    public void openDirectory() {

        mDirectory = new Directory(mActivity);
        mDirectory.createView();
        mDirectory.requestData();

    }

    public void openGallery(String id, String name) {

        mGallery = new Gallery(mActivity);
        mGallery.createView(id, name);
        mGallery.requestData();
    }

    public void onBackPressed() {

        if (finishCWGallery()) {
            super.onBackPressed();
        }
    }

    private boolean finishCWGallery() {
        if (mDirectory != null) {

            if (mGallery != null) {
                mGallery.removeView();
                mGallery = null;

                return false;
            } else {

                return true;
            }
        }
        return true;
    }

    public void onDestroy() {
        super.onDestroy();
        try {
            mGallery.removeView();
            mGallery = null;
        } catch (NullPointerException e) {
            e.getStackTrace();
        }
        try {
            mDirectory.removeView();
            mDirectory = null;
        } catch (NullPointerException e) {
            e.getStackTrace();
        }
    }


    @Override
    public void resultListCallBack(ArrayList<Uri> uriList) {

    }

    @Override
    public void checkStatusCallback(int current, int max) {

    }

    @Override
    public void directoryListCallBack(ArrayList<DirectoryItem> directoryList) {

    }

    @Override
    public void galleryListCallBack(ArrayList<GalleryItem> galleryList) {

    }
}

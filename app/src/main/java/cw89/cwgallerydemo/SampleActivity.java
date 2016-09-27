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

package cw89.cwgallerydemo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import cw89.cwgallery.CWGalleryActivity;
import cw89.cwgallery.common.CWGallerySettings;
import cw89.cwgallery.common.Config;
import cw89.cwgallery.item.DirectoryItem;
import cw89.cwgallery.item.GalleryItem;
import cw89.cwgallery.view.CWGallery;
import cw89.cwutil.Utils;

/**
 * Created by chang-wan on 2016. 9. 26..
 */
public class SampleActivity extends CWGalleryActivity {

    private static final String TAG = SampleActivity.class.getSimpleName();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        CWGallerySettings settings = new CWGallery(mActivity).getSettings();
        settings.setMax(5);
        settings.setCurrent(0);
        settings.setRequestGalleryPictureCount(120);
        show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void resultListCallBack(ArrayList<Uri> uriList) {


        Log.e(TAG, " uriList : " + uriList);

        try {

            Intent intent = new Intent();
            intent.putParcelableArrayListExtra(Config.INTENT_RESULT_NAME, uriList);
            setResult(Activity.RESULT_OK, intent);
            overridePendingTransition(0, 0);
            finish();


        } catch (NullPointerException e) {
            e.getStackTrace();
        }
    }

    @Override
    public void checkStatusCallback(int current, int max) {
        Log.e(TAG, " current : " + current);
        Log.e(TAG, " max : " + max);
        if (current == max) {
            String message = mActivity.getString(cw89.cwgallery.R.string.cw_photo_max_count);
            message = message.replace("[##COUNT##]", max + "");
            Utils.showToast(mActivity, message);
        }
    }

    @Override
    public void directoryListCallBack(ArrayList<DirectoryItem> directoryList) {


        Log.e(TAG, " directoryListCallBack : " + directoryList);
    }

    @Override
    public void galleryListCallBack(ArrayList<GalleryItem> galleryList) {
        Log.e(TAG, " galleryListCallBack : " + galleryList);
    }
}
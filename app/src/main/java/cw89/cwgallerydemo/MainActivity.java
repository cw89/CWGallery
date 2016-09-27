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

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import cw89.cwgallery.callback.OnGalleryCallBackListener;
import cw89.cwgallery.common.CWGallerySettings;
import cw89.cwgallery.common.Config;
import cw89.cwgallery.item.DirectoryItem;
import cw89.cwgallery.item.GalleryItem;
import cw89.cwgallery.view.CWGallery;
import cw89.cwutil.Utils;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = MainActivity.class.getSimpleName();

    private Activity mActivity;

    private Button btn_open;
    private Button btn_clear;
    private TextView txt_result;
    private LinearLayout parentView;
    private int CURRENT_COUNT = 0;
    private int MAX_COUNT = 5;
    public ArrayList<Uri> list = new ArrayList<Uri>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActivity = this;

        onFindView();
        init();


    }

    private void permissionCheck() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {

                getCWGallery();

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        10);


            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 10:

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getCWGallery();
                } else {
                }
                return;
        }
    }

    public void onStart() {
        super.onStart();

    }


    private void getCWGallery() {

        //getCWGalleryClass();
          getCWGalleryActivity();
    }

    /**
     * Open Class
     */
    private void getCWGalleryClass() {

        CWGallery cwGallery = new CWGallery(mActivity);
        CWGallerySettings settings = cwGallery.getSettings();
        settings.setMax(MAX_COUNT);
        settings.setCurrent(CURRENT_COUNT);


        cwGallery.addcallBackListener(new OnGalleryCallBackListener() {
            @Override
            public void resultListCallBack(ArrayList<Uri> uriList) {


                list = uriList;

                if (list != null) {
                    updateImage();
                    updateData();
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
        });

        cwGallery.show();
    }


    /**
     * Open Activity extends CWGalleryActivity
     */
    private void getCWGalleryActivity() {

        Intent intent = new Intent(mActivity, SampleActivity.class);
        startActivityForResult(intent, Config.INTENT_REQUEST_CWGALLERY);
        overridePendingTransition(0, 0);
    }

    private void onFindView() {
        btn_open = (Button) findViewById(R.id.btn_open);
        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permissionCheck();
            }
        });
        btn_clear = (Button) findViewById(R.id.btn_clear);
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearData();
            }
        });

        txt_result = (TextView) findViewById(R.id.txt_result);
        parentView = (LinearLayout) findViewById(R.id.parentView);
    }

    private void clearData() {

        init();
    }

    private void init() {

        list = new ArrayList<Uri>();
        CURRENT_COUNT = list.size();
        parentView.removeAllViews();

        updateData();
    }


    private void updateImage() {

        for (Uri uri : list) {


            int margins = Utils.convertDpPixel(mActivity, 5);
            int size = Utils.convertDpPixel(mActivity, 200);
            ImageView img = new ImageView(mActivity);
            LinearLayout.LayoutParams params_img = new LinearLayout.LayoutParams(size, size);

            params_img.topMargin = margins;
            params_img.bottomMargin = margins;
            Glide.with(this)
                    .load(uri.toString())
                    .fitCenter()
                    .into(img);
            parentView.addView(img, params_img);
        }
    }

    private void updateData() {
        CURRENT_COUNT = CURRENT_COUNT + list.size();
        StringBuilder sb = new StringBuilder();
        sb.append(" CURRENT : ");
        sb.append("" + CURRENT_COUNT);
        sb.append("\n");
        sb.append(" MAX : ");
        sb.append("" + MAX_COUNT);
        txt_result.setText(sb.toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);


        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Config.INTENT_REQUEST_CWGALLERY) {

                list = intent.getParcelableArrayListExtra(Config.INTENT_RESULT_NAME);

                if (list != null) {
                    updateImage();
                    updateData();
                }


            }
        }
    }

    public void onBackPressed() {
        super.onBackPressed();

    }
}

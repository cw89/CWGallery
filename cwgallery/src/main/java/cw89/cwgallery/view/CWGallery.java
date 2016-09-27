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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cw89.cwgallery.CWGalleryActivity;
import cw89.cwgallery.callback.OnGalleryCallBackListener;
import cw89.cwgallery.common.BaseGallery;
import cw89.cwgallery.common.CWGallerySettings;
import cw89.cwgallery.common.Config;
import cw89.cwgallery.item.DirectoryItem;
import cw89.cwgallery.item.GalleryItem;

/**
 * Created by chang-wan on 2016. 9. 22..
 */
public class CWGallery extends BaseGallery {


    private static final String TAG = CWGallery.class.getSimpleName();

    private static CWGallery cwGallery = null;

    private final List<OnGalleryCallBackListener> listeners = new LinkedList<OnGalleryCallBackListener>();

    public void addcallBackListener(OnGalleryCallBackListener listener) {
        listeners.add(listener);
    }

    public void removecallBackListener(OnGalleryCallBackListener listener) {
        listeners.remove(listener);
    }

    public void resultListCallBack(ArrayList<Uri> uriList) {
        for (OnGalleryCallBackListener listener : listeners) {
            if (listener != null) {
                close();
                listener.resultListCallBack(uriList);
            }
        }
    }
    public void checkStatusCallback(int current, int max) {
        for (OnGalleryCallBackListener listener : listeners) {
            if (listener != null) {
                listener.checkStatusCallback(current,max);
            }
        }
    }
    public void directoryListCallBack(ArrayList<DirectoryItem> directoryList) {
        for (OnGalleryCallBackListener listener : listeners) {
            if (listener != null) {
                listener.directoryListCallBack(directoryList);
            }
        }
    }

    public void galleryListCallBack(ArrayList<GalleryItem> galleryList) {
        for (OnGalleryCallBackListener listener : listeners) {
            if (listener != null) {
                listener.galleryListCallBack(galleryList);
            }
        }
    }


    public CWGallery(Context context) {
        super();
        this.context = context;
        cwGallery = this;
    }


    public static CWGallery getInstance(Context context) {
        if (cwGallery == null) {
            cwGallery = new CWGallery(context);
        }
        return cwGallery;
    }

    @Override
    public CWGallerySettings getSettings() {

        super.getSettings();
        return settings;
    }

    /**
     * show
     *
     */
    public void show() {

        Intent intent = new Intent(context, CWGalleryActivity.class);
        intent.putExtra(Config.INTENT_REQUEST_CWGALLERY_SHOW,true);
        ((Activity) context).startActivityForResult(intent, Config.INTENT_REQUEST_CWGALLERY);

    }

    /**
     * close
     *
     */
    private void close() {
        Activity activity = getActivity();

        if (activity != null) {
            activity.finish();
            activity.overridePendingTransition(0, 0);
        }
    }

    /**
     * Top of the stack of the current activity
     *
     * @return activity
     */
    private static Activity getActivity() {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);

            Map<Object, Object> activities = (Map<Object, Object>) activitiesField.get(activityThread);
            if (activities == null)
                return null;

            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    Activity activity = (Activity) activityField.get(activityRecord);
                    return activity;
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }


}

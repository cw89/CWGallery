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

package cw89.cwgallery.item;

import android.net.Uri;

/**
 * Created by chang-wan on 2016. 9. 22..
 */
public class GalleryItem {
    private String id;
    private String data;
    private String path;
    private Uri uri;
    private boolean checkedState = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Uri getUri() { return uri; }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public boolean getCheckedState() {
        return checkedState;
    }

    public void setCheckedState(boolean checkedState) {
        this.checkedState = checkedState;
    }
}

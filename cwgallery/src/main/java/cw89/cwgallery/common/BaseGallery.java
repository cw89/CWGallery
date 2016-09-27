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

package cw89.cwgallery.common;

import android.content.Context;

/**
 * Created by chang-wan on 2016. 9. 22..
 */
public abstract class BaseGallery {


    protected CWGallerySettings settings = null;
    protected Context context = null;

    protected CWGallerySettings getSettings() {


        if (settings == null && context != null) {
            settings = new CWGallerySettings(context) {
            };
        }

        return settings;


    }
}

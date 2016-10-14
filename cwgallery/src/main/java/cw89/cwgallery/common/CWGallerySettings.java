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
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.widget.LinearLayout;

import cw89.cwgallery.R;

/**
 * Created by chang-wan on 2016. 10. 14..
 */
public abstract class CWGallerySettings {


    protected int max_count;
    protected int current_count;
    protected String title;
    protected int headerBackgroundColor;
    protected int headerBackgroundResource;
    protected int bodyBackgroundColor;
    protected int headerHeight;
    protected int headerTextColor;
    protected float headerTextSize;

    protected int headerBackBtnResource;
    protected int headerCheckBtnResource;

    protected Drawable divider;
    protected int dividerHeight;
    protected int directoryListItemHeight;
    protected int directoryListItemBackgroundColor;
    protected int directoryListItemBackgroundResource;
    protected int directoryListItemTitleTextColor;
    protected float directoryListItemTitleTextSize;

    protected int directoryListItemCountTextColor;
    protected float directoryListItemCountTextSize;


    protected int galleryBackgroundColor;
    protected int galleryBackgroundResource;
    protected Drawable galleryCheckBoxResource;
    protected int galleryCheckBoxSize;
    protected int galleryCheckBoxMargins;

    protected boolean galleryCheckCountStatus;
    protected int galleryCheckCountStatusTextColor;
    protected float galleryCheckCountStatusTextSize;

    protected int requestGalleryPictureCount;


    protected CWGallerySettings(Context context) {


        init(context);
    }

    protected void init(Context context) {
        max_count = 10;
        current_count = 0;

        title = context.getString(R.string.cw_title_directory);

        headerHeight = context.getResources().getDimensionPixelSize(R.dimen.common_header_height);

        headerBackgroundColor = context.getColor(R.color.common_header_background_color);

        headerBackgroundResource = 0;

        headerTextColor = context.getColor(R.color.common_header_text_color);

        headerTextSize = context.getResources().getDimensionPixelSize(R.dimen.common_header_center_font_size);


        headerBackBtnResource = context.getResources().getIdentifier("header_btn_back", "drawable", context.getPackageName());

        headerCheckBtnResource = context.getResources().getIdentifier("header_btn_check", "drawable", context.getPackageName());

        divider = new ColorDrawable(Color.BLACK);

        dividerHeight = 2;

        directoryListItemHeight = context.getResources().getDimensionPixelSize(R.dimen.list_item_height);

        directoryListItemBackgroundColor = Color.WHITE;

        directoryListItemBackgroundResource = 0;

        directoryListItemTitleTextColor = Color.BLACK;

        directoryListItemTitleTextSize = context.getResources().getDimensionPixelSize(R.dimen.list_item_font_size);

        directoryListItemCountTextColor = Color.BLACK;

        directoryListItemCountTextSize = context.getResources().getDimensionPixelSize(R.dimen.list_item_font_size);

        galleryBackgroundColor = Color.WHITE;

        galleryBackgroundResource = 0;

        galleryCheckBoxResource = null;

        galleryCheckBoxSize = LinearLayout.LayoutParams.WRAP_CONTENT;

        galleryCheckBoxMargins = 0;

        galleryCheckCountStatus = true;

        galleryCheckCountStatusTextColor = Color.parseColor("#f5a503");

        galleryCheckCountStatusTextSize =  context.getResources().getDimensionPixelSize(R.dimen.common_header_center_font_size);

        requestGalleryPictureCount = 90;
    }


    public void setMax(int max) {

        max_count = max;
    }

    public int getMax() {
        return max_count;
    }

    public void setCurrent(int current) {
        current_count = current;
    }

    public int getCurrent() {
        return current_count;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setHeaderBackgroundColor(int color) {
        this.headerBackgroundColor = color;
    }

    public int getHeaderBackgroundColor() {
        return headerBackgroundColor;
    }

    public void setHeaderBackgroundResource(int resource) {
        this.headerBackgroundResource = resource;
    }

    public int getHeaderBackgroundResource() {
        return headerBackgroundResource;
    }

    public void setBodyBackgroundColor(int backgroundBodyColor) {

        this.bodyBackgroundColor = backgroundBodyColor;
    }

    public int getBodyBackgroundColor() {
        return bodyBackgroundColor;
    }

    public void setHeaderHeight(int height) {
        this.headerHeight = height;
    }

    public int getHeaderHeight() {
        return headerHeight;
    }

    public void setHeaderBackBtnResource(int resource) {
        this.headerBackBtnResource = resource;
    }

    public int getHeaderBackBtnResource() {
        return headerBackBtnResource;
    }

    public void setHeaderCheckBtnResource(int resource) {
        this.headerCheckBtnResource = resource;
    }

    public int getHeaderCheckBtnResource() {
        return headerCheckBtnResource;
    }

    public void setHeaderTextColor(int color) {
        this.headerTextColor = color;
    }

    public int getHeaderTextColor() {
        return headerTextColor;
    }

    public void setHeaderTextSize(float size) {
        this.headerTextSize = size;
    }

    public float getHeaderTextSize() {
        return headerTextSize;
    }


    public void setDirectoryListItemHeight(int height) {
        this.directoryListItemHeight = height;
    }

    public int getDirectoryListItemHeight() {
        return directoryListItemHeight;
    }


    public void setDirectoryListItemBackgroundColor(int color) {
        this.directoryListItemBackgroundColor = color;
    }

    public int getDirectoryListItemBackgroundColor() {
        return directoryListItemBackgroundColor;
    }

    public void setDirectoryListItemBackgroundResource(int resource) {

        this.directoryListItemBackgroundResource = resource;
    }

    public int getDirectoryListItemBackgroundResource() {
        return directoryListItemBackgroundResource;
    }

    public void setDirectoryListItemTitleTextColor(int color) {
        this.directoryListItemCountTextColor = color;
    }

    public int getDirectoryListItemTitleTextColor() {
        return directoryListItemTitleTextColor;
    }

    public void setDirectoryListItemCountTextColor(int color) {
        this.directoryListItemCountTextColor = color;
    }

    public int getDirectoryListItemCountTextColor() {
        return directoryListItemCountTextColor;
    }

    public void setDirectoryListItemTitleTextSize(float size) {

        this.directoryListItemTitleTextSize = size;
    }


    public float getDirectoryListItemTitleTextSize() {
        return directoryListItemTitleTextSize;
    }


    public void setDirectoryListItemCountTextSize(float size) {
        this.directoryListItemCountTextSize = size;
    }


    public float getDirectoryListItemCountTextSize() {
        return directoryListItemCountTextSize;
    }


    public void setDivider(Drawable drawable) {
        this.divider = drawable;
    }

    public Drawable getDivider() {
        return divider;
    }

    public void setDividerHeight(int height) {
        this.dividerHeight = height;
    }

    public int getDividerHeight() {
        return dividerHeight;
    }


    public void setGalleryBackgroundColor(int color) {
        this.galleryBackgroundColor = color;
    }

    public int getGalleryBackgroundColor() {
        return galleryBackgroundColor;
    }

    public void setGalleryBackgroundResource(int resource) {
        this.galleryBackgroundResource = resource;
    }

    public int getGalleryBackgroundResource() {
        return galleryBackgroundResource;
    }

    public void setGalleryCheckBoxResource(Drawable drawable) {
        this.galleryCheckBoxResource = drawable;
    }

    public Drawable getGalleryCheckBoxResource() {
        return galleryCheckBoxResource;
    }

    public void setGalleryCheckBoxSize(int size) {
        this.galleryCheckBoxSize = size;
    }

    public int getGalleryCheckBoxSize() {
        return galleryCheckBoxSize;
    }


    public void setGalleryCheckBoxMargins(int margins) {
        this.galleryCheckBoxMargins = margins;
    }

    public int getGalleryCheckBoxMargins()
    {
        return galleryCheckBoxMargins;
    }


    public void setGalleryCheckCountStatus(boolean flag) {
        this.galleryCheckCountStatus = flag;
    }

    public boolean getGalleryCheckCountStatus() {
        return galleryCheckCountStatus;
    }

    public void setGalleryCheckCountStatusTextColor(int color) {
        this.galleryCheckCountStatusTextColor = color;
    }

    public int getGalleryCheckCountStatusTextColor() {

        return galleryCheckCountStatusTextColor;
    }

    public void setGalleryCheckCountStatusTextSize(float size)
    {
        this.galleryCheckCountStatusTextSize = size;
    }

    public float getGalleryCheckCountStatusTextSize()
    {
        return galleryCheckCountStatusTextSize;
    }

    public void setRequestGalleryPictureCount(int count)
    {
        this.requestGalleryPictureCount = count;
    }

    public int getRequestGalleryPictureCount()
    {
        return requestGalleryPictureCount;
    }

}




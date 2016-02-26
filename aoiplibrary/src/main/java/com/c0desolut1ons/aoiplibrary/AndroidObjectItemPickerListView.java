package com.c0desolut1ons.aoiplibrary;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.ListView;

import java.util.List;

import adapter.AndroidObjectItemPickerListViewAdapter;

/**
 * Description
 *
 * @author Nenad Stojnic
 * @email nenad.stojnic@gmail.com
 * @company dzomba.net
 * @date 24.02.2016 08:13
 */
public class AndroidObjectItemPickerListView extends ListView {

    private List<String> items;
    private AndroidObjectItemPickerClickListener mItemClickAndroidObjectItemPicker;
    private AndroidObjectItemPickerListViewAdapter mAdapter;
    private boolean scrollEnabled = false;
    private int objectId;
    private int lastPositionNotified;

    public AndroidObjectItemPickerListView(Context context){
        super(context);
        if(!isInEditMode()){
            initView(items);
        }

    }

    public AndroidObjectItemPickerListView(Context context, AttributeSet attrs){
        super(context, attrs);
        if(!isInEditMode()){
            initView(items);
        }
    }

    public AndroidObjectItemPickerListView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        if(!isInEditMode()){
            initView(items);
        }
    }

    private void initView(List<String> items){
        this.items = items;

        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                scrollEnabled = true;
                if (AndroidObjectItemPickerListView.this.items != null) {
                    selectListItem(AndroidObjectItemPickerListView.this.items.size() / 2, false);
                }

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });

    }

    public void setItems(Context context, List<String> items, int id, int position, boolean itemsClickables) {
        this.items = items;
        this.objectId = id;
        mAdapter = new AndroidObjectItemPickerListViewAdapter(context, R.layout.android_object_item_picker_list_item, items, position, itemsClickables, false);
        setAdapter(mAdapter);
    }

    AndroidObjectItemPickerListViewAdapter getAndroidObjectItemPickerListViewAdapter() {
        return mAdapter;
    }

    private void selectListItem(final int position, boolean notify){
        setSelection(position);
        if(notify){
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mItemClickAndroidObjectItemPicker == null) {
                        throw new IllegalStateException("Error!");
                    }
                    mItemClickAndroidObjectItemPicker.onItemClickAndroidObjectItemPicker(objectId, position, items.get(position));
                }
            }, 100);

        }
    }


    private void setNewPositionCenter(int position) {
        mAdapter.handleSelectEvent(position);
        selectListItem(position - 2, true);
    }

    public int getItemInListCenter() {

        int position = pointToPosition(getWidth() / 2, getHeight() / 2);
        if (position != -1) {

            if (position != lastPositionNotified) {

                //Only refresh adapter on different positions
                lastPositionNotified = position;
                mAdapter.handleSelectEvent(position);
            }
        }
        return position - 2;
    }

    private void setOnItemClickAndroidObjectItemPicker(AndroidObjectItemPickerClickListener listener){
        mItemClickAndroidObjectItemPicker = listener;
    }

    interface AndroidObjectItemPickerClickListener {
        void onItemClickAndroidObjectItemPicker(int id, int position, String valueResult);
    }



}

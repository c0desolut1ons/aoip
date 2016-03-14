package com.c0desolut1ons.aoiplibrary;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import com.c0desolut1ons.aoiplibrary.adapter.AndroidObjectItemPickerListViewAdapter;
import com.c0desolut1ons.aoiplibrary.common.Constants;

/**
 * Library class which simulate scroll picker with plain ListView.
 * Data source are places in list view with known number of elements.
 *
 * @author Nenad Stojnic
 */
public class AndroidObjectItemPickerListView extends ListView {

    private List<String> items;
    private AndroidObjectItemPickerClickListener mItemClickAndroidObjectItemPicker;
    private AndroidObjectItemPickerListViewAdapter mAdapter;
    private boolean scrollEnabled = false;
    private int objectId;
    private int lastPositionNotified;
    private int firstItem, scrollTop;

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

        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == 0) {
                    getItemInListCenter();
                    if (scrollTop < -Constants.PICKER_LIST_ITEM_HEIGHT) {
                        mAdapter.handleSelectEvent(firstItem + 1 + 2);
                        selectListItem(firstItem + 1, true);
                    } else {
                        selectListItem(firstItem, true);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
                // save index and top position
                View v = getChildAt(0);

                //Required to select the closest item when finger releases scroll
                scrollTop = (v == null) ? 0 : v.getTop();
                firstItem = firstVisibleItem;

                if (scrollEnabled) {
                    getItemInListCenter();
                }
            }
        });

        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setNewPositionCenter(position);
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
                    mItemClickAndroidObjectItemPicker.onItemClickAndroidObjectItemPickers(objectId, position, items.get(position));
                }
            }, 500);

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

    public void setOnItemClickAndroidObjectItemPicker(AndroidObjectItemPickerClickListener listener){
        mItemClickAndroidObjectItemPicker = listener;
    }

    public interface AndroidObjectItemPickerClickListener {
        void onItemClickAndroidObjectItemPickers(int id, int position, String valueResult);
    }

}

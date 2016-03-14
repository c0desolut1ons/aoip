package com.c0desolut1ons.aoiplibrary;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import java.util.Arrays;
import java.util.List;

/**
 * Main libray class.
 * Used for handling picker component and subcomponents.
 *
 * @author Nenad Stojnic
 */
public class AndroidObjectItemPicker extends RelativeLayout {

    private boolean doesItemsAreClickables = AndroidObjectItemPickerSettings.DEFAULT_ITEMS_CLICKABLES;
    private boolean autoDismiss = AndroidObjectItemPickerSettings.DEFAULT_AUTO_DISMISS;
    private int colorBackroundPanel;
    private int colorLines;
    private int colorTextInCenterListView;
    private int colorTextNoInCenterListView;

    private Context mContext;
    private AndroidObjectItemPickerListView androidObjectItemPickerListView;
    private AndroidObjectItemPickerSettings mAndroidObjectItemPickerSettings;
    private AndroidObjectItemPickerListener mAndroidObjectItemPickerListener;

    private List<String> items;
    private int position  = 0;

    private RelativeLayout rootLayout;

    public AndroidObjectItemPicker(Context context) {
        super(context);
        mContext = context;
        if (!isInEditMode()) {
            initView(null);
        }

    }

    public AndroidObjectItemPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        if (!isInEditMode()) {
            initView(attrs);
            getAttributes(attrs);
        }
    }

    public AndroidObjectItemPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        if (!isInEditMode()) {
            initView(attrs);
            getAttributes(attrs);
        }
    }

    private void getAttributes(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.aoip, 0, 0);
        if (typedArray != null) {
            try {
                autoDismiss = typedArray.getBoolean(R.styleable.aoip_autoDismiss, AndroidObjectItemPickerSettings.DEFAULT_AUTO_DISMISS);
                doesItemsAreClickables = typedArray.getBoolean(R.styleable.aoip_itemsClickables, AndroidObjectItemPickerSettings.DEFAULT_ITEMS_CLICKABLES);
                colorBackroundPanel = typedArray.getColor(R.styleable.aoip_backgroundColor, getResources().getColor(R.color.background_panel_aoip));
                colorLines = typedArray.getColor(R.styleable.aoip_linesCenterColor, getResources().getColor(R.color.lines_panel_aoip));
                colorTextInCenterListView = typedArray.getColor(R.styleable.aoip_textCenterColor, getResources().getColor(R.color.text_center_aoip));
                colorTextNoInCenterListView = typedArray.getColor(R.styleable.aoip_textNoCenterColor, getResources().getColor(R.color.text_no_center_aoip));
                int idItems;
                idItems = typedArray.getResourceId(R.styleable.aoip_entries, -1);
                if (idItems != -1) {
                    setItems(mContext, Arrays.asList(getResources().getStringArray(idItems)));
                }

            } catch (Exception e) {

            } finally {
                typedArray.recycle();
            }
        }
    }


    private void initView(AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.android_object_item_picker, this, true);
        rootLayout = (RelativeLayout) view.findViewById(R.id.id_lib_a_rl_root);
        androidObjectItemPickerListView = (AndroidObjectItemPickerListView) view.findViewById(R.id.id_lib_a_lv_list_picker);

        //set listener on every list item
        setItemsClickables(doesItemsAreClickables);

    }

    public void setItemsClickables(boolean doesItemsAreClickables) {
        this.doesItemsAreClickables = doesItemsAreClickables;
        if (androidObjectItemPickerListView != null && androidObjectItemPickerListView.getAndroidObjectItemPickerListViewAdapter() != null) {
            androidObjectItemPickerListView.getAndroidObjectItemPickerListViewAdapter().setItemsClickables(doesItemsAreClickables);
        }


    }


    public interface AndroidObjectItemPickerListener {

        public void onItemClickAndroidObjectItemPicker(int which, int position, String valueResult);

    }


    public void slide() {
        int position = 0;
        if (items != null) {
            position = items.size() / 2;
        }
        slide(position);
    }

    public void slide(final int position) {
        if (!isPanelShown()) {
            slideUp(position);
        }
    }


    public void slide(SLIDE slide) {
        if (slide == SLIDE.UP) {
            if (!isPanelShown()) {
                int position = 0;
                if (items != null) {
                    position = items.size() / 2;
                }
                slideUp(position);
            }
        }
    }

    public void slideUp(int position) {
        //Render to do the blur effect
        this.position = position;

    }

    public void setAutoDismiss(boolean autoDismiss) {
        this.autoDismiss = autoDismiss;
    }

    public void setBackgroundColorPanel(int color) {
        colorBackroundPanel = color;
    }

    public void setLinesColor(int color) {
        colorLines = color;
    }

    private void setTextColorsListView() {
        setColorTextCenter(colorTextInCenterListView);
        setColorTextNoCenter(colorTextNoInCenterListView);
    }

    public boolean isPanelShown() {
        return rootLayout.getVisibility() == View.VISIBLE;
    }

    public void setItems(Context context, List<String> items) {
        if (items != null) {
            setItems(context, items, 0, items.size() / 2);
        }
    }

    public void setItems(Context context, List<String> items, int which, int position) {
        if (items != null) {
            this.items = items;
            androidObjectItemPickerListView.setItems(context, items, which, position, doesItemsAreClickables);
            setTextColorsListView();
        }
    }

    public void setColorTextCenter(int color) {
        if (androidObjectItemPickerListView != null && androidObjectItemPickerListView.getAndroidObjectItemPickerListViewAdapter() != null) {

            int newColor;
            try {
                newColor = getResources().getColor(color);
            } catch (Resources.NotFoundException e) {
                newColor = color;
            }
            colorTextInCenterListView = newColor;
            androidObjectItemPickerListView.getAndroidObjectItemPickerListViewAdapter().setColorTextCenter(newColor);
        }
    }

    public void setColorTextNoCenter(int color) {
        if (androidObjectItemPickerListView != null && androidObjectItemPickerListView.getAndroidObjectItemPickerListViewAdapter() != null) {
            int newColor;
            try {
                newColor = getResources().getColor(color);
            } catch (Resources.NotFoundException e) {
                newColor = color;
            }
            colorTextNoInCenterListView = newColor;
            androidObjectItemPickerListView.getAndroidObjectItemPickerListViewAdapter().setColorTextNoCenter(newColor);
        }
    }

    private void showPanelPickerUI() {
        rootLayout.setVisibility(View.VISIBLE);
        setBackgroundPanel();
        setBackgroundLines();
    }


    private void setBackgroundPanel() {
        int color;
        try {
            color = getResources().getColor(colorBackroundPanel);
        } catch (Resources.NotFoundException e) {
            color = colorBackroundPanel;
        }
        rootLayout.setBackgroundColor(color);
    }

    private void setBackgroundLines() {
        int color;
        try {
            color = getResources().getColor(colorLines);
        } catch (Resources.NotFoundException e) {
            color = colorLines;
        }

        //Top line
        rootLayout.findViewById(R.id.picker_line_top).setBackgroundColor(color);

        //Bottom line
        rootLayout.findViewById(R.id.picker_line_bottom).setBackgroundColor(color);
    }

    public void setOnClickItemPickerUIListener(final AndroidObjectItemPickerListener listener) {
        this.mAndroidObjectItemPickerListener = listener;

        androidObjectItemPickerListView.setOnItemClickAndroidObjectItemPicker(
                new AndroidObjectItemPickerListView.AndroidObjectItemPickerClickListener() {
                    @Override
                    public void onItemClickAndroidObjectItemPickers(int which, int position,
                                                                    String valueResult) {
                        if (autoDismiss) {
                            slide(position);
                        }

                        if (mAndroidObjectItemPickerListener == null) {
                            throw new IllegalStateException(
                                    "You must assign a valid PickerUI.PickerUIItemClickListener first!");
                        }
                        mAndroidObjectItemPickerListener.onItemClickAndroidObjectItemPicker(which, position, valueResult);
                    }
                });
    }

    public void setSettings(AndroidObjectItemPickerSettings pickerUISettings) {
        mAndroidObjectItemPickerSettings = pickerUISettings;
        setColorTextCenter(pickerUISettings.getColorTextCenter());
        setColorTextNoCenter(pickerUISettings.getColorTextNoCenter());
        setItems(mContext, pickerUISettings.getItems());
        setBackgroundColorPanel(pickerUISettings.getBackgroundColor());
        setLinesColor(pickerUISettings.getLinesColor());
        setItemsClickables(pickerUISettings.areItemsClickables());
        setAutoDismiss(pickerUISettings.isAutoDismiss());
    }

    @Override
    public Parcelable onSaveInstanceState() {

        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putParcelable("stateSettings", mAndroidObjectItemPickerSettings);
        //save everything
        bundle.putBoolean("stateIsPanelShown", isPanelShown());
        bundle.putInt("statePosition", androidObjectItemPickerListView.getItemInListCenter());
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {

        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            //load everything
            AndroidObjectItemPickerSettings pickerUISettings = bundle.getParcelable("stateSettings");
            if (pickerUISettings != null) {
                setSettings(pickerUISettings);
            }

            boolean stateIsPanelShown = bundle.getBoolean("stateIsPanelShown");
            if (stateIsPanelShown) {

                final int statePosition = bundle.getInt("statePosition");

                ViewTreeObserver observer = getViewTreeObserver();
                observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        slideUp(statePosition);

                        if (android.os.Build.VERSION.SDK_INT
                                >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        } else {
                            //noinspection deprecation
                            getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        }

                    }
                });

            }
            state = bundle.getParcelable("instanceState");
        }
        super.onRestoreInstanceState(state);
    }

    public enum SLIDE {
        UP,
        DOWN
    }


}

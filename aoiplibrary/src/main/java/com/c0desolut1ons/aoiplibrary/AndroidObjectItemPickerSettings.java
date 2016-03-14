package com.c0desolut1ons.aoiplibrary;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.List;

/**
 * Main libray class.
 * Used for handling picker component and subcomponents.
 *
 * @author Nenad Stojnic
 */
public class AndroidObjectItemPickerSettings implements Parcelable {

    public static final Parcelable.Creator<AndroidObjectItemPickerSettings> CREATOR
            = new Parcelable.Creator<AndroidObjectItemPickerSettings>() {
        public AndroidObjectItemPickerSettings createFromParcel(Parcel source) {
            return new AndroidObjectItemPickerSettings(source);
        }

        public AndroidObjectItemPickerSettings[] newArray(int size) {
            return new AndroidObjectItemPickerSettings[size];
        }
    };

    public static boolean DEFAULT_AUTO_DISMISS = true;

    public static boolean DEFAULT_ITEMS_CLICKABLES = true;

    private List<String> mItems;
    private int mColorTextCenter;
    private int mColorTextNoCenter;
    private int mBackgroundColor;
    private int mLinesColor;
    private boolean mAutoDismiss;
    private boolean mItemsClickables;
    private float mBlurDownScaleFactor;
    private int mBlurRadius;
    private int mBlurFilterColor;
    private boolean mUseBlur;
    private boolean mUseBlurRenderscript;

    private AndroidObjectItemPickerSettings(Builder builder) {
        setItems(builder.mItems);
        setColorTextCenter(builder.mColorTextCenter);
        setColorTextNoCenter(builder.mColorTextNoCenter);
        setBackgroundColor(builder.mBackgroundColor);
        setLinesColor(builder.mLinesColor);
        setItemsClickables(builder.mItemsClickables);
        setAutoDismiss(builder.mAutoDismiss);
        setBlurFilterColor(builder.mFilterColor);
    }

    private AndroidObjectItemPickerSettings(Parcel in) {
        in.readStringList(this.mItems);
        this.mColorTextCenter = in.readInt();
        this.mColorTextNoCenter = in.readInt();
        this.mBackgroundColor = in.readInt();
        this.mLinesColor = in.readInt();
        this.mAutoDismiss = in.readByte() != 0;
        this.mItemsClickables = in.readByte() != 0;
        this.mBlurDownScaleFactor = in.readFloat();
        this.mBlurRadius = in.readInt();
        this.mBlurFilterColor = in.readInt();
        this.mUseBlur = in.readByte() != 0;
        this.mUseBlurRenderscript = in.readByte() != 0;
    }

    public List<String> getItems() {
        return mItems;
    }

    void setItems(List<String> items) {
        mItems = items;
    }

    public int getColorTextCenter() {
        return mColorTextCenter;
    }

    void setColorTextCenter(int colorTextCenter) {
        mColorTextCenter = colorTextCenter;
    }

    public int getColorTextNoCenter() {
        return mColorTextNoCenter;
    }

    void setColorTextNoCenter(int colorTextNoCenter) {
        mColorTextNoCenter = colorTextNoCenter;
    }

    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    void setBackgroundColor(int backgroundColor) {
        mBackgroundColor = backgroundColor;
    }

    public int getLinesColor() {
        return mLinesColor;
    }

    void setLinesColor(int linesColor) {
        mLinesColor = linesColor;
    }

    public boolean areItemsClickables() {
        return mItemsClickables;
    }

    void setItemsClickables(boolean itemsClickables) {
        mItemsClickables = itemsClickables;
    }

    public boolean isAutoDismiss() {
        return mAutoDismiss;
    }

    void setAutoDismiss(boolean autoDismiss) {
        mAutoDismiss = autoDismiss;
    }

    void setBlurFilterColor(int blurFilterColor) {
        mBlurFilterColor = blurFilterColor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.mItems);
        dest.writeInt(this.mColorTextCenter);
        dest.writeInt(this.mColorTextNoCenter);
        dest.writeInt(this.mBackgroundColor);
        dest.writeInt(this.mLinesColor);
        dest.writeByte(mAutoDismiss ? (byte) 1 : (byte) 0);
        dest.writeByte(mItemsClickables ? (byte) 1 : (byte) 0);
        dest.writeFloat(this.mBlurDownScaleFactor);
        dest.writeInt(this.mBlurRadius);
        dest.writeInt(this.mBlurFilterColor);
        dest.writeByte(mUseBlur ? (byte) 1 : (byte) 0);
        dest.writeByte(mUseBlurRenderscript ? (byte) 1 : (byte) 0);
    }

    public static final class Builder {

        private List<String> mItems;
        private int mColorTextCenter = R.color.text_center_aoip;
        private int mColorTextNoCenter = R.color.text_no_center_aoip;
        private int mBackgroundColor = R.color.background_panel_aoip;
        private int mLinesColor = R.color.lines_panel_aoip;
        private boolean mItemsClickables = DEFAULT_ITEMS_CLICKABLES;
        private boolean mAutoDismiss = DEFAULT_AUTO_DISMISS;
        private int mFilterColor = -1;

        public Builder() {
        }

        private Builder(Builder builder) {
            //mUseBlurRenderscript = builder.mUseBlurRenderscript;
            //mUseBlur = builder.mUseBlur;
        }

        public Builder withItems(List<String> mItems) {
            this.mItems = mItems;
            return this;
        }

        public Builder withColorTextCenter(int mColorTextCenter) {
            this.mColorTextCenter = mColorTextCenter;
            return this;
        }

        public Builder withColorTextNoCenter(int mColorTextNoCenter) {
            this.mColorTextNoCenter = mColorTextNoCenter;
            return this;
        }

        public Builder withBackgroundColor(int mBackgroundColor) {
            this.mBackgroundColor = mBackgroundColor;
            return this;
        }

        public Builder withLinesColor(int mLinesColor) {
            this.mLinesColor = mLinesColor;
            return this;
        }

        public Builder withItemsClickables(boolean mItemsClickables) {
            this.mItemsClickables = mItemsClickables;
            return this;
        }

        public Builder withAutoDismiss(boolean mAutoDismiss) {
            this.mAutoDismiss = mAutoDismiss;
            return this;
        }

        public AndroidObjectItemPickerSettings build() {
            return new AndroidObjectItemPickerSettings(this);
        }

    }
}

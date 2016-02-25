package com.c0desolut1ons.aoiplibrary;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import java.text.AttributedCharacterIterator;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Main libray class.
 * Used for handle picker component and subcomponents.
 *
 * @author Nenad Stojnic
 * @email nenad.stojnic@gmail.com
 * @company dzomba.net
 * @date 23.02.2016 18:09
 */
public class AndroidObjectItemPicker extends RelativeLayout{

    private Context mContext;

    @Bind(R.id.id_lib_a_rl_root) RelativeLayout rootLayout;

    public AndroidObjectItemPicker(Context context){
        super(context);
        mContext = context;
        if(!isInEditMode()){
            initView(null);
        }

    }

    public AndroidObjectItemPicker(Context context, AttributeSet attrs){
        super(context, attrs);
        mContext = context;
        if(!isInEditMode()){
            initView(attrs);
        }
    }

    public AndroidObjectItemPicker(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        mContext = context;
        if(!isInEditMode()){
            initView(attrs);
        }
    }

    private void initView(AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.android_object_item_picker, this, true);
        ButterKnife.bind(this, view);
    }
}

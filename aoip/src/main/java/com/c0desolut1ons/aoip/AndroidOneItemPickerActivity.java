package com.c0desolut1ons.aoip;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;

import com.c0desolut1ons.aoiplibrary.AndroidObjectItemPicker;
import com.c0desolut1ons.aoiplibrary.AndroidObjectItemPickerSettings;

import java.util.List;
import java.util.Random;

public class AndroidOneItemPickerActivity extends AppCompatActivity {

    private AndroidObjectItemPicker mPickerUI;
    private CheckBox mRandomColor;
    private CheckBox mUseBlur;
    private CheckBox mItemsClickables;
    private CheckBox mAutoDismiss;
    private Button btSlide;
    private int currentPosition = -1;
    private List<String> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_one_item_picker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_android_one_item_picker, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void findViews() {
        btSlide = (Button) findViewById(R.id.bt_slide);
        mPickerUI = (AndroidObjectItemPicker) findViewById(R.id.picker_ui_view);
        mRandomColor = (CheckBox) findViewById(R.id.cb_random_color);
        mUseBlur = (CheckBox) findViewById(R.id.cb_use_blur);
        mItemsClickables = (CheckBox) findViewById(R.id.cb_items_clickables);
        mAutoDismiss = (CheckBox) findViewById(R.id.cb_auto_dismiss);
    }

    private void setListeners() {
        btSlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int randomColor = -1;

                if (mRandomColor.isChecked()) {
                    randomColor = getRandomColor();
                }

                AndroidObjectItemPickerSettings pickerUISettings =
                        new AndroidObjectItemPickerSettings.Builder().withItems(options)
                                .withBackgroundColor(randomColor)
                                .withAutoDismiss(mAutoDismiss.isChecked())
                                .withItemsClickables(
                                        mItemsClickables.isChecked())
                                .build();

                mPickerUI.setSettings(pickerUISettings);

                if (currentPosition == -1) {
                    mPickerUI.slide();
                } else {
                    mPickerUI.slide(currentPosition);
                }
            }
        });
    }

    private int getRandomColor() {
        // generate the random integers for r, g and b value
        Random rand = new Random();
        int r = rand.nextInt(255);
        int g = rand.nextInt(255);
        int b = rand.nextInt(255);
        return Color.rgb(r, g, b);
    }

}

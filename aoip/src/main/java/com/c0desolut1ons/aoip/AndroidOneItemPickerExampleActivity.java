package com.c0desolut1ons.aoip;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.c0desolut1ons.aoiplibrary.AndroidObjectItemPicker;

import java.util.Arrays;
import java.util.List;

public class AndroidOneItemPickerExampleActivity extends AppCompatActivity {

    private AndroidObjectItemPicker mAndroidObjectItemPicker;
    private CheckBox mRandomColor;
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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.id_a_fab);

        findViews();

        options = Arrays.asList(getResources().getStringArray(R.array.countries_array));
        //Populate list
        mAndroidObjectItemPicker.setItems(this, options);
        mAndroidObjectItemPicker.setColorTextCenter(R.color.center_font_color);
        mAndroidObjectItemPicker.setColorTextNoCenter(R.color.center_font_color);
        mAndroidObjectItemPicker.setBackgroundColorPanel(R.color.background_picker);
        mAndroidObjectItemPicker.setLinesColor(R.color.background_picker);
        mAndroidObjectItemPicker.setItemsClickables(true);



        mAndroidObjectItemPicker.setOnClickItemPickerUIListener(
                new AndroidObjectItemPicker.AndroidObjectItemPickerListener() {

                    @Override
                    public void onItemClickAndroidObjectItemPicker(int which, int position, String valueResult) {
                        currentPosition = position;
                        Toast.makeText(AndroidOneItemPickerExampleActivity.this, valueResult + "[" + position + "]", Toast.LENGTH_SHORT).show();
                    }
                });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Snackbar.make(v, "Android Object Item Picket application v1.0", Snackbar.LENGTH_LONG).setAction("Action", null).show();

//                int randomColor = -1;
//
//                if (mRandomColor.isChecked()) {
//                    randomColor = getRandomColor();
//                }
//
//                AndroidObjectItemPickerSettings androidObjectItemPickerSettings =
//                        new AndroidObjectItemPickerSettings.Builder().withItems(options)
//                                .withBackgroundColor(randomColor)
//                                .withAutoDismiss(mAutoDismiss.isChecked())
//                                .withItemsClickables(
//                                        mItemsClickables.isChecked())
//                                .build();
//
//                mAndroidObjectItemPicker.setSettings(androidObjectItemPickerSettings);
//
//                if (currentPosition == -1) {
//                    mAndroidObjectItemPicker.slide();
//                } else {
//                    mAndroidObjectItemPicker.slide(currentPosition);
//                }
            }
        });

        mAndroidObjectItemPicker.setItemInCenter(4);

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
        mAndroidObjectItemPicker = (AndroidObjectItemPicker) findViewById(R.id.picker_ui_view);
    }

}

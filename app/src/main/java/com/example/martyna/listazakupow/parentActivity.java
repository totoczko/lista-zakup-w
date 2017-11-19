package com.example.martyna.listazakupow;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class parentActivity extends AppCompatActivity {
    public static final String PREFERENCES_NAME = "myPreferences";
    public static final String PREFERENCES_SIZE_FIELD = "sizeField";
    public static final String PREFERENCES_COLOR_FIELD = "colorField";
    public SharedPreferences preferences;
    public String sizeFromPreferences;
    public int colorFromPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);

        //get shared preferences (font size & color)
        customGetSharedPreferences();
        changeColor((ViewGroup) findViewById(android.R.id.content));
    }

    @Override
    protected void onResume() {
        super.onResume();

        //get shared preferences (font size & color)
        customGetSharedPreferences();
        changeColor((ViewGroup) findViewById(android.R.id.content));
    }

    public void customGetSharedPreferences(){
        preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
        sizeFromPreferences = preferences.getString(PREFERENCES_SIZE_FIELD, "");
        colorFromPreferences = preferences.getInt(PREFERENCES_COLOR_FIELD,0);
    }

    public void changeColor(ViewGroup parentLayout){
        for (int count=0; count < parentLayout.getChildCount(); count++){
            View view = parentLayout.getChildAt(count);
            if(view instanceof TextView && !(view instanceof Button) && !(view instanceof EditText) && !(view instanceof Spinner)){
                if (colorFromPreferences == 0){
                    ((TextView)view).setTextColor(Color.BLACK);
                }else if (colorFromPreferences == 1){
                    ((TextView)view).setTextColor(Color.RED);
                }else if (colorFromPreferences == 2){
                    ((TextView)view).setTextColor(Color.GREEN);
                }else if (colorFromPreferences == 3){
                    ((TextView)view).setTextColor(Color.BLUE);
                }else if (colorFromPreferences == 4){
                    ((TextView)view).setTextColor(Color.MAGENTA);
                }
            } else if(view instanceof ViewGroup && !(view instanceof Button) && !(view instanceof EditText) && !(view instanceof Spinner)){
                changeColor((ViewGroup)view);
            }
        }
    }

}

package com.example.martyna.listazakupow;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SettingsActivity extends parentActivity {
    EditText edit_size;
    Spinner edit_color;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get elements
        setContentView(R.layout.activity_settings);
        edit_size = (EditText) findViewById(R.id.edit_size);
        edit_color = (Spinner) findViewById(R.id.edit_color);
        save = (Button) findViewById(R.id.save);

        //populate color spinner
        String[] colors = new String[]{"czarny", "czerwony", "zielony", "niebieski", "różowy"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, colors);
        edit_color.setAdapter(adapter);

        applyDataToInputs();
}

    public void onClickSave(View v) {
        saveData();

        //change font size & color
        customGetSharedPreferences();
        changeColor((ViewGroup) findViewById(R.id.settings_activity));
        showToast("Zapisano");
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void saveData() {
        //create shared preferences editor
        SharedPreferences.Editor preferencesEditor = preferences.edit();

        //get values from editText and spinner
        String editSizeData = edit_size.getText().toString();
        int editColorData = edit_color.getSelectedItemPosition();

        //save them into editor
        preferencesEditor.putString(PREFERENCES_SIZE_FIELD, editSizeData);
        preferencesEditor.putInt(PREFERENCES_COLOR_FIELD, editColorData);
        preferencesEditor.commit();
}

    private void applyDataToInputs() {
        edit_size.setText(sizeFromPreferences);
        edit_color.setSelection(colorFromPreferences);
    }


}

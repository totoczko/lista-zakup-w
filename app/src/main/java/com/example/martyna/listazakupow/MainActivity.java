package com.example.martyna.listazakupow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends parentActivity {

    Button moja_lista, ustawienia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        moja_lista = (Button)findViewById(R.id.moja_lista);
        ustawienia = (Button)findViewById(R.id.ustawienia);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void click_moja_lista(View v){
        Intent intent_lista = new Intent(this, ListActivity.class);
        startActivity(intent_lista);
    }

    public void click_ustawienia(View v){
        Intent intent_settings = new Intent(this, SettingsActivity.class);
        startActivity(intent_settings);
    }

}

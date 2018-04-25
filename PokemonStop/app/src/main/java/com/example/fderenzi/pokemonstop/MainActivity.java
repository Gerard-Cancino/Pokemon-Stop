package com.example.fderenzi.pokemonstop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private Button map;
    private Button history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildGUIByCode();
    }

    public void buildGUIByCode(){
        LinearLayout linearLayout = new LinearLayout(this);
        map = new Button(this);
        map.setText("Go to Map");
        history = new Button(this);
        history.setText("Go to History");
        linearLayout.addView(map);
        linearLayout.addView(history);

        setContentView(linearLayout);

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(mapIntent);
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyIntent = new Intent(getApplicationContext(), HistoryActivity.class);
                startActivity(historyIntent);
            }
        });
    }


}

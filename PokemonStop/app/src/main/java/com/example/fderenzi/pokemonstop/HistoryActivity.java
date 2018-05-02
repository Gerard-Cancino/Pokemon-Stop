package com.example.fderenzi.pokemonstop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    private DatabaseManager dbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager= new DatabaseManager (this);
        setContentView(R.layout.activity_history);
        updateView();
    }
    public void updateView(){
        ArrayList<Result> results= dbManager.selectAll();
        if( results.size ()>0)
        {
            String finalString="";
            TextView resultDisplay= (TextView) findViewById(R.id.historyDisplay);

            for (Result result : results)
            {
                finalString += result.getOpponentName() + ": " + result.getResult() + "\n";
            }
            resultDisplay.setText(finalString);
        }
    }
}

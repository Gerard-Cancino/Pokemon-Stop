package com.example.fderenzi.pokemonstop;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class BattleActivity extends AppCompatActivity {
    private DatabaseManager dbManager;
    private Ability playerAbility;
    private Ability monsterAbility;
    private Monster opponent;
    private Monster player;
    private Battle fight;
    private ProgressBar opponentBar;
    private ProgressBar playersBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DatabaseManager(this);
        setContentView(R.layout.activity_battle);

        ////////
        playerAbility = new Ability("slash", 1, "pokemon slashes its opponent");
        monsterAbility = new Ability("growl", 33, "Lowers pokemons defense");
        opponent = new Monster("Curriculum Writer Gupta", monsterAbility);
        player = new Monster("Guten", playerAbility);
        fight = new Battle(player, opponent);
        TextView opponentName = (TextView)findViewById(R.id.OpponentName);
        opponentName.setText(opponent.getName());
        playersBar = findViewById(R.id.PLayerHP);
        opponentBar = findViewById(R.id.OpponentHP);
        playersBar.setMax(100);
        opponentBar.setMax(100);
        playersBar.setProgress(player.getHealth());
        opponentBar.setProgress(opponent.getHealth());
        Drawable bgDrawable = playersBar.getProgressDrawable();
    }

    public void attackPressed(View v)
    {
        int yellow = getResources().getColor(R.color.yellow);
        int red = getResources().getColor(R.color.red);
        int p = fight.play();
        if(p == 1)
        {
            //Toast.makeText(this,player.getName(),Toast.LENGTH_LONG).show();
            //Result resultToInsert = new Result(opponent.getName(), "Win");
            try{
                dbManager.insert(opponent.getName(), "Win");
            }catch(NumberFormatException nfe){
                Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
            }
            this.finish();

        }
        else if(p ==2)
        {
            //Toast.makeText(this,opponent.getName(),Toast.LENGTH_LONG).show();
            //Result resultToInsert = new Result(opponent.getName(), "Loss");
            try{
                dbManager.insert(opponent.getName(), "Loss");
            }catch(NumberFormatException nfe){
                Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
            }
            this.finish();
        }
        else
        {
            playersBar.setProgress(player.getHealth(), true);
            if(player.getHealth() < 50) {
                Drawable bgDrawable = playersBar.getProgressDrawable();
                bgDrawable.setColorFilter(yellow, android.graphics.PorterDuff.Mode.MULTIPLY);
                playersBar.setProgressDrawable(bgDrawable);
            }
            if(player.getHealth() < 25) {
                Drawable bgDrawable = playersBar.getProgressDrawable();
                bgDrawable.setColorFilter(red, android.graphics.PorterDuff.Mode.MULTIPLY);
                playersBar.setProgressDrawable(bgDrawable);
            }
            opponentBar.setProgress(opponent.getHealth(),true);
            if(opponent.getHealth() < 50) {
                Drawable bgDrawable = opponentBar.getProgressDrawable();
                bgDrawable.setColorFilter(yellow, android.graphics.PorterDuff.Mode.MULTIPLY);
                opponentBar.setProgressDrawable(bgDrawable);
            }
            if(opponent.getHealth() < 25) {
                Drawable bgDrawable = opponentBar.getProgressDrawable();
                bgDrawable.setColorFilter(red, android.graphics.PorterDuff.Mode.MULTIPLY);
                opponentBar.setProgressDrawable(bgDrawable);
            }
            //Toast.makeText(this,"Attack Again",Toast.LENGTH_LONG).show();
        }

    }
}

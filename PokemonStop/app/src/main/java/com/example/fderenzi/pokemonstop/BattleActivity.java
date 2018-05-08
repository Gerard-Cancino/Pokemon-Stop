package com.example.fderenzi.pokemonstop;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class BattleActivity extends AppCompatActivity {
    private DatabaseManager dbManager;
    private Ability slash;
    private Ability tackle;
    private Ability pound;
    private Ability study;
    private Monster opponent;
    private Monster player;
    private Battle fight;
    private ProgressBar opponentBar;
    private ProgressBar playersBar;
    private int counter1 = 15;
    private int counter2 = 15;
    private int counter3 = 10;
    private int counter4 = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DatabaseManager(this);
        setContentView(R.layout.activity_battle);

        ////////
        study = new Ability("study", 5, "Lowers pokemons defense");
        slash = new Ability("slash", 10, "pokemon slashes its opponent");
        tackle = new Ability("tackle", 15, "Lowers pokemons defense");
        pound = new Ability("pound", 20, "Lowers pokemons defense");

        opponent = new Monster("Eevee", study, slash, tackle, pound);
        player = new Monster("Guten", study, slash, tackle, pound);

        fight = new Battle(player, opponent);
        TextView opponentName = (TextView)findViewById(R.id.OpponentName);
        opponentName.setText(opponent.getName());

        Button attack1 = (Button)findViewById(R.id.attack1);
        Button attack2 = (Button)findViewById(R.id.attack2);
        Button attack3 = (Button)findViewById(R.id.attack3);
        Button attack4 = (Button)findViewById(R.id.attack4);
        attack1.setText(player.getAbility1().getAName().toString());
        attack2.setText(player.getAbility2().getAName().toString());
        attack3.setText(player.getAbility3().getAName().toString());
        attack4.setText(player.getAbility4().getAName().toString());


        playersBar = findViewById(R.id.PLayerHP);
        opponentBar = findViewById(R.id.OpponentHP);
        playersBar.setMax(100);
        opponentBar.setMax(100);
        playersBar.setProgress(player.getHealth());
        opponentBar.setProgress(opponent.getHealth());
        Drawable bgDrawable = playersBar.getProgressDrawable();
    }

    public void attackPressed1(View v)
    {
        Button attack1 = (Button)findViewById(R.id.attack1);
        int yellow = getResources().getColor(R.color.yellow);
        int red = getResources().getColor(R.color.red);
        int move = 1;
        int p = fight.play(move);
        if(p == 1)
        {
            //PLAYER WIN
            try{
                dbManager.insert(opponent.getName(), "Win");
            }catch(NumberFormatException nfe){
                Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
            }
            this.finish();

        }
        else if(p ==2)
        {
            //OPPONENT WIN
            try{
                dbManager.insert(opponent.getName(), "Loss");
            }catch(NumberFormatException nfe){
                Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
            }
            this.finish();
        }
        else
        {
            //BATTLE CONTINUE
            playersBar.setProgress(player.getHealth(), true);
            if(player.getHealth() < 50) {
                Drawable bgDrawable = playersBar.getProgressDrawable();
                bgDrawable.setColorFilter(yellow, android.graphics.PorterDuff.Mode.MULTIPLY);
                playersBar.setProgressDrawable(bgDrawable);
            }
            if(player.getHealth() < 15) {
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
            if(opponent.getHealth() < 15) {
                Drawable bgDrawable = opponentBar.getProgressDrawable();
                bgDrawable.setColorFilter(red, android.graphics.PorterDuff.Mode.MULTIPLY);
                opponentBar.setProgressDrawable(bgDrawable);
            }
            counter1--;
            if(counter1 == 0)
                attack1.setEnabled(false);
        }

    }

    public void attackPressed2(View v)
    {
        Button attack2 = (Button)findViewById(R.id.attack2);
        int yellow = getResources().getColor(R.color.yellow);
        int red = getResources().getColor(R.color.red);
        int move = 2;
        int p = fight.play(move);
        if(p == 1)
        {
            //PLAYER WIN
            try{
                dbManager.insert(opponent.getName(), "Win");
            }catch(NumberFormatException nfe){
                Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
            }
            this.finish();

        }
        else if(p ==2)
        {
            //OPPONENT WIN
            try{
                dbManager.insert(opponent.getName(), "Loss");
            }catch(NumberFormatException nfe){
                Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
            }
            this.finish();
        }
        else
        {
            //BATTLE CONTINUES
            playersBar.setProgress(player.getHealth(), true);
            if(player.getHealth() < 50) {
                Drawable bgDrawable = playersBar.getProgressDrawable();
                bgDrawable.setColorFilter(yellow, android.graphics.PorterDuff.Mode.MULTIPLY);
                playersBar.setProgressDrawable(bgDrawable);
            }
            if(player.getHealth() < 15) {
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
            if(opponent.getHealth() < 15) {
                Drawable bgDrawable = opponentBar.getProgressDrawable();
                bgDrawable.setColorFilter(red, android.graphics.PorterDuff.Mode.MULTIPLY);
                opponentBar.setProgressDrawable(bgDrawable);
            }
            counter2--;
            if(counter2 == 0)
                attack2.setEnabled(false);
        }

    }

    public void attackPressed3(View v)
    {
        Button attack3 = (Button)findViewById(R.id.attack3);
        int yellow = getResources().getColor(R.color.yellow);
        int red = getResources().getColor(R.color.red);
        int move = 3;
        int p = fight.play( move);
        if(p == 1)
        {
            //PLAYER WIN
            try{
                dbManager.insert(opponent.getName(), "Win");
            }catch(NumberFormatException nfe){
                Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
            }
            this.finish();

        }
        else if(p ==2)
        {
            //OPPONENT WIN
            try{
                dbManager.insert(opponent.getName(), "Loss");
            }catch(NumberFormatException nfe){
                Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
            }
            this.finish();
        }
        else
        {
            //BATTLE CONTINUES
            playersBar.setProgress(player.getHealth(), true);
            if(player.getHealth() < 50) {
                Drawable bgDrawable = playersBar.getProgressDrawable();
                bgDrawable.setColorFilter(yellow, android.graphics.PorterDuff.Mode.MULTIPLY);
                playersBar.setProgressDrawable(bgDrawable);
            }
            if(player.getHealth() < 15) {
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
            if(opponent.getHealth() < 15) {
                Drawable bgDrawable = opponentBar.getProgressDrawable();
                bgDrawable.setColorFilter(red, android.graphics.PorterDuff.Mode.MULTIPLY);
                opponentBar.setProgressDrawable(bgDrawable);
            }
            counter3--;
            if(counter3 == 0)
                attack3.setEnabled(false);
        }

    }

    public void attackPressed4(View v)
    {
        Button attack4 = (Button)findViewById(R.id.attack4);
        int yellow = getResources().getColor(R.color.yellow);
        int red = getResources().getColor(R.color.red);
        int move = 4;
        int p = fight.play(move);
        if(p == 1)
        {
            //PLAYER WIN
            try{
                dbManager.insert(opponent.getName(), "Win");
            }catch(NumberFormatException nfe){
                Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
            }
            this.finish();

        }
        else if(p ==2)
        {
            //OPPONENT WIN
            try{
                dbManager.insert(opponent.getName(), "Loss");
            }catch(NumberFormatException nfe){
                Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
            }
            this.finish();
        }
        else
        {
            //BATTLE CONTINUES
            playersBar.setProgress(player.getHealth(), true);
            if(player.getHealth() < 50) {
                Drawable bgDrawable = playersBar.getProgressDrawable();
                bgDrawable.setColorFilter(yellow, android.graphics.PorterDuff.Mode.MULTIPLY);
                playersBar.setProgressDrawable(bgDrawable);
            }
            if(player.getHealth() < 15) {
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
            if(opponent.getHealth() < 15) {
                Drawable bgDrawable = opponentBar.getProgressDrawable();
                bgDrawable.setColorFilter(red, android.graphics.PorterDuff.Mode.MULTIPLY);
                opponentBar.setProgressDrawable(bgDrawable);
            }
            counter4--;
            if(counter4 == 0)
                attack4.setEnabled(false);
        }

    }

    public void resetCounters(){
        counter1 = 15;
        counter2 = 15;
        counter3 = 10;
        counter4 = 5;
        Button attack1 = (Button)findViewById(R.id.attack1);
        Button attack2 = (Button)findViewById(R.id.attack2);
        Button attack3 = (Button)findViewById(R.id.attack3);
        Button attack4 = (Button)findViewById(R.id.attack4);
        attack1.setEnabled(true);
        attack2.setEnabled(true);
        attack3.setEnabled(true);
        attack4.setEnabled(true);

    }
}

package com.example.fderenzi.pokemonstop;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;


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
    private int counter1 = 5;
    private int counter2 = 5;
    private int counter3 = 5;
    private int counter4 = 5;
    private MediaPlayer battleMusic;
    private MediaPlayer cry;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        battleMusic = MediaPlayer.create(this, R.raw.battle);
        battleMusic.start();

        study = new Ability("study", 5, "Lowers pokemons defense");
        slash = new Ability("slash", 10, "pokemon slashes its opponent");
        tackle = new Ability("tackle", 15, "Lowers pokemons defense");
        pound = new Ability("pound", 20, "Lowers pokemons defense");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String aName1 = extras.getString("a1Name");
            int damage1 = extras.getInt("a1Damage");
            String description1 = extras.getString("a1Desc");
            Ability a1 = new Ability(aName1,damage1,description1);

            String aName2 = extras.getString("a2Name");
            int damage2 = extras.getInt("a2Damage");
            String description2 = extras.getString("a2Desc");
            Ability a2 = new Ability(aName2,damage2,description2);

            String aName3 = extras.getString("a3Name");
            int damage3 = extras.getInt("a3Damage");
            String description3 = extras.getString("a3Desc");
            Ability a3 = new Ability(aName3,damage3,description3);

            String aName4 = extras.getString("a4Name");
            int damage4 = extras.getInt("a4Damage");
            String description4 = extras.getString("a4Desc");
            Ability a4 = new Ability(aName4,damage4,description4);

            String name = extras.getString("monName" );

            opponent = new Monster(name,a1,a2,a3,a4);
        }

        if(opponent==null)
            opponent = new Monster("Meowth", study, slash, tackle, pound);


        dbManager = new DatabaseManager(this);
        setContentView(R.layout.activity_battle);

        ImageView gifImageView = (ImageView)findViewById(R.id.gif);
        String gifName = opponent.getName().toLowerCase();
        gifImageView.setImageResource(getResources().getIdentifier(gifName, "drawable", getPackageName()));

        String cryName = opponent.getName().toLowerCase();
        int cryID = getResources().getIdentifier(cryName, "raw", getPackageName());
        cry = MediaPlayer.create(this, cryID);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cry.start();
            }
        }, 1000);



        player = new Monster("Guten", study, slash, tackle, pound);


        fight = new Battle(player, opponent);
        TextView opponentName = (TextView)findViewById(R.id.OpponentName);
        opponentName.setText(opponent.getName());

        Button attack1 = (Button)findViewById(R.id.attack1);
        Button attack2 = (Button)findViewById(R.id.attack2);
        Button attack3 = (Button)findViewById(R.id.attack3);
        Button attack4 = (Button)findViewById(R.id.attack4);
        attack1.setText(player.getAbility1().getAName().toString() + "\n" + player.getAbility1().getDamage());
        attack2.setText(player.getAbility2().getAName().toString() + "\n" + player.getAbility2().getDamage());
        attack3.setText(player.getAbility3().getAName().toString() + "\n" + player.getAbility3().getDamage());
        attack4.setText(player.getAbility4().getAName().toString() + "\n" + player.getAbility4().getDamage());
        TextView opponentMoveUsed = (TextView)findViewById(R.id.opponentMoveUsed);
        opponentMoveUsed.setText(opponent.getName() + " used");

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
        final MediaPlayer winSound = MediaPlayer.create(this, R.raw.win );
        final MediaPlayer lossSound = MediaPlayer.create(this, R.raw.loss );
        Button attack1 = (Button)findViewById(R.id.attack1);
        int yellow = getResources().getColor(R.color.yellow);
        int red = getResources().getColor(R.color.red);
        int move = 1;
        int randomNumber = (int)(Math.random()*4)+1;
        int p = fight.play(move,randomNumber);
        TextView opponentAttackName = (TextView)findViewById(R.id.opponentAttackName);
        opponentAttackName.setText(fight.moveUsed(randomNumber));
        if(p == 1)
        {
            //PLAYER WIN
            try{
                dbManager.insert(opponent.getName(), "Win");
            }catch(NumberFormatException nfe){
                Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
            }
            battleMusic.stop();
            winSound.start();
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
            battleMusic.stop();
            lossSound.start();
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
        final MediaPlayer winSound = MediaPlayer.create(this, R.raw.win );
        final MediaPlayer lossSound = MediaPlayer.create(this, R.raw.loss );
        Button attack2 = (Button)findViewById(R.id.attack2);
        int yellow = getResources().getColor(R.color.yellow);
        int red = getResources().getColor(R.color.red);
        int move = 2;
        int randomNumber = (int)(Math.random()*4)+1;
        int p = fight.play(move,randomNumber);
        TextView opponentAttackName = (TextView)findViewById(R.id.opponentAttackName);
        opponentAttackName.setText(fight.moveUsed(randomNumber));
        if(p == 1)
        {
            //PLAYER WIN
            try{
                dbManager.insert(opponent.getName(), "Win");
            }catch(NumberFormatException nfe){
                Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
            }
            battleMusic.stop();
            winSound.start();
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
            battleMusic.stop();
            lossSound.start();
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
        final MediaPlayer winSound = MediaPlayer.create(this, R.raw.win );
        final MediaPlayer lossSound = MediaPlayer.create(this, R.raw.loss );
        Button attack3 = (Button)findViewById(R.id.attack3);
        int yellow = getResources().getColor(R.color.yellow);
        int red = getResources().getColor(R.color.red);
        int move = 3;
        int randomNumber = (int)(Math.random()*4)+1;
        int p = fight.play(move,randomNumber);
        TextView opponentAttackName = (TextView)findViewById(R.id.opponentAttackName);
        opponentAttackName.setText(fight.moveUsed(randomNumber));
        if(p == 1)
        {
            //PLAYER WIN
            try{
                dbManager.insert(opponent.getName(), "Win");
            }catch(NumberFormatException nfe){
                Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
            }
            battleMusic.stop();
            winSound.start();
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
            battleMusic.stop();
            lossSound.start();
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
        final MediaPlayer winSound = MediaPlayer.create(this, R.raw.win );
        final MediaPlayer lossSound = MediaPlayer.create(this, R.raw.loss );

        Button attack4 = (Button)findViewById(R.id.attack4);
        int yellow = getResources().getColor(R.color.yellow);
        int red = getResources().getColor(R.color.red);
        int move = 4;
        int randomNumber = (int)(Math.random()*4)+1;
        int p = fight.play(move,randomNumber);
        TextView opponentAttackName = (TextView)findViewById(R.id.opponentAttackName);
        opponentAttackName.setText(fight.moveUsed(randomNumber));
        if(p == 1)
        {
            //PLAYER WIN
            try{
                dbManager.insert(opponent.getName(), "Win");
            }catch(NumberFormatException nfe){
                Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
            }
            battleMusic.stop();
            winSound.start();
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
            battleMusic.stop();
            lossSound.start();
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
        counter1 = 5;
        counter2 = 5;
        counter3 = 5;
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

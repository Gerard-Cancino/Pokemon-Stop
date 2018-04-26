package com.example.fderenzi.pokemonstop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class BattleActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_battle);

        ////////
        playerAbility = new Ability("slash", 10, "pokemon slashes its opponent");
        monsterAbility = new Ability("growl", 5, "Lowers pokemons defense");
        opponent = new Monster("Evee", monsterAbility);
        player = new Monster("Guten", playerAbility);
        fight = new Battle(player, opponent);
        playersBar = findViewById(R.id.PLayerHP);
        opponentBar = findViewById(R.id.OpponentHP);
        playersBar.setMax(100);
        opponentBar.setMax(100);
        playersBar.setProgress(player.getHealth());
        opponentBar.setProgress(opponent.getHealth());
    }

    public void attackPressed(View v)
    {
        fight.Play();
        if(fight.checkWin()== 1)
        {
            playersBar.setProgress(player.getHealth());
            opponentBar.setProgress(opponent.getHealth());
            //Toast.makeText(this,player.getName(),Toast.LENGTH_LONG).show();

        }
        else if(fight.checkWin()==2)
        {
            playersBar.setProgress(player.getHealth());
            opponentBar.setProgress(opponent.getHealth());
            //Toast.makeText(this,opponent.getName(),Toast.LENGTH_LONG).show();

        }
        else
        {
            playersBar.setProgress(player.getHealth());
            opponentBar.setProgress(opponent.getHealth());
            //Toast.makeText(this,"Attack Again",Toast.LENGTH_LONG).show();
        }
        //this.finish();


    }
}

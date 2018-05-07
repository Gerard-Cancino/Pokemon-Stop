package com.example.fderenzi.pokemonstop;

import java.util.Random;

public class Battle {

    private Monster player;
    private Monster opponent;

    public Battle(Monster monster1, Monster monster2){
        player = monster1;
        opponent = monster2;

    }

    public int play(int p){
        int randomNumber = (int)(Math.random()*4)+1;
        //Players turn
        PlayerdamageToOpponent(p);
        OpponentdamageToPlayer(randomNumber);
        if(checkWin() == 1)
        {
            Reset();
            return 1;
        }
        else if(checkWin() == 2)
        {
            Reset();
            return 2;
        }
        else{
            return 0;
        }

    }

    public void PlayerdamageToOpponent(int p){
        int damageofability = 0;
        if(p == 1) {
            damageofability = player.getAbility1().getDamage();
        }else if(p == 2){
            damageofability = player.getAbility2().getDamage();
        }else if(p == 3) {
            damageofability = player.getAbility3().getDamage();
        }else if(p == 4){
            damageofability = player.getAbility4().getDamage();
        }
        opponent.setHealth(opponent.getHealth() - damageofability);
    }

    public void OpponentdamageToPlayer(int p){
        int damageofability = 0;
        if(p == 1) {
            damageofability = player.getAbility1().getDamage();
        }else if(p == 2){
            damageofability = player.getAbility2().getDamage();
        }else if(p == 3) {
            damageofability = player.getAbility3().getDamage();
        }else if(p == 4){
            damageofability = player.getAbility4().getDamage();
        }
        player.setHealth(player.getHealth()- damageofability);
    }


    public int checkWin(){
        if(opponent.getHealth() <= 0){
            return 1;
        }
        else if(player.getHealth() <= 0){
            return 2;
        }
        else{
            return 0;
        }

    }

    public void Reset(){
        player.setHealth(100);
        opponent.setHealth(100);

    }
}
package com.example.fderenzi.pokemonstop;

public class Battle {

    private Monster player;
    private Monster opponent;
    private int turn = 1;

    public Battle(Monster monster1, Monster monster2){
        player = monster1;
        opponent = monster2;

    }

    public int play(){

        //Players turn
        PlayerdamageToOpponent();
        OpponentdamageToPlayer();
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

   public void PlayerdamageToOpponent(){
        int damageofability = player.getAbility().getDamage();
        int healthO = opponent.getHealth();
        opponent.setHealth(opponent.getHealth()-damageofability);
    }

    public void OpponentdamageToPlayer(){
        int damageofability2 = opponent.getAbility().getDamage();
        player.setHealth(player.getHealth()- damageofability2);
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
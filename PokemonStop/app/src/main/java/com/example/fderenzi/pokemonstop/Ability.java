package com.example.fderenzi.pokemonstop;

public class Ability {
    private String aName;
    private int damage;
    private String description;

    public Ability(){
        aName = "default";
        damage = 1;
        description = "";
    }

    public Ability(String n, int d, String des){
        aName = n;
        damage = d;
        description = des;
    }

   public String getAName(){
        return aName;
   }

    public int getDamage(){
        return damage;
    }

    public String getDescription(){
        return description;
    }
}

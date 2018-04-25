package com.example.fderenzi.pokemonstop;

public class Monster
{
    private String name;
    private int heath;
    private Ability ability; //will need to make ability class

    public Monster()
    {
        name = "default";
        heath = 100;
        ability = new Ability();
    }
    public Monster(String newName, Ability move)
    {
        name = newName;
        int heath = 100;
        ability = move;
    }
    public int getHealth()
    {
        return heath;
    }
    public Ability getAbility()
    {
        return ability;
    }
    public void setHealth(int newHeath)
    {
        heath = newHeath;
    }

}

package com.example.fderenzi.pokemonstop;

public class Monster
{
    private String name;
    private int health;
    private Ability ability;

    public Monster()
    {
        name = "default";
        health = 100;
        ability = new Ability();
    }
    public Monster(String newName, Ability move)
    {
        name = newName;
        health = 100;
        ability = move;
    }
    public int getHealth()
    {
        return health;
    }
    public String getName(){
        return name;
    }
    public Ability getAbility()
    {
        return ability;
    }
    public void setHealth(int newHealth)
    {
        health = newHealth;
    }

}

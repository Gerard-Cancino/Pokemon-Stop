package com.example.fderenzi.pokemonstop;

// Object
public class Monster
{
    private String name;
    private int health;
    private Ability ability1;
    private Ability ability2;
    private Ability ability3;
    private Ability ability4;

    public Monster()
    {
        name = "default";
        health = 100;
        ability1 = new Ability();
        ability2 = new Ability();
        ability3 = new Ability();
        ability4 = new Ability();
    }
    public Monster(String newName, Ability move1, Ability move2, Ability move3, Ability move4)
    {
        name = newName;
        health = 100;
        ability1 = move1;
        ability2 = move2;
        ability3 = move3;
        ability4 = move4;
    }
    public int getHealth()
    {
        return health;
    }
    public String getName(){
        return name;
    }
    public void setHealth(int newHealth)
    {
        health = newHealth;
    }

    public Ability getAbility1()
    {
        return ability1;
    }
    public Ability getAbility2()
    {
        return ability2;
    }
    public Ability getAbility3()
    {
        return ability3;
    }
    public Ability getAbility4()
    {
        return ability4;
    }


}

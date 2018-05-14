package com.example.fderenzi.pokemonstop;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class NodeMon {

    private final Node marker1 = new Node(40.799030, -73.575573);
    private final Node marker2 = new Node(40.796685, -73.573142);
    private final Node marker3 = new Node(40.799409, -73.574252);
    private final Node marker4 = new Node(40.721074, -73.729185);

    // Low Damage Attacks
    private Ability Tackle = new Ability("Tackle", 4, "The user tackles the opponent");
    private Ability Rage = new Ability("Rage", 5, "The user attacks with all it's rage");
    private Ability PoisonSting = new Ability("Poison Sting", 4, "The user fires a poison needle");
    private Ability Gust = new Ability("Gust", 5, "The user attacks with a Gust of air");
    private Ability WaterGun = new Ability("Water Gun", 5, "The user sprays the foe with a stream of water");
    private Ability RapidSpin = new Ability("Rapid Spin", 5, "Spins the body at high speed to strike the foe");
    private Ability IceShard = new Ability("Ice Shard", 6, "Throws a shard of ice at the foe");
    private Ability Stomp = new Ability("Stomp", 6, "User stomps on the foe");


    //Medium Damage Attacks
    private Ability Scratch = new Ability("Scratch", 10, "The user scratches the opponent");
    private Ability QuickAttack = new Ability("Quick Attack", 10, "The user quickly attacks the opponent");
    private Ability AquaJet = new Ability("Aqua Jet", 10, "The user fires a jet of water at the opponent");
    private Ability BugBite = new Ability("Bug Bite", 10, "The user bites the foe");
    private Ability Bonemerange = new Ability("Bonemerange", 10, "A boomerang made of bone is thrown to inflict damage");

    //Average Damage Attacks
    private Ability Bite = new Ability("Bite", 15, "The user bites the opponent");
    private Ability IceFang = new Ability("Ice Fang", 15, "The user bites with cold-infused fangs");
    private Ability WingAttack = new Ability("Wing Attack", 15, "The user attacks the foe with its wings");
    private Ability BoneClub = new Ability("Bone Club", 15, "The user attacks the foe a club of bone");
    private Ability IcePunch = new Ability("Ice Punch", 15, "The user hit with cold-infused fist");
    private Ability DragonTail = new Ability("DragonTail", 13, "user strikes the foe with its tail");
    private Ability DragonClaw = new Ability("DragonClaw", 16, "The user slashes the target with huge, sharp claws");
    private Ability HyperFang = new Ability("Hyper Fang", 13, "The user bites the foe");

    //High Damage Attacks
    private Ability Swift = new Ability("Swift", 20, "Attacks the opponent with a flurry of stars");
    private Ability Slash = new Ability("Slash", 20, "The user slashes the opponent with its claws");
    private Ability SkullBash = new Ability("Skull Bash", 30, "The user headbutts the opponent at full steam");
    private Ability Hurricane = new Ability("Hurricane", 30, "The user attacks by wrapping its opponent in a fierce wind that flies up into the sky");
    private Ability BoneRush = new Ability("Bone Rush", 20, "The user strikes at the foe with a hard bone");
    private Ability HydroPump = new Ability("Hydro Pump", 25, "Blasts water at high power to strike the foe");
    private Ability Blizzard = new Ability("Blizzard", 30, "A howling blizzard is summoned to strike the foe");
    private Ability Earthquake = new Ability("Earthquake", 20, "Causes an earthquake to damage the foe");
    private Ability HyperBeam = new Ability("HyperBeam", 30, "Fires a massive laser at the foe");



    Monster Eevee = new Monster("Eevee", Tackle, QuickAttack, Bite, Swift);
    Monster Vaporeon = new Monster("Vaporeon", Tackle, QuickAttack, AquaJet, HydroPump);
    Monster Glaceon = new Monster("Glaceon", Tackle, QuickAttack, IceFang, Blizzard);
    Monster Meowth = new Monster("Meowth",Tackle, Scratch, Bite, Slash);
    Monster Sharpedo = new Monster( "Sharpedo ", Rage, AquaJet, IceFang, SkullBash);
    Monster Weedle = new Monster("Weedle", PoisonSting, PoisonSting, BugBite, BugBite);
    Monster Pidgeot = new Monster("Pidgeot", Gust, QuickAttack, WingAttack, Hurricane);
    Monster Cubone = new Monster("Cubone", Rage, Bonemerange, BoneClub, BoneRush);
    Monster Starmie = new Monster("Starmie", WaterGun, RapidSpin, Swift, HydroPump);
    Monster Jynx = new Monster("Jynx", Tackle, IcePunch,IceFang ,Blizzard);
    Monster Flygon = new Monster("Flygon", DragonTail, DragonClaw, Earthquake, HyperBeam);
    Monster Snorunt = new Monster("Snorunt", Tackle, RapidSpin, IceShard, IceFang);
    Monster Tyrunt = new Monster("Tyrunt", Tackle, Stomp, Bite, DragonTail);
    Monster Rattata = new Monster("Rattata", Tackle, Tackle, Bite, HyperFang);




    private ArrayList<Node> markList = new ArrayList<>();

    public NodeMon(GoogleMap map) {
        markerSetup();
        mapAddMarker(map);
    }

    public ArrayList<Node> getMarkList() {
        return markList;
    }

    private void mapAddMarker(GoogleMap map) {
        LatLng grass1 = new LatLng(marker1.getLatitude(), marker1.getLongitude());
        map.addMarker(new MarkerOptions().position(grass1));
        LatLng grass2 = new LatLng(marker2.getLatitude(), marker2.getLongitude());
        map.addMarker(new MarkerOptions().position(grass2));
        LatLng grass3 = new LatLng(marker3.getLatitude(), marker3.getLongitude());
        map.addMarker(new MarkerOptions().position(grass3));
        LatLng grass4 = new LatLng(marker4.getLatitude(), marker4.getLongitude());
        map.addMarker(new MarkerOptions().position(grass4));

    }

    private void markerMon(){
        marker1.getMonAtLocation().add(Meowth);
        marker1.getMonAtLocation().add(Eevee);
        marker1.getMonAtLocation().add(Weedle);
        marker1.getMonAtLocation().add(Pidgeot);

        marker2.getMonAtLocation().add(Sharpedo);
        marker2.getMonAtLocation().add(Starmie);
        marker2.getMonAtLocation().add(Vaporeon);
        marker2.getMonAtLocation().add(Rattata);

        marker3.getMonAtLocation().add(Cubone);
        marker3.getMonAtLocation().add(Flygon);
        marker3.getMonAtLocation().add(Tyrunt);
        marker3.getMonAtLocation().add(Rattata);

        marker4.getMonAtLocation().add(Jynx);
        marker4.getMonAtLocation().add(Glaceon);
        marker4.getMonAtLocation().add(Snorunt);
        marker4.getMonAtLocation().add(Rattata);

    }


    private void markerSetup() {
        markList.add(marker1);
        markList.add(marker2);
        markList.add(marker3);
        markList.add(marker4);

        marker1.getAdjNode().add(marker2);
        marker1.getAdjNode().add(marker3);

        marker2.getAdjNode().add(marker1);
        marker2.getAdjNode().add(marker3);

        marker3.getAdjNode().add(marker1);
        marker3.getAdjNode().add(marker2);
        markerMon();
    }

}
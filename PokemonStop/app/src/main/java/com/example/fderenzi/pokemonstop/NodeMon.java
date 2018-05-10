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


    private Ability study = new Ability("study", 5, "Lowers pokemons defense");
    private Ability slash = new Ability("slash", 10, "pokemon slashes its opponent");
    private Ability tackle = new Ability("tackle", 15, "Lowers pokemons defense");
    private Ability pound = new Ability("pound", 20, "Lowers pokemons defense");

    private Ability research = new Ability("research", 10, "Increases intelligence");
    private Ability Guptas_Wrath = new Ability("Gupta's Wrath", 95, "Gupta tragically scars your life by teaching for so many semesters");
    private Ability Gupta_Quiz = new Ability("Gupta Quiz", 22, "This ability is an abstraction of slash, therefore, Everyone's grade is slashed by 50%!");

    Monster Eevee = new Monster("Eevee", study, slash, tackle, pound);
    Monster LordAndSaviorGupta = new Monster("DR PROFESSOR GUPTA",research, Guptas_Wrath, Gupta_Quiz, tackle);
    Monster DooYP = new Monster( "Doo YoungUNNASTAN ", research, slash, Gupta_Quiz, Guptas_Wrath);
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
        marker1.getMonAtLocation().add(LordAndSaviorGupta);
        marker1.getMonAtLocation().add(Eevee);
        marker1.getMonAtLocation().add(DooYP);
        marker2.getMonAtLocation().add(LordAndSaviorGupta);
        marker2.getMonAtLocation().add(Eevee);
        marker3.getMonAtLocation().add(LordAndSaviorGupta);
        marker3.getMonAtLocation().add(Eevee);
        marker4.getMonAtLocation().add(LordAndSaviorGupta);
        marker4.getMonAtLocation().add(Eevee);
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
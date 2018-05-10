package com.example.fderenzi.pokemonstop;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;

public class Node {
    public double latitude;
    public double longitude;
    public ArrayList<Node> adjNode;
    public ArrayList<Monster> monAtLocation;

    public Node(double inpLat, double inpLong) {
        latitude = inpLat;
        longitude = inpLong;
        adjNode = new ArrayList<>();
        monAtLocation = new ArrayList<>();

    }


    public double calcDistance(double curLat, double curLong){
        double distance = Math.pow((curLat-latitude),2) + Math.pow((curLong-longitude),2);
        return distance;
    }
    public Monster randomMonsterSelect(){
        Random rand = new Random();
        int n = rand.nextInt(monAtLocation.size());
        return monAtLocation.get(n);
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public ArrayList<Node> getAdjNode() {
        return adjNode;
    }

    public ArrayList<Monster> getMonAtLocation() {
        return monAtLocation;
    }
}

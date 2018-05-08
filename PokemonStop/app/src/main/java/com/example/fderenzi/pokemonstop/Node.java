package com.example.fderenzi.pokemonstop;

import java.sql.ResultSet;
import java.util.ArrayList;

public class Node {
    public double latitude;
    public double longitude;
    public ArrayList<Node> adjNode;
    public Monster monAtLocation;

    public Node(double inpLat, double inpLong) {
        latitude = inpLat;
        longitude = inpLong;
        adjNode = new ArrayList<>();
    }


    public double calcDistance(double curLat, double curLong){
        double distance = Math.pow((curLat-latitude),2) + Math.pow((curLong-longitude),2);
        return distance;
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
}

package com.kyungminum;

import java.util.ArrayList;

public class VertexList {
    private String name;
    private Vertex root;
    private ArrayList<Vertex> neibs;

    public VertexList(String name){
        this.name = new String(name);
        this.root = new Vertex(name);
    }

    @Override
    public String toString() {
        return name + " " + neibs;
    }

    public ArrayList<Vertex> getNeibs(){
        return neibs;
    }

    public void setNeibs(ArrayList<Vertex> neibs){
        this.neibs = neibs;
    }

    public Vertex getRoot() {
        return root;
    }

    public String getName() {
        return name;
    }
}

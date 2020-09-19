package com.kyungminum;

public class Vertex {
    private String name;

    public Vertex(String name){
        this.name = new String(name);
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }
}

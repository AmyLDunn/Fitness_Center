package com.example.fitnesscenter.helper;

public class ClassType {

    int id;
    String name, description;

    public ClassType(int id, String name, String description){
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

}

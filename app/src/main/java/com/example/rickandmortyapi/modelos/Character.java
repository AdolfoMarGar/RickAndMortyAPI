package com.example.rickandmortyapi.modelos;

public class Character {
    int id;
    String name;
    String status;
    String species;
    String gender;
    String location;
    String image;
    boolean favorite;



    public Character(int id,String name, String status, String species,  String gender, String location, String image) {
        this.id=id;
        this.name = name;
        this.status = status;
        this.species = species;
        this.gender = gender;
        this.location = location;
        this.image = image;
        favorite=false;
    }

    public boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}

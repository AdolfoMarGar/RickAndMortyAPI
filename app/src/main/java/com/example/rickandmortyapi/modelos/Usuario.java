package com.example.rickandmortyapi.modelos;

public class Usuario {
    //Objeto usuario
    String usu;
    String pass;

    //constructor
    public Usuario(String usu, String pass) {
        this.usu = usu;
        this.pass = pass;
    }

    //getters y setters
    public String getUsu() {
        return usu;
    }

    public void setUsu(String usu) {
        this.usu = usu;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}

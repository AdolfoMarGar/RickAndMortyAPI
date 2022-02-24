package com.example.rickandmortyapi;

public class Usuario {
    String usu;
    String pass;

    public Usuario(String usu, String pass) {
        this.usu = usu;
        this.pass = pass;
    }

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

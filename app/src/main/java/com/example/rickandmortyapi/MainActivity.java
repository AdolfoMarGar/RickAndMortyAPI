package com.example.rickandmortyapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rickandmortyapi.DB.DBUsers;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    EditText txtUsu;
    EditText txtPass;

    String urlCharacters="/character";


    DBUsers DB;

    Usuario usuIntro;
    Usuario usuAux;
    ArrayList<Usuario>listaUsuarios = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        txtUsu = findViewById(R.id.txtUsuario);
        txtPass = findViewById(R.id.txtPassword);
        DB = new DBUsers(this);
        obtenerDatos();
    }



    public void obtenerDatos(){
        ArrayList<String>listaUsu;
        ArrayList<String>listaPass;
        listaUsu = DB.getAllPasswords();
        listaPass = DB.getAllPasswords();
        for(int i=0;i<listaUsu.size();i++){
            usuAux = new Usuario(listaUsu.get(i),listaPass.get(i));
            listaUsuarios.add(usuAux);
            System.out.println("usu"+i+":"+usuAux.getUsu());
            System.out.println("pass"+i+":"+usuAux.getPass());
        }
    }

    public boolean existe(String usu){
        boolean existe=false;
        for(int i=0;i<listaUsuarios.size();i++){
            if(usu.equals(listaUsuarios.get(i).getUsu())){
                existe=true;
            }
        }
        return existe;
    }

    public boolean passCorrecta(String usu,String pass){
        boolean correcta=false;
        for(int i=0;i<listaUsuarios.size();i++){
            if(usu.equals(listaUsuarios.get(i).getUsu())){
                if(pass.equals(listaUsuarios.get(i).getPass())){
                    correcta=true;
                }
            }
        }
        return correcta;
    }

    public void iniciarSesion(View view) {
        try{
            if(!txtUsu.getText().toString().equals("") && !txtPass.getText().toString().equals("")){
                usuIntro= new Usuario(txtUsu.getText().toString(),txtPass.getText().toString());
                if(existe(usuIntro.getUsu())){
                    if(passCorrecta(usuIntro.getUsu(),usuIntro.getPass())){
                        Toast("Sesion iniciada correctamente.");
                        this.setContentView(R.layout.character_layout);
                        Intent i = new Intent(MainActivity.this,SecondActivity.class);
                        startActivity(i);


                    }else{
                        Toast("Contraseña incorrecta.");
                    }
                }else{
                    Toast("Usuario no encontrado.");
                }
            }else{
                Toast("Rellena los campos");
            }

        }catch (NullPointerException nEx){
            Toast("Rellena los campos");
        }
    }

    //Esta to correcto
    public void registrarUsuario(View view) {
        try{
            if(!txtUsu.getText().toString().equals("") && !txtPass.getText().toString().equals("")){
                usuIntro= new Usuario(txtUsu.getText().toString(),txtPass.getText().toString());
                if(!existe(usuIntro.getUsu())){
                    if(DB.insert(usuIntro.getUsu(), usuIntro.getPass()) != -1){
                        Toast("Usuario registrado correctamente.");
                        listaUsuarios.add(usuIntro);
                    }else{
                        Toast("Error! Usuario no creado.");
                    }
                }else{
                    Toast("Este usuario ya existe.");
                }
            }else{
                Toast("Rellena los campos");
            }

        }catch (NullPointerException nEx){
            Toast("Rellena los campos");
        }
    }

    public void Toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
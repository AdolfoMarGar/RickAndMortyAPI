package com.example.rickandmortyapi;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.rickandmortyapi.DB.DBUsers;
import com.example.rickandmortyapi.modelos.Usuario;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    //declaramos los objetos que vamos a utilizar globalmente
    EditText txtUsu;
    EditText txtPass;
    int color = -1;
    DBUsers DB;
    Usuario usuIntro;
    Usuario usuAux;
    ArrayList<Usuario>listaUsuarios = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //inicializamos los recursos necesarios
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        txtUsu = findViewById(R.id.txtUsuario);
        txtPass = findViewById(R.id.txtPassword);
        DB = new DBUsers(this);
        obtenerDatos();
    }

    public void obtenerDatos(){
        //obtenemos los datos de la bd
        ArrayList<String>listaUsu;
        ArrayList<String>listaPass;
        color = DB.getColor();
        cambiarColor(color);
        listaUsu = DB.getAllUsers();
        listaPass = DB.getAllPasswords();
        for(int i=0;i<listaUsu.size();i++){
            usuAux = new Usuario(listaUsu.get(i),listaPass.get(i));
            listaUsuarios.add(usuAux);
        }
    }

    public boolean existe(String usu){
        //comprobamos si existe un usuario
        boolean existe=false;
        for(int i=0;i<listaUsuarios.size();i++){
            if(usu.equals(listaUsuarios.get(i).getUsu())){
                existe=true;
            }
        }
        return existe;
    }

    public boolean passCorrecta(String usu,String pass){
        //comprobamos si la contraseña de un usuario es correcta
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

    public  void cambiarColor(int color){
        //cambiamos colores segun el color/tema almacenado
        switch (color){
            case 0:
                txtUsu.setTextColor(Color.CYAN);
                txtUsu.setBackgroundColor(getResources().getColor(R.color.color0));

                txtPass.setTextColor(Color.CYAN);
                txtPass.setBackgroundColor(getResources().getColor(R.color.color0));

                break;

            case 1:
                txtUsu.setTextColor(Color.RED);
                txtUsu.setBackgroundColor(getResources().getColor(R.color.color1));

                txtPass.setTextColor(Color.RED);
                txtPass.setBackgroundColor(getResources().getColor(R.color.color1));

                break;

            case 2:
                txtUsu.setTextColor(Color.GREEN);
                txtUsu.setBackgroundColor(getResources().getColor(R.color.color2));

                txtPass.setTextColor(Color.GREEN);
                txtPass.setBackgroundColor(getResources().getColor(R.color.color2));

                break;

            case -1:
                Toast("Error al cargar el color.");

                break;
        }
    }

    public void actualizarBD(){
        //actualizamos la bd de manera bruta en vez de con un alter table
        DB.deleteAll();
        for(Usuario u : listaUsuarios){
            DB.insert(u.getUsu(),u.getPass(),color);
        }
    }

    public void iniciarSesion(View view) {
        //metodo que comprueba que tod o este correcto para iniciar sesion.
        //Si no es así, devuelve un toast con el fallo
        try{
            if(!txtUsu.getText().toString().equals("") && !txtPass.getText().toString().equals("")){
                usuIntro= new Usuario(txtUsu.getText().toString(),txtPass.getText().toString());
                if(existe(usuIntro.getUsu())){
                    if(passCorrecta(usuIntro.getUsu(),usuIntro.getPass())){
                        Toast("Sesion iniciada correctamente.");
                        //creamos intent y le pasamos como segundo parametro la actividad2
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
        //igual que iniciar sesion pero en vez de cambiar de actividad, se realiza un insert en la bd
        //y se actualiza la arraylist que almacena los usuarios y sus datos
        try{
            if(!txtUsu.getText().toString().equals("") && !txtPass.getText().toString().equals("")){
                usuIntro= new Usuario(txtUsu.getText().toString(),txtPass.getText().toString());
                if(!existe(usuIntro.getUsu())){
                    DB.insert(usuIntro.getUsu(), usuIntro.getPass(),color);
                    Toast("Usuario registrado correctamente.");
                    listaUsuarios.add(usuIntro);

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

    //metodo para llamar a un toast pasandole como parametro el mensaje
    public void Toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    //metodo que inicializa un alertDialog que utilizamos para cambiar de tema
    public void color(View view) {
        final CharSequence [] opciones={"Tema 1","Tema 2","Tema 3"};
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(MainActivity.this);
        alertOpciones.setTitle("Seleccione tema.");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            if(opciones[i].equals("Tema 1")){
                if(color==0){
                    Toast("Ya esta este Tema.");
                }else{
                    color=0;
                    actualizarBD();

                    Toast("Tema cambiado.");
                }
            }else{
                if(opciones[i].equals("Tema 2")){
                    if(color==1){
                        Toast("Ya esta este Tema.");
                    }else{
                        color=1;
                        actualizarBD();

                        Toast("Tema cambiado.");
                    }
                }else{
                    if(opciones[i].equals("Tema 3")){
                        if(color==2){
                            Toast("Ya esta este Tema.");
                        }else{
                            color=2;
                            actualizarBD();

                            Toast("Tema cambiado.");
                        }
                    }else{
                        dialogInterface.dismiss();
                    }
                }
            }
            cambiarColor(color);
        }});
        alertOpciones.show();
    }
}
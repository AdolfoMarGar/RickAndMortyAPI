package com.example.rickandmortyapi;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rickandmortyapi.DB.DBCharacters;
import com.example.rickandmortyapi.modelos.Character;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SecondActivity extends AppCompatActivity implements AdaptadorListenerPosition {
    DBCharacters DBChar;

    String urlCharacters="/character";
    ArrayList<Character>listaCharacters = new ArrayList<>();
    ArrayList<Character>listaCharactersFav = new ArrayList<>();
    ArrayList<Character>DBlistaCharacters = new ArrayList<>();
    Character auxChar;

    String result;

    RecyclerView recyclerView;
    RecyclerAdapter recAdapter;

    EditText txtId;
    EditText txtNombre;
    EditText txtEspecie;
    EditText txtGenero;
    EditText txtLoc;
    EditText txtEstatus;

    boolean fav=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_layout);

        DBChar = new DBCharacters(this);

        new taskConnections().execute(urlCharacters);

        recyclerView = findViewById(R.id.charLay);
        recAdapter = new RecyclerAdapter(listaCharacters);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setAdapter(recAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recAdapter.setListener(this);

        txtId = findViewById(R.id.txtId);
        txtNombre=findViewById(R.id.txtNombre);
        txtEstatus=findViewById(R.id.txtStatus);
        txtEspecie=findViewById(R.id.txtSpecies);
        txtGenero = findViewById(R.id.txtGender);
        txtLoc = findViewById(R.id.txtLocation);
    }

    @Override
    public void onContactPos(int Id){
        acciones(Id);
    }


    private class taskConnections extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            String resChar;

            resChar = ConectApiRest.getRequest(strings[0]);
            result = resChar;
            if(result!=null){
                return "1";
            }else{
                return "-1;";
            }

        }
        @Override
        protected void onPostExecute(String s) {
            try {
                if(!s.equals("-1")){
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    for(int j=0; j<jsonArray.length(); j++){
                        JSONObject jsonLocation = jsonArray.getJSONObject(j).getJSONObject("location");
                        auxChar = new Character(
                                Integer.parseInt(jsonArray.getJSONObject(j).getString("id")),
                                jsonArray.getJSONObject(j).getString("name"),
                                jsonArray.getJSONObject(j).getString("status"),
                                jsonArray.getJSONObject(j).getString("species"),
                                jsonArray.getJSONObject(j).getString("gender"),
                                jsonLocation.getString("name"),
                                jsonArray.getJSONObject(j).getString("image")
                        );
                        listaCharacters.add(auxChar);
                    }
                    recAdapter.notifyDataSetChanged();

                }else{
                    Toast.makeText(SecondActivity.this, "Problema al cargar los datos", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void Toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void acciones(int pos) {
        final CharSequence [] opciones={"Fav : SI","Fav : NO","Rellenar"};
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(SecondActivity.this);
        alertOpciones.setTitle("Ventana Acciones.");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(opciones[i].equals("Fav : SI")){
                    listaCharacters.get(pos).setFavorite(true);
                    Toast(listaCharacters.get(pos).getName()+" ahora es favorito.");
                }else{
                    if(opciones[i].equals("Fav : NO")){
                        listaCharacters.get(pos).setFavorite(false);
                        Toast(listaCharacters.get(pos).getName()+" ahora no es favorito.");


                    }else{
                        if(opciones[i].equals("Rellenar")){
                            auxChar = listaCharacters.get(pos);
                            Toast("Campos rellenados.");
                            txtId.setText(String.valueOf(auxChar.getId()));
                            txtEspecie.setText(auxChar.getSpecies());
                            txtEstatus.setText(auxChar.getStatus());
                            txtGenero.setText(auxChar.getGender());
                            txtLoc.setText(auxChar.getLocation());
                            txtNombre.setText(auxChar.getName());

                        }else{
                            dialogInterface.dismiss();
                        }
                    }
                }
            }
        });
        alertOpciones.show();

    }

    public void guardar(View view) {
        Toast("Guardando datos");
        DBChar.deleteAll();
        for(Character c : listaCharacters){
            DBChar.insert(c.getId(),c.getName(),c.getStatus(),c.getSpecies(),c.getGender(),c.getLocation(),c.getImage(),c.getFavorite());
        }
    }

    public void anadir(View view) {
        try{
            int idAux = Integer.parseInt(txtId.getText().toString());
            if(idAux>0){
                boolean anadir=true;
                for (int i =0;i<listaCharacters.size();i++){
                    if(listaCharacters.get(i).getId()==idAux){
                        anadir=false;
                        break;
                    }
                }
                if(!anadir){
                    Toast("Id ya existente.");
                }else{
                    String name = txtNombre.getText().toString();
                    String especie = txtEspecie.getText().toString();
                    String estado = txtEstatus.getText().toString();
                    String genero = txtGenero.getText().toString();
                    String localizacion = txtLoc.getText().toString();
                    if(!name.equals("") && !especie.equals("")&& !estado.equals("")&& !genero.equals("")&& !localizacion.equals("")){
                        auxChar = new Character(idAux,name,estado,especie,genero,localizacion,"https://rickandmortyapi.com/api/character/avatar/19.jpeg");
                        listaCharacters.add(auxChar);
                    }else{
                        Toast("Rellene los campos");
                    }
                    recAdapter.notifyDataSetChanged();
                }
            }else{
                Toast("Itroduce un Id positivo.");
            }

        }catch (Exception e){
            Toast("Introduce un numero en ID");
        }

    }

    public void modificar(View view) {
        try{
            int idAux = Integer.parseInt(txtId.getText().toString());
            if(idAux>0){
                boolean anadir=true;
                for (int i =0;i<listaCharacters.size();i++){
                    if(listaCharacters.get(i).getId()==idAux){
                        anadir=false;
                        break;
                    }
                }
                if(anadir){
                    Toast("Id no encontrada.");
                }else{
                    String name = txtNombre.getText().toString();
                    String especie = txtEspecie.getText().toString();
                    String estado = txtEstatus.getText().toString();
                    String genero = txtGenero.getText().toString();
                    String localizacion = txtLoc.getText().toString();
                    if(!name.equals("") && !especie.equals("")&& !estado.equals("")&& !genero.equals("")&& !localizacion.equals("")){
                        for(Character c : listaCharacters){
                            if(c.getId()==idAux){
                                c.setLocation(localizacion);
                                c.setGender(genero);
                                c.setStatus(estado);
                                c.setSpecies(especie);
                                c.setName(name);
                            }
                        }
                        Toast("Personaje modificado.");
                    }else{
                        Toast("Rellene los campos");
                    }
                    recAdapter.notifyDataSetChanged();
                }
            }else{
                Toast("Itroduce un Id positivo.");
            }

        }catch (Exception e){
            Toast("Introduce un numero en ID");
        }
    }

    public void borrar(View view) {
        try{
            int idAux = Integer.parseInt(txtId.getText().toString());
            if(idAux>0){
                boolean borrado=false;
                for (int i =0;i<listaCharacters.size();i++){
                    if(listaCharacters.get(i).getId()==idAux){
                        borrado=true;
                        Toast(listaCharacters.get(i).getName()+" ha sido borrado.");
                        listaCharacters.remove(i);
                        break;
                    }
                }
                if(!borrado){
                    Toast("Id no encontrada.");
                }else{
                    recAdapter.notifyDataSetChanged();
                }
            }else{
                Toast("Itroduce un Id positivo.");
            }

        }catch (Exception e){
            Toast("Introduce un numero en ID");
        }

    }

    public void cargar (View view){
        fav=false;
        Toast("Cargando datos");
        ArrayList<Integer>ides;
        ArrayList<String>namees;
        ArrayList<String>statuses;
        ArrayList<String>specieses;
        ArrayList<String>genderes;
        ArrayList<String>locationes;
        ArrayList<String>imagenes;
        ArrayList<Boolean> favorites;

        ides = DBChar.getAllid();
        namees = DBChar.getAllNames();
        statuses = DBChar.getAllStatus();
        specieses = DBChar.getAllSpecies();
        genderes = DBChar.getAllGender();
        locationes = DBChar.getAllLocation();
        imagenes = DBChar.getAllImage();
        favorites = DBChar.getAllFavorite();
        for(Boolean b : favorites){
            System.out.println(b);
        }

        DBlistaCharacters.clear();
        for(int i=0;i<ides.size();i++){
            auxChar = new Character(
                    ides.get(i),
                    namees.get(i),
                    statuses.get(i),
                    specieses.get(i),
                    genderes.get(i),
                    locationes.get(i),
                    imagenes.get(i)
            );
            auxChar.setFavorite(favorites.get(i));
            DBlistaCharacters.add(auxChar);
        }

        listaCharacters =DBlistaCharacters;
        //Al actualizar la lista ya que recAdapter.notifyDataSetChanged(); no funciona al cambiar la lista al mismo momento
        //La volvemos a crear de 0 pasandole otra vez la lista e instaciando los objetos necesarios.
        recAdapter = new RecyclerAdapter(listaCharacters);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(recAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recAdapter.setListener(this);

    }

    public void favoritos(View view){
        if(!fav){
            if(!listaCharactersFav.isEmpty()){
                listaCharactersFav.clear();
            }
            for(Character c :listaCharacters){
                listaCharactersFav.add(c);
            }
            int listafavSize = listaCharactersFav.size();
            Toast(String.valueOf(listafavSize));

            for(int i=listafavSize-1;i>-1;i--){
                if(!listaCharacters.get(i).getFavorite()){
                    listaCharacters.remove(i);
                }
            }

            //Al actualizar la lista ya que recAdapter.notifyDataSetChanged(); no funciona al cambiar la lista al mismo momento
            //La volvemos a crear de 0 pasandole otra vez la lista e instaciando los objetos necesarios.
            recAdapter = new RecyclerAdapter(listaCharacters);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setAdapter(recAdapter);
            recyclerView.setLayoutManager(layoutManager);
            recAdapter.setListener(this);
            Toast("Vista favoritos: Encendida");

            fav=true;

        }else{
            if(!listaCharacters.isEmpty()){
                listaCharacters.clear();
            }
            for(Character c :listaCharactersFav){
                listaCharacters.add(c);
            }


            //Al actualizar la lista ya que recAdapter.notifyDataSetChanged(); no funciona al cambiar la lista al mismo momento
            //La volvemos a crear de 0 pasandole otra vez la lista e instaciando los objetos necesarios.
            recAdapter = new RecyclerAdapter(listaCharacters);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setAdapter(recAdapter);
            recyclerView.setLayoutManager(layoutManager);
            recAdapter.setListener(this);
            Toast("Vista favoritos: APAGADA");

            fav=false;

        }

    }
}

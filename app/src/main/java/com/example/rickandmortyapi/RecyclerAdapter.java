package com.example.rickandmortyapi;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.example.rickandmortyapi.modelos.Character;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> {
    private List<Character> listCharacter;
    private AdaptadorListenerPosition listener;

    //setteamos el listener de la interfaz
    public void setListener(AdaptadorListenerPosition listener) {
        this.listener = listener;
    }

    //declaramos que tipo de lista le vamos a pasar en el constructor
    public RecyclerAdapter(ArrayList<Character> listCharacter) {
        this.listCharacter = listCharacter;
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //enlazamos el layout de la lista
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.character_list,parent, false);

        return new RecyclerHolder(view);
    }


    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        //obtenemos x objeto segun position
        Character auxChar = listCharacter.get(position);
        //enlazamos los recursos a sus respectivos sitios
        holder.txt_N.setText(auxChar.getName());
        holder.txt_Est.setText(auxChar.getStatus());
        holder.txt_Esp.setText(auxChar.getSpecies());
        holder.txt_G.setText(auxChar.getGender());
        holder.txt_L.setText(auxChar.getLocation());
        Glide.with(holder.imgCharacter.getContext()).load(auxChar.getImage()).into(holder.imgCharacter);

        //Creamos el listener y le damos su funcion. En este caso devolver la posicion en la lista al clickear el boton
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null){
                    listener.onContactPos(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCharacter.size();
    }


    public class RecyclerHolder extends ViewHolder{
        //declaramos los objetos que vamos a utilizar
        ImageView imgCharacter;
        TextView txt_N;
        TextView txt_Est;
        TextView txt_Esp;
        TextView txt_G;
        TextView txt_L;
        Button btn;

        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
            //los inicializamos enlazandolos a sus recursos
            imgCharacter  = itemView.findViewById(R.id.img_item);
            txt_N = itemView.findViewById(R.id.txtId);
            txt_Est = itemView.findViewById(R.id.txtStatus);
            txt_Esp = itemView.findViewById(R.id.txtSpecies);
            txt_G = itemView.findViewById(R.id.txtGender);
            txt_L = itemView.findViewById(R.id.txtLocation);
            btn = itemView.findViewById(R.id.btn_Acciones);
        }
    }
}

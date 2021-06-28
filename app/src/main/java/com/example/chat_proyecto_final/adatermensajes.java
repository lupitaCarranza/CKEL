package com.example.chat_proyecto_final;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;

import com.bumptech.glide.Glide;
import com.example.chat_proyecto_final.entidades.recibirmensaje;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class adatermensajes extends RecyclerView.Adapter<holdermensaje> {

    private List<recibirmensaje> listmensaje = new ArrayList<>();
    private Context c;

    public adatermensajes( Context c) {

        this.c = c;
    }

    public void agregarmensaje(recibirmensaje m){
        listmensaje.add(m);
        notifyItemInserted(listmensaje.size());
    }

    @Override
    public holdermensaje onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.card_view_mensajes,parent,false);
        return new holdermensaje(v);
    }

    @Override
    public void onBindViewHolder(holdermensaje holder, int position) {
        holder.getNombre().setText(listmensaje.get(position).getNombre());
        holder.getMensaje().setText(listmensaje.get(position).getMensaje());

        //ver imagen en el chat
        if(listmensaje.get(position).getType_mensaje().equals("2")){
            holder.getFotomensajeenviado().setVisibility(View.VISIBLE);
            holder.getMensaje().setVisibility(View.VISIBLE);
            Glide.with(c).load(listmensaje.get(position).getUrlfoto()).into(holder.getFotomensajeenviado());
        }else if(listmensaje.get(position).getType_mensaje().equals("1")){
            holder.getFotomensajeenviado().setVisibility(View.GONE);
            holder.getMensaje().setVisibility(View.VISIBLE);
        }

        //cambiar foto perfil
        if(listmensaje.get(position).getFotoperfil().isEmpty()){
            holder.getFotomensaje().setImageResource(R.mipmap.ic_launcher);
        }else{
            Glide.with(c).load(listmensaje.get(position).getFotoperfil()).into(holder.getFotomensaje());
        }
        //formato de hora
        Long codigoHora  = listmensaje.get(position).getHora();
        Date d = new Date(codigoHora);
        SimpleDateFormat adf = new SimpleDateFormat("hh:mm:ss a");
        holder.getHora().setText(adf.format(d));

    }

    @Override
    public int getItemCount() {

        return listmensaje.size();
    }
}

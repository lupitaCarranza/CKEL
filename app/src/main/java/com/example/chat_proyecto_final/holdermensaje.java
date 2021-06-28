package com.example.chat_proyecto_final;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class holdermensaje extends RecyclerView.ViewHolder {

    private TextView nombre;
    private TextView mensaje;
    private TextView hora;
    private CircleImageView fotomensaje; //fotomensajeperfil
    private ImageView fotomensajeenviado; //fotomensaje

    public holdermensaje(View itemView){
        super(itemView);
        nombre = (TextView) itemView.findViewById(R.id.nombremensaje);
        mensaje = (TextView) itemView.findViewById(R.id.mensajemensaje);
        hora = (TextView) itemView.findViewById(R.id.horamensaje);
        fotomensaje = (CircleImageView) itemView.findViewById(R.id.fotoperfilmensaje);
        fotomensajeenviado = (ImageView) itemView.findViewById(R.id.mensajefoto);

    }
    public ImageView getFotomensajeenviado() {

        return fotomensajeenviado;
    }

    public void setFotomensajeenviado(ImageView fotomensajeenviado) {

        this.fotomensajeenviado = fotomensajeenviado;
    }

    public TextView getNombre() {
        return nombre;
    }

    public void setNombre(TextView nombre) {

        this.nombre = nombre;
    }

    public TextView getMensaje() {

        return mensaje;
    }

    public void setMensaje(TextView mensaje) {
        this.mensaje = mensaje;
    }

    public TextView getHora() {

        return hora;
    }

    public void setHora(TextView hora) {

        this.hora = hora;
    }

    public CircleImageView getFotomensaje() {

        return fotomensaje;
    }

    public void setFotomensaje(CircleImageView fotomensaje) {

        this.fotomensaje = fotomensaje;
    }
}


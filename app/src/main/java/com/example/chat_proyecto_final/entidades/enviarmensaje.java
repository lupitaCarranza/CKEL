package com.example.chat_proyecto_final.entidades;

import java.util.Map;

public class enviarmensaje extends com.example.chat_proyecto_final.entidades.mensaje {
    private Map hora;

    public enviarmensaje(){

    }
    public enviarmensaje(Map hora){
        this.hora = hora;
    }

    public enviarmensaje(String mensaje, String nombre, String fotoperfil, String type_mensaje, String urlfoto, Map hora) {
        super(mensaje, nombre, fotoperfil, type_mensaje, urlfoto);
        this.hora = hora;
    }

    public enviarmensaje(String mensaje, String nombre, String fotoperfil, String type_mensaje, Map hora) {
        super(mensaje, nombre, fotoperfil, type_mensaje);
        this.hora = hora;
    }

    public Map getHora() {
        return hora;
    }

    public void setHora(Map hora) {
        this.hora = hora;
    }
}

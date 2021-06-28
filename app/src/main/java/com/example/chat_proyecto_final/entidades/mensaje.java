package com.example.chat_proyecto_final.entidades;

public class mensaje {

    private String mensaje;
    private String nombre;
    private String fotoperfil;
    private String type_mensaje;
    private String urlfoto;

    public mensaje(){

    }

    public mensaje(String mensaje, String nombre, String fotoperfil, String type_mensaje) {
        this.mensaje = mensaje;
        this.nombre = nombre;
        this.fotoperfil = fotoperfil;
        this.type_mensaje = type_mensaje;

    }

    public mensaje(String mensaje, String nombre, String fotoperfil, String type_mensaje, String urlfoto) {
        this.mensaje = mensaje;
        this.nombre = nombre;
        this.fotoperfil = fotoperfil;
        this.type_mensaje = type_mensaje;
        this.urlfoto = urlfoto;
    }

    public String getMensaje() {

        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNombre() {

        return nombre;
    }

    public void setNombre(String nombre) {

        this.nombre = nombre;
    }

    public String getFotoperfil() {

        return fotoperfil;
    }

    public void setFotoperfil(String fotoperfil) {

        this.fotoperfil = fotoperfil;
    }

    public String getType_mensaje() {
        return type_mensaje;
    }

    public void setType_mensaje(String type_mensaje) {
        this.type_mensaje = type_mensaje;
    }

    public String getUrlfoto() {
        return urlfoto;
    }

    public void setUrlfoto(String urlfoto) {
        this.urlfoto = urlfoto;
    }
}

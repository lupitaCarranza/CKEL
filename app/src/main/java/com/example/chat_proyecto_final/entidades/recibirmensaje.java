package com.example.chat_proyecto_final.entidades;

public class recibirmensaje extends com.example.chat_proyecto_final.entidades.mensaje {

    private Long hora;
    public recibirmensaje(){
    }

    public recibirmensaje(Long hora){
        this.hora = hora;
    }

    public recibirmensaje(String mensaje, String nombre, String fotoperfil, String type_mensaje, String urlfoto, Long hora) {
        super(mensaje, nombre, fotoperfil, type_mensaje, urlfoto);
        this.hora = hora;
    }

    public Long getHora() {
        return hora;
    }

    public void setHora(Long hora) {
        this.hora = hora;
    }
}

package com.example.mercado.Entidades;

import java.io.Serializable;

public class Usuario implements Serializable {

    String id_user;
    String nombre_user;
    String password;

    public Usuario() {
    }

    public Usuario(String id_user, String nombre_user, String password) {
        this.id_user = id_user;
        this.nombre_user = nombre_user;
        this.password = password;
    }


    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getNombre_user() {
        return nombre_user;
    }

    public void setNombre_user(String nombre_user) {
        this.nombre_user = nombre_user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

package com.example.mercado.Entidades;

import java.io.Serializable;

public class Clientes implements Serializable {

    private int id_cliente;
    private String nombre_cliente;
    private String apellido_cliente;

    public String getLimite() {
        return limite;
    }

    public void setLimite(String limite) {
        this.limite = limite;
    }

    private String limite;
    private int estado_cliente;

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public String getApellido_cliente() {
        return apellido_cliente;
    }

    public void setApellido_cliente(String apellido_cliente) {
        this.apellido_cliente = apellido_cliente;
    }

    public int getEstado_cliente() {
        return estado_cliente;
    }

    public void setEstado_cliente(int estado_cliente) {
        this.estado_cliente = estado_cliente;
    }



    public int getDependencia_cliente() {
        return dependencia_cliente;
    }

    public void setDependencia_cliente(int dependencia_cliente) {
        this.dependencia_cliente = dependencia_cliente;
    }


    private int dependencia_cliente;

}

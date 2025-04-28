package com.example.mercado.VistaVender.Utilidades;

public class ClienteItem {
    private String idCliente;
    private String nomClient;
    private String apeClient;
    private String limiteClien;

    public ClienteItem(){

    }

    public ClienteItem(String idCliente, String nomClient, String apeClient, String limiteClien) {
        this.idCliente = idCliente;
        this.nomClient = nomClient;
        this.apeClient = apeClient;
        this.limiteClien = limiteClien;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getApeClient() {
        return apeClient;
    }

    public void setApeClient(String apeClient) {
        this.apeClient = apeClient;
    }

    public String getLimiteClien() {
        return limiteClien;
    }

    public void setLimiteClien(String limiteClien) {
        this.limiteClien = limiteClien;
    }


}

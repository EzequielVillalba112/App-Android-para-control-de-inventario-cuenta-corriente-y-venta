package com.example.mercado.VistaVender.Utilidades;

public class Devolucion {
    String id_devolucion;
    String id_prod;
    String cantidad_pro;
    String total_pro;
    String fecha;

    String precio_pro;
    String nom_pro;

    public  Devolucion(){

    }

    public String getId_devolucion() {
        return id_devolucion;
    }

    public void setId_devolucion(String id_devolucion) {
        this.id_devolucion = id_devolucion;
    }

    public String getId_prod() {
        return id_prod;
    }

    public void setId_prod(String id_prod) {
        this.id_prod = id_prod;
    }

    public String getCantidad_pro() {
        return cantidad_pro;
    }

    public void setCantidad_pro(String cantidad_pro) {
        this.cantidad_pro = cantidad_pro;
    }

    public String getTotal_pro() {
        return total_pro;
    }

    public void setTotal_pro(String total_pro) {
        this.total_pro = total_pro;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNom_pro() {
        return nom_pro;
    }

    public void setNom_pro(String nom_pro) {
        this.nom_pro = nom_pro;
    }

    public String getPrecio_pro() {
        return precio_pro;
    }

    public void setPrecio_pro(String precio_pro) {
        this.precio_pro = precio_pro;
    }
}

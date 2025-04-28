package com.example.mercado.VistaVender.Utilidades;

public class CuentaCorrienteList {
    String id_cuenta_cc;
    String id_prod;
    String id_cliente;

    String precio;
    String cantidad;
    String total;
    String nomPro;
    String nomClien;
    String fecha;

    public CuentaCorrienteList() {

    }

    public String getId_cuenta_cc() {
        return id_cuenta_cc;
    }

    public void setId_cuenta_cc(String id_cuenta_cc) {
        this.id_cuenta_cc = id_cuenta_cc;
    }

    public String getId_prod() {
        return id_prod;
    }

    public void setId_prod(String id_prod) {
        this.id_prod = id_prod;
    }

    public String getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getNomPro() {
        return nomPro;
    }

    public void setNomPro(String nomPro) {
        this.nomPro = nomPro;
    }

    public String getNomClien() {
        return nomClien;
    }

    public void setNomClien(String nomClien) {
        this.nomClien = nomClien;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}

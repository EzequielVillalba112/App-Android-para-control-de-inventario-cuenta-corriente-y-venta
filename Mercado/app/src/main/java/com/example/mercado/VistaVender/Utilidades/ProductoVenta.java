package com.example.mercado.VistaVender.Utilidades;

import java.io.Serializable;

public class ProductoVenta implements Serializable {
    int idProducto;
    String nombreProd;
    String precio;
    String total;
    String cantidad;

    public ProductoVenta(){

    }
    public ProductoVenta( String nombreProd, String precio,String cantidad,String total, int idProducto) {
        this.idProducto = idProducto;
        this.nombreProd = nombreProd;
        this.precio = precio;
        this.cantidad = cantidad;
        this.total = total;
        this.idProducto = idProducto;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProd() {
        return nombreProd;
    }

    public void setNombreProd(String nombreProd) {
        this.nombreProd = nombreProd;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotal() {
        return total;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

}

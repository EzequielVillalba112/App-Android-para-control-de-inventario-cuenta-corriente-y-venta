package com.example.mercado.VistaVender.Utilidades;

import java.io.Serializable;

public class VentaCAB implements Serializable {

    String id_venta;
    String id_cliente;
    String id_detalle;
    String id_producto;

    String precio;
    String cantidad;
    String fecha;
    String total;
    String nombre;
    String nomClien;


    public VentaCAB(){

    }

    public VentaCAB(String id_venta, String id_cliente, String fecha, String total) {
        this.id_venta = id_venta;
        this.id_cliente = id_cliente;
        this.fecha = fecha;
        this.total = total;
    }

    public String getId_venta() {
        return id_venta;
    }

    public void setId_venta(String id_venta) {
        this.id_venta = id_venta;
    }

    public String getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
    public String getId_detalle() {
        return id_detalle;
    }

    public void setId_detalle(String id_detalle) {
        this.id_detalle = id_detalle;
    }

    public String getId_producto() {
        return id_producto;
    }

    public void setId_producto(String id_producto) {
        this.id_producto = id_producto;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNomClien() {
        return nomClien;
    }

    public void setNomClien(String nomClien) {
        this.nomClien = nomClien;
    }
}

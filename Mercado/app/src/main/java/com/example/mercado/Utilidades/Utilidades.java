package com.example.mercado.Utilidades;

import java.net.PortUnreachableException;

public class Utilidades {
    //Constante campos tabla producto
    public static final String TABLA_PRODUCTO="producto";
    public static final String C_ID="id";
    public static final String C_COD_BARRA="cod_barra";
    public static final String C_NOMBRE="nombre";
    public static final String C_PRECIO="precio";
    public static final String C_IMAGEN="imagen";
    public static final String C_ESTADO="estado";
    public static final String C_DEPENDECIA="dependencia";

    public static final String CREAR_TABLA_PRODUCTO = "CREATE TABLE "
            +TABLA_PRODUCTO+ " " +
            "(" +C_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +C_COD_BARRA+ " INTEGER NOT NULL,"
            +C_NOMBRE+ " TEXT NOT NULL,"
            +C_PRECIO+ " TEXT NOT NULL,"
            +C_IMAGEN+ " TEXT,"
            +C_ESTADO+ " INTEGER,"
            +C_DEPENDECIA+ " INTEGER"+
            ")";

    //Constante campos tabla cliente
    public static final String TABLA_CLIENTE="cliente";
    public static final String C_ID_CLIENTE="id_cliente";
    public static final String C_NOMBRE_CLIENTE="nom_cliente";
    public static final String C_APELLIDO_CLIENTE="apellido_cliente";
    public static final String C_ESTADO_CLIENTE="estado_cliente";
    public static final String C_LIMITE="limite";
    public static final String C_DEPENDECIA_CLIENTE="dependencia_cliente";

    public static final String CREAR_TABLA_CLIENTE = "CREATE TABLE "
            +TABLA_CLIENTE+ " " +
            "(" +C_ID_CLIENTE+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +C_NOMBRE_CLIENTE+ " TEXT NOT NULL,"
            +C_APELLIDO_CLIENTE+ " TEXT NOT NULL,"
            +C_ESTADO_CLIENTE+ " INTEGER,"
            +C_LIMITE+ " REAL,"
            +C_DEPENDECIA_CLIENTE+ " INTEGER"+
            ")";

    //Constates campos tabla VENTAS
    public static final String TABLA_VENTA="venta";
    public static final String C_ID_VENTA="id_venta";
    public static final String C_FECHA="fecha";
    public static final String C_TOTAL="total";

    public static final String CREAR_TABLA_VENTAS = "CREATE TABLE "
            +TABLA_VENTA+ "" +
            "(" +C_ID_VENTA+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +C_FECHA+ " TEXT NOT NULL,"
            +C_TOTAL+ " TEXT NOT NULL"+
            ")";


    //Constante campos tabla DETALLE VENTAS
    public static final String TABLA_DETALLE_VENTAS="detalle_venta";
    public static final String C_ID_DETALLE_VENTAS="id_detalle_ventas";
    public static final String C_ID_VENTAS="id_ventas";
    public static final String C_ID_CLIENTE_VENTA="id_cliente";
    public static final String C_ID_PRODUCTO="id_producto";
    public static final String C_PRECIO_PRO="precio_pro";
    public static final String C_ID_CANTIDAD="cantidad";

    public static final String CREAR_TABLA_DETALLE_VENTA = "CREATE TABLE "
            +TABLA_DETALLE_VENTAS+ "" +
            "(" +C_ID_DETALLE_VENTAS+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +C_ID_VENTAS+ " TEXT NOT NULL,"
            +C_ID_CLIENTE_VENTA+ " TEXT NOT NULL,"
            +C_ID_PRODUCTO+ " TEXT NOT NULL,"
            +C_PRECIO_PRO+ " TEXT NOT NULL,"
            +C_ID_CANTIDAD+ " TEXT NOT NULL"+
            ")";


    //Consatantes campos tabla CUENTA CORRIENTE
    public static final String TABLA_CUENTA_CORRIENTE="cuenta_corriente";
    public static final String C_ID_CUENTA_CORRIENTE="id_cuenta_corriente";
    public static final String C_ID_ARTICULO="id_articulo";
    public static final String C_ID_CLIENTES="id_cliente";
    public static final String C_PRECIO_PRODUCTO="precio_producto";
    public static final String C_CANTIDAD_PRODUCTO="cantidad_prod";
    public static final String C_TOTAL_PROD="total_pro";
    public static final String C_FECHA_CC="fecha_cc";

    public static final String CREAR_TABLA_CUENTA_CORRIENTE = "CREATE TABLE "
            +TABLA_CUENTA_CORRIENTE+ "" +
            "(" +C_ID_CUENTA_CORRIENTE+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +C_ID_ARTICULO+ " TEXT NOT NULL,"
            +C_ID_CLIENTES+ " TEXT NOT NULL,"
            +C_PRECIO_PRODUCTO+ " TEXT NOT NULL,"
            +C_CANTIDAD_PRODUCTO+ " TEXT NOT NULL,"
            +C_TOTAL_PROD+ " TEXT NOT NULL,"
            +C_FECHA_CC+ " TEXT NOT NULL"+
            ")";

    //Constante campos DEVOLUCION
    public static final String TABLA_DEVOLUCION="devolucio";
    public static final String C_ID_DEVOLUCION="id_devolucion";
    public static final String C_ID_PRODUCTO_DEVO="id_producto_devo";
    //public static final String C_ID_CLIENTE_DEVO="id_cliente_devo";
    public static final String C_CANTIDAD_PROD="cantidad_devo";
    public static final String C_PRECIO_PROD="precio_devo";
    public static final String C_TOTAL_DEVO="total_devo";
    public static final String C_FECHA_DEVO="FECHA_DEVO";

    public static final String CREAR_TABLA_DEVOLUCION = "CREATE TABLE "
            +TABLA_DEVOLUCION+ "" +
            "("+C_ID_DEVOLUCION+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +C_ID_PRODUCTO_DEVO+ " TEXT NOT NULL,"
            +C_CANTIDAD_PROD+ " TEXT NOT NULL,"
            +C_TOTAL_DEVO+ " TEXT NOT NULL,"
            +C_PRECIO_PROD+ " TEXT NOT NULL,"
            +C_FECHA_DEVO+ " TEXT NOT NULL"+
            ")";


    //Constante usuario
    public static final String TABLA_USUARIO="usuario";
    public static final String C_ID_USUARIO="id_usuario";
    public static final String C_NOMBRE_USER="nombre_user";
    public static final String C_PASSWORD="password";

    public static final String CREAR_TABLA_USUARIO = "CREATE TABLE "
            +TABLA_USUARIO+ "" +
            "("+C_ID_USUARIO+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +C_NOMBRE_USER+ " TEXT NOT NULL,"
            +C_PASSWORD+ " TEXT NOT NULL"+
            ")";

}

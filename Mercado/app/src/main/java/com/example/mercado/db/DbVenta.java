package com.example.mercado.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.mercado.Entidades.Articulos;
import com.example.mercado.Producto;
import com.example.mercado.Utilidades.Utilidades;
import com.example.mercado.VistaVender.ProductoVender;
import com.example.mercado.VistaVender.Utilidades.CuentaCorrienteList;
import com.example.mercado.VistaVender.Utilidades.Devolucion;
import com.example.mercado.VistaVender.Utilidades.ProductoVenta;
import com.example.mercado.VistaVender.Utilidades.VentaCAB;

import java.util.ArrayList;

public class DbVenta extends  DbHelper{
    Context context;
    DbArticulos dbArticulos;

    public DbVenta(@Nullable Context context) {
        super(context);
        this.context = context;
    }
    public ArrayList<ProductoVenta> mostrarArticulos(){

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<ProductoVenta> listaProducto = new ArrayList<>();
        ProductoVenta productoVenta;
        Cursor cursorArticulos;

        cursorArticulos = db.rawQuery("SELECT nombre,precio,id FROM " + Utilidades.TABLA_PRODUCTO+" WHERE estado = 1", null);

        if (cursorArticulos.moveToFirst()){
            do {
                productoVenta = new ProductoVenta();
                productoVenta.setNombreProd(cursorArticulos.getString(0));
                productoVenta.setPrecio(cursorArticulos.getString(1));
                productoVenta.setIdProducto(cursorArticulos.getInt(2));
                //String nombre = cursorArticulos.getString(0);
                // String precio = cursorArticulos.getString(1);
                listaProducto.add(productoVenta);

                // listaProducto.add(new Articulos(nombre,precio));
            } while (cursorArticulos.moveToNext());
        }
        cursorArticulos.close();

        return listaProducto;
    }

    public long agregarVentaCab(String fecha,String total){
        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put(Utilidades.C_FECHA,fecha);
            values.put(Utilidades.C_TOTAL,total);

            id = db.insert(Utilidades.TABLA_VENTA,null,values);

        }catch (Exception ex){
            ex.toString();
        }

        return id;
    }


    public VentaCAB ultimoidventa(){

            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            VentaCAB ventaCAB = null;
            Cursor cursorVentaCab = null;

            cursorVentaCab = db.rawQuery("SELECT id_venta FROM " +Utilidades.TABLA_VENTA+ " ORDER BY id_venta DESC LIMIT 1", null);

            if (cursorVentaCab.moveToFirst()){
                ventaCAB = new VentaCAB();

                ventaCAB.setId_venta(cursorVentaCab.getString(0));

            }

            cursorVentaCab.close();

        return ventaCAB;
    }

    public long detalleVenta(String id_venta, String id_producto, String precio_pro, String cantidad, int id_cliente){
        long id = 0;

       try {
           DbHelper dbHelper = new DbHelper(context);
           SQLiteDatabase db = dbHelper.getWritableDatabase();

           ContentValues values = new ContentValues();
           values.put(Utilidades.C_ID_VENTAS,id_venta);
           values.put(Utilidades.C_ID_PRODUCTO,id_producto);
           values.put(Utilidades.C_PRECIO_PRO,precio_pro);
           values.put(Utilidades.C_ID_CANTIDAD,cantidad);
           values.put(Utilidades.C_ID_CLIENTE_VENTA,id_cliente);

           //Modificar dependencia articulos
           ContentValues contentProducto = new ContentValues();
           String dep = "1";

           contentProducto.put(Utilidades.C_DEPENDECIA,dep);
           db.update(Utilidades.TABLA_PRODUCTO,contentProducto,"id = ?",new String[]{id_producto});

           id = db.insert(Utilidades.TABLA_DETALLE_VENTAS,null,values);

       }catch (Exception ex){
           ex.toString();
       }


       return id;
    }

    public long devolucion(String id_producto, String cantidad, String precio_pro, String total, String fecha){
        long id = 0;

        String cantidad_pro = cantidad;
        String precio_pro_dev = precio_pro;

        int total_pro = (Integer.parseInt(cantidad_pro) * Integer.parseInt(precio_pro_dev));

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(Utilidades.C_ID_PRODUCTO_DEVO,id_producto);
            values.put(Utilidades.C_CANTIDAD_PROD,cantidad);
            values.put(Utilidades.C_PRECIO_PROD,precio_pro);
            values.put(Utilidades.C_TOTAL_DEVO,total_pro);
            values.put(Utilidades.C_FECHA_DEVO,fecha);

            id = db.insert(Utilidades.TABLA_DEVOLUCION,null,values);

        }catch (Exception ex){
            ex.toString();
        }
        return id;
    }

    public long cuentaCorriente(String id_articulo,String id_cliente, String precio_pre,
                                String cantidad, String total, String fecha){
        long id = 0;

        String cantidad_pro = cantidad;
        String precio_pro = precio_pre;

        int total_pro = (Integer.parseInt(cantidad_pro) * Integer.parseInt(precio_pro));

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(Utilidades.C_ID_ARTICULO,id_articulo);
            values.put(Utilidades.C_ID_CLIENTES,id_cliente);
            values.put(Utilidades.C_PRECIO_PRODUCTO,precio_pre);
            values.put(Utilidades.C_CANTIDAD_PRODUCTO,cantidad);
            values.put(Utilidades.C_TOTAL_PROD,total_pro);
            values.put(Utilidades.C_FECHA_CC,fecha);

            //Modificar dependencia articulos
            ContentValues contentProducto = new ContentValues();
            String dep = "1";

            contentProducto.put(Utilidades.C_DEPENDECIA,dep);
            db.update(Utilidades.TABLA_PRODUCTO,contentProducto,"id = ?",new String[]{id_articulo});

            //Modificar dependencia cliente
            ContentValues contenCliente = new ContentValues();
            contenCliente.put(Utilidades.C_DEPENDECIA_CLIENTE,dep);
            db.update(Utilidades.TABLA_CLIENTE,contenCliente,"id_cliente = ?", new String[]{id_cliente});

            id = db.insert(Utilidades.TABLA_CUENTA_CORRIENTE,null,values);

        }catch (Exception ex){
            ex.toString();
        }

        return id;
    }

    public ArrayList<VentaCAB> mostrarVentas(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<VentaCAB> listaVenta = new ArrayList<>();

        VentaCAB ventaCAB = null;
        Cursor cursorVentaCab = null;

        cursorVentaCab = db.rawQuery("SELECT id_venta,fecha,total FROM " +Utilidades.TABLA_VENTA+"",null);

        if (cursorVentaCab.moveToFirst()){
            do {

                ventaCAB = new VentaCAB();
                ventaCAB.setId_venta(cursorVentaCab.getString(0));
                ventaCAB.setFecha(cursorVentaCab.getString(1));
                ventaCAB.setTotal(cursorVentaCab.getString(2));

                listaVenta.add(ventaCAB);

            }while (cursorVentaCab.moveToNext());
        }
        cursorVentaCab.close();

        return listaVenta;
    }

    public ArrayList<VentaCAB> detalleVentas(int id){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<VentaCAB> detalleVenta = new ArrayList<>();

        VentaCAB ventaCAB = null;
        Cursor cursorVentaCab = null;

        cursorVentaCab = db.rawQuery("SELECT id_cliente,id_producto,precio_pro,cantidad FROM "+Utilidades.TABLA_DETALLE_VENTAS+ " WHERE id_ventas = " +id+ "",null);

        if (cursorVentaCab.moveToFirst()) {
            do {
                ventaCAB = new VentaCAB();

                ventaCAB.setId_producto(cursorVentaCab.getString(1));
                ventaCAB.setPrecio(cursorVentaCab.getString(2));
                ventaCAB.setCantidad(cursorVentaCab.getString(3));

                String precio = cursorVentaCab.getString(2);
                String cantidad = cursorVentaCab.getString(3);

                int total = (Integer.parseInt(cantidad) * Integer.parseInt(precio));

                ventaCAB.setTotal(String.valueOf(total));

                //Obtener Nombre de Cliente
                String nom = cursorVentaCab.getString(0);
                Cursor cu =db.rawQuery("SELECT nom_cliente FROM "+Utilidades.TABLA_CLIENTE+ " WHERE id_cliente = " + nom +"",null);

                if (cu.moveToFirst()) {
                    ventaCAB.setNomClien(cu.getString(0));
                }

                //Obtener Nombre de Producto
                String idPro = cursorVentaCab.getString(1);
                Cursor nomPro = db.rawQuery("SELECT nombre FROM "+Utilidades.TABLA_PRODUCTO+ " WHERE id = " + idPro +"",null);

                if (nomPro.moveToFirst()){
                    ventaCAB.setNombre(nomPro.getString(0));
                }

                detalleVenta.add(ventaCAB);
            }while (cursorVentaCab.moveToNext());
        }
        cursorVentaCab.close();

        return  detalleVenta;
    }

    public ArrayList<CuentaCorrienteList> detalleCC(int id_cliente){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<CuentaCorrienteList> detalleCC = new ArrayList<>();

        CuentaCorrienteList cuentaCC = null;
        Cursor cursorCC= null;

        cursorCC = db.rawQuery("SELECT id_articulo,precio_producto,cantidad_prod,total_pro,fecha_cc,id_cuenta_corriente FROM "+Utilidades.TABLA_CUENTA_CORRIENTE+ " WHERE id_cliente = " +id_cliente+ "",null);

        if (cursorCC.moveToFirst()){
            do {
                cuentaCC = new CuentaCorrienteList();

                String idPro = cursorCC.getString(0);
                Cursor nomPro = db.rawQuery("SELECT nombre FROM "+Utilidades.TABLA_PRODUCTO+ " WHERE id = " + idPro +"",null);

                if (nomPro.moveToFirst()){
                    cuentaCC.setNomPro(nomPro.getString(0));
                }

                cuentaCC.setPrecio(cursorCC.getString(1));
                cuentaCC.setCantidad(cursorCC.getString(2));
                cuentaCC.setTotal(cursorCC.getString(3));
                cuentaCC.setFecha(cursorCC.getString(4));
                cuentaCC.setId_cuenta_cc(cursorCC.getString(5));

                detalleCC.add(cuentaCC);

            }while (cursorCC.moveToNext());
        }
        cursorCC.close();

        return detalleCC;
    }


    public long eliminarProdCC(String id){
        long correcto = 0;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        if (correcto == 0){
            db.execSQL("DELETE FROM " +Utilidades.TABLA_CUENTA_CORRIENTE+ " WHERE id_cuenta_corriente = " + id +"");
            correcto = 1;
        }else {
            correcto = 0;
        }

        return correcto;
    }

    public ArrayList<Devolucion> mostrarDevolucion(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Devolucion> listaDevolucion = new ArrayList<>();
        Devolucion devolucion;
        Cursor cursorDevo;

        cursorDevo = db.rawQuery("SELECT id_producto_devo,id_devolucion,FECHA_DEVO,total_devo,cantidad_devo FROM "+Utilidades.TABLA_DEVOLUCION+"",null);

        if (cursorDevo.moveToFirst()){
            do {
                devolucion = new Devolucion();

                String idPro = cursorDevo.getString(0);
                Cursor nomPro = db.rawQuery("SELECT nombre FROM "+Utilidades.TABLA_PRODUCTO+ " WHERE id = " + idPro +"",null);

                if (nomPro.moveToFirst()){
                    devolucion.setNom_pro(nomPro.getString(0));
                }

                devolucion.setId_devolucion(cursorDevo.getString(1));
                devolucion.setFecha(cursorDevo.getString(2));
                devolucion.setTotal_pro(cursorDevo.getString(3));
                devolucion.setCantidad_pro(cursorDevo.getString(4));

                listaDevolucion.add(devolucion);

            }while (cursorDevo.moveToNext());
        }
        cursorDevo.close();

        return listaDevolucion;
    }

    public long pagarCC(int idCliente){
        long correcto = 0;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.execSQL("DELETE FROM "+Utilidades.TABLA_CUENTA_CORRIENTE+ " WHERE id_cliente = " +idCliente+ "");

        //Modificar dependencia cliente
        String dep = "0";
        String id = String.valueOf(idCliente);

        ContentValues contenCliente = new ContentValues();
        contenCliente.put(Utilidades.C_DEPENDECIA_CLIENTE,dep);
        db.update(Utilidades.TABLA_CLIENTE,contenCliente,"id_cliente = ?", new String[]{id});

        return correcto;
    }

    public  long saldoCC(String idCliente, String fecha, String saldo){
        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues saldoCC = new ContentValues();

            saldoCC.put(Utilidades.C_ID_ARTICULO,0);
            saldoCC.put(Utilidades.C_PRECIO_PRODUCTO,0);
            saldoCC.put(Utilidades.C_CANTIDAD_PRODUCTO,0);
            saldoCC.put(Utilidades.C_ID_CLIENTES, idCliente);
            saldoCC.put(Utilidades.C_FECHA_CC, fecha);
            saldoCC.put(Utilidades.C_TOTAL_PROD, saldo);

            id = db.insert(Utilidades.TABLA_CUENTA_CORRIENTE,null,saldoCC);

        }catch (Exception ex){
            ex.toString();
        }

        return id;

    }

    public long limite(String limite, String idCliente){
        int correcto = 0;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String id = String.valueOf(idCliente);

        ContentValues contenCliente = new ContentValues();
        contenCliente.put(Utilidades.C_LIMITE,limite);
        db.update(Utilidades.TABLA_CLIENTE,contenCliente,"id_cliente = ?", new String[]{id});

        return correcto;
    }

}

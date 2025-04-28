package com.example.mercado.Actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mercado.Entidades.Articulos;
import com.example.mercado.R;
import com.example.mercado.db.DbArticulos;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;

public class AgregarProducto extends AppCompatActivity {

    ImageButton btnScan;
    EditText txtCodBarra, txtNombreProd, txtPrecio;
    ImageView imgViewAgregarFoto;
    Button btnGuardar;

    Toolbar toolbarProductoADD;

    Articulos articulos;

    //Permiso de la clase Constants
    private  static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;
    //selección de imagen Constants
    private static final int IMAGE_PICK_CAMERA_CODE = 102;
    private static final int IMAGE_PICK_GALLERY_CODE = 103;
    // matrices de permisos
    private String[] cameraPermissions; // cámara y almacenamiento
    private String [] storagePermissions;// solo almacenamiento
    // variables (constain datos para guardar)
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);

        //Recolectar los datos de la caja de texto
        btnScan = findViewById(R.id.btnScanner);
        txtCodBarra = findViewById(R.id.txtCodBarra);
        txtNombreProd =(EditText) findViewById(R.id.txtApellidoCliente);
        txtPrecio =(EditText) findViewById(R.id.txtPrecio);
        imgViewAgregarFoto = findViewById(R.id.imgViewAgregarFoto);
        btnGuardar = findViewById(R.id.btnGuardar);
        toolbarProductoADD = findViewById(R.id.toolbarProductoADD);

        txtNombreProd.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        setSupportActionBar(toolbarProductoADD);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Agregar Producto");


        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(txtNombreProd.getText().toString().isEmpty()||txtPrecio.getText().toString().isEmpty()){
                  Toast.makeText(getApplicationContext(), "LLENE LOS CAPOS NOMBRE Y PRECIO", Toast.LENGTH_LONG).show();
              }else {
                  DbArticulos dbArticulos = new DbArticulos(AgregarProducto.this);

                  if(txtCodBarra.getText().toString().isEmpty()){
                      articulos = dbArticulos.nombreProductoVal(txtNombreProd.getText().toString());
                      if (articulos != null){
                          Toast.makeText(AgregarProducto.this, "Este Producto ya existe", Toast.LENGTH_LONG).show();
                          limpiar();
                      }else{
                          /// inserta producto a bd
                          long id = dbArticulos.insertarArticulo(txtCodBarra.getText().toString(), txtNombreProd.getText().toString(),txtPrecio.getText().toString(),ImageToByte(imgViewAgregarFoto),"1","0");

                          if(id > 0){
                              Toast.makeText(AgregarProducto.this, "REGISTRO GUARDADO", Toast.LENGTH_LONG).show();
                              limpiar();
                          }else{
                              Toast.makeText(AgregarProducto.this, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_LONG).show();
                          }

                      }
                  }else {
                      articulos = dbArticulos.buscarCodBarra(txtCodBarra.getText().toString());
                      if (articulos != null){
                          Toast.makeText(AgregarProducto.this, "Este Cod. de barra ya existe", Toast.LENGTH_LONG).show();
                          limpiar();
                      }else {
                          articulos = dbArticulos.nombreProductoVal(txtNombreProd.getText().toString());
                          if (articulos != null){
                              Toast.makeText(AgregarProducto.this, "Este Producto ya existe", Toast.LENGTH_LONG).show();
                              limpiar();
                          }else{
                              /// inserta producto a bd
                              long id = dbArticulos.insertarArticulo(txtCodBarra.getText().toString(), txtNombreProd.getText().toString(),txtPrecio.getText().toString(),ImageToByte(imgViewAgregarFoto),"1","0");

                              if(id > 0){
                                  Toast.makeText(AgregarProducto.this, "REGISTRO GUARDADO", Toast.LENGTH_LONG).show();
                                  limpiar();
                              }else{
                                  Toast.makeText(AgregarProducto.this, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_LONG).show();
                              }

                          }
                      }
                  }
              }
            }
        });

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrador = new IntentIntegrator(AgregarProducto.this);
                integrador.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrador.setPrompt("Lector de codigo de barra");
                integrador.setCameraId(0);
                integrador.setBeepEnabled(true);
                integrador.setBarcodeImageEnabled(true);
                integrador.initiateScan();
            }
        });


        //Inicializamos Permisos arrays
        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        imgViewAgregarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // muestra el cuadro de diálogo de selección de imagen
                imagePickDialog();
            }
        });
    }

    private void limpiar(){
        txtCodBarra.setText("");
        txtNombreProd.setText("");
        txtPrecio.setText("");
        imgViewAgregarFoto.setImageResource(R.drawable.camera);
    }

    //Configuracion de cod.barra
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result= IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result !=null){
            if(result.getContents()==null){
                Toast.makeText(this, "Lectora cancelada",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this, result.getContents(),Toast.LENGTH_LONG).show();
                txtCodBarra.setText(result.getContents());
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }

        //image picked from camera or gallery will be received hare
        if (resultCode == RESULT_OK){
            //Image is picked
            if(requestCode == IMAGE_PICK_GALLERY_CODE){
                //Picked from gallery

                //crop image
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(this);

            }
            else if(requestCode == IMAGE_PICK_CAMERA_CODE){
                //Picked from camera
                //crop Image
                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(this);

            }
            else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                //Croped image received
                CropImage.ActivityResult resp = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK){
                    Uri resultUri = resp.getUri();
                    imageUri = resultUri;
                    //set Image
                    imgViewAgregarFoto.setImageURI(resultUri);
                }
                else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    //ERROR
                    Exception error = resp.getError();
                    Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();
                }

            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private byte[] ImageToByte(ImageView imgViewAgregarFoto) {
        Bitmap bitmap=((BitmapDrawable) imgViewAgregarFoto.getDrawable()).getBitmap();
        ByteArrayOutputStream stream=new ByteArrayOutputStream( );
        bitmap.compress(Bitmap.CompressFormat.WEBP, 0, stream);
        byte[] bytes=stream.toByteArray();
        return bytes;
    }


    private void imagePickDialog(){
        // opciones para mostrar en el diálogo
        String[] options = {"Camara", "Galeria"};
        //dialogo
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Titulo
        builder.setTitle("Seleccionar imagen");
        // establecer elementos / opciones
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // manejar clicks
                if (which==0){
                    //click en camara
                    if (!checkCameraPermission()){
                        requestCameraPermission();
                    }
                    else{
                        // permiso ya otorgado
                        PickFromCamera();
                    }

                }
                else if (which==1){
                    if (!checkStoragePermission()){
                        requestStoragePermission();
                    }
                    else{
                        // permiso ya otorgado
                        PickFromGallery();
                    }
                }
            }
        });

        // Crear / mostrar diálogo
        builder.create().show();
    }

    private void PickFromGallery() {
        // intento de elegir la imagen de la galería, la imagen se devolverá en el método onActivityResult
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY_CODE);
    }

    private void PickFromCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Titulo de la Imagen");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Descripción de la imagen");
        //put image Uri
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        // Intento de abrir la cámara para la imagen
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }

    private boolean checkStoragePermission(){
        //comprobar si el permiso de almacenamiento está habilitado o no
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result;
    }

    private  void requestStoragePermission(){
        // solicita el permiso de almacenamiento
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission(){
        // verifica si el permiso de la cámara está habilitado o no
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result && result1;
    }

    private void requestCameraPermission(){
        // solicita el permiso de la cámara
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // resultado del permiso permitido / denegado

        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                if (grantResults.length>0){

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if(cameraAccepted && storageAccepted){
                        // ambos permisos permitidos
                        PickFromCamera();
                    }
                    else{
                        Toast.makeText(this, "Se requieren permisos de cámara y almacenamiento", Toast.LENGTH_SHORT).show();
                    }
                }

            }
            break;
            case STORAGE_REQUEST_CODE:{
                if (grantResults.length>0){

                    // si se permite devolver verdadero de lo contrario falso
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted){
                        // permiso de almacenamiento permitido
                        PickFromGallery();
                    }
                    else{
                        Toast.makeText(this, "Se requiere permiso de almacenamiento", Toast.LENGTH_SHORT).show();
                    }
                }

            }
            break;
        }
    }

}
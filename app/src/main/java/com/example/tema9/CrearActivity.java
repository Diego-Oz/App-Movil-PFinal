package com.example.tema9;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Instrumentation;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class CrearActivity extends AppCompatActivity {

    EditText etnombre, etdes, etprecio;
    FloatingActionButton btngua;
    CircleImageView foto;

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;
    private static final int IMAGE_PICK_CAMERA_CODE = 102;
    private static final int IMAGE_PICK_GALLERY_CODE = 103;
    private String[] permisoscamera;
    private String[] permisosstorage;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear);

        btngua = (FloatingActionButton)findViewById(R.id.btnguardar);
        etnombre = (EditText)findViewById(R.id.ednombre);
        etdes = (EditText)findViewById(R.id.edDes);
        etprecio = (EditText)findViewById(R.id.edprecio);
        foto = (CircleImageView)findViewById(R.id.foto);

        BDAlimentos dbali = new BDAlimentos(CrearActivity.this);

        permisoscamera = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        permisosstorage = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagenDialog();
            }
        });

        btngua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BDAlimentos dbali = new BDAlimentos(CrearActivity.this);
                long id = dbali.insertar(etnombre.getText().toString(), etdes.getText().toString(), etprecio.getText().toString(), imageUri.toString());

                if(id > 0){
                    Toast.makeText(CrearActivity.this, "Guardado Correcto", Toast.LENGTH_SHORT).show();
                    bye();
                }else{
                    Toast.makeText(CrearActivity.this, "Error de Guardado", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void imagenDialog(){
        String[] op = {"Cámara","Galeria"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccionar una imagen:");
        builder.setItems(op, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0){
                    if(!checkCameraPermisos()){
                        requestCameraPermisos();
                    }else {
                        PFCamara();
                    }
                }else if(which == 1){
                    if(!checkStoragePermisos()){
                        requestStoragePermisos();
                    }else{
                        PFGaleria();
                    }
                }
            }
        });
        builder.create().show();
    }

    private void PFGaleria(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY_CODE);
    }

    private void PFCamara(){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Titulo de la Imagen");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Descripción de la imagen");

        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraItent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraItent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraItent, IMAGE_PICK_CAMERA_CODE);
    }

    private  boolean checkStoragePermisos(){
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestStoragePermisos(){
        ActivityCompat.requestPermissions(this, permisosstorage, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermisos(){
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result && result2;
    }

    private void requestCameraPermisos(){
        ActivityCompat.requestPermissions(this, permisoscamera, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                if(grantResults.length > 0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if(cameraAccepted && storageAccepted){
                        PFCamara();
                    }else{
                        Toast.makeText(this, "Se necesitan los permisos de la cámara y el almacenamiento", Toast.LENGTH_LONG).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST_CODE:{
                if(grantResults.length > 0){

                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if(storageAccepted){
                        PFGaleria();
                    }else{
                        Toast.makeText(this, "Se necesita permiso de almacenamiento", Toast.LENGTH_LONG).show();
                    }
                }
            }
            break;
        }
    }

    protected  void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(this);
            } else if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(this);
            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    Uri resultUri = result.getUri();
                    imageUri = resultUri;
                    foto.setImageURI(resultUri);
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                    Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void bye(){
        Intent in = new Intent(this, MainAdmin.class);
        startActivity(in);
    }
}
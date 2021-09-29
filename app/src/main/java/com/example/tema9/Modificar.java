package com.example.tema9;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class Modificar extends AppCompatActivity {

    EditText ed1, ed2, ed3;
    FloatingActionButton btned, btnde;
    CircleImageView foto2;

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;
    private static final int IMAGE_PICK_CAMERA_CODE = 102;
    private static final int IMAGE_PICK_GALLERY_CODE = 103;
    private String[] cameraPermissions;
    private String[] storagePermissions;
    private Uri imageUri;

    boolean ed = false;
    Alimentos ali;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);

        ed1 = (EditText)findViewById(R.id.edtitulo2);
        ed2 = (EditText)findViewById(R.id.etdes2);
        ed3 = (EditText)findViewById(R.id.edprecio2);
        foto2 = (CircleImageView)findViewById(R.id.foto2);
        btned = (FloatingActionButton)findViewById(R.id.edit);
        btnde = (FloatingActionButton)findViewById(R.id.delete);

        if(savedInstanceState == null){
            Bundle ext = getIntent().getExtras();

            if(ext == null){
                id = Integer.parseInt(null);
            }else {
                id = ext.getInt("ID");
            }
        }else {
            id = (int) savedInstanceState.getSerializable("ID");
        }

        final BDAlimentos bdAlimentos = new BDAlimentos(Modificar.this);
        ali = bdAlimentos.modificar(id);

        if(ali != null){
            ed1.setText(ali.getNombre());
            ed2.setText(ali.getDescripcion());
            ed3.setText(ali.getPrecio());
            foto2.setImageURI(Uri.parse(ali.getImagen()));
        }

        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        foto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePickDialog();
            }
        });

        btned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ed1.getText().toString().equals("") && !ed2.getText().toString().equals("") && !ed3.getText().toString().equals("")){
                    ed = bdAlimentos.editar(id, ed1.getText().toString(), ed2.getText().toString(), ed3.getText().toString(), imageUri.toString());

                    if(ed){
                        Toast.makeText(Modificar.this, "Modificación correcta", Toast.LENGTH_SHORT).show();
                        bye();
                    }else {
                        Toast.makeText(Modificar.this, "Error de modificación", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Modificar.this, "Complete los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Modificar.this);
                builder.setMessage("Desea eliminar este Alimento?").setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(bdAlimentos.eliminar(id)){
                            sinnada();
                        }
                    }
                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(Modificar.this, "Cancelado", Toast.LENGTH_LONG).show();
                            }
                        }).show();
            }
        });
    }

    private void imagePickDialog(){
        String[] op = {"Cámara","Galeria"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccionar una imagen:");
        builder.setItems(op, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0){
                    if(!checkCameraPermission()){
                        requestCameraPermission();
                    }else {
                        PickFromCamera();
                    }
                }else if(which == 1){
                    if(!checkStoragePermission()){
                        requestStoragePermission();
                    }else{
                        PickFromGallery();
                    }
                }
            }
        });
        builder.create().show();
    }

    private void PickFromGallery(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY_CODE);
    }

    private void PickFromCamera(){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Titulo de la Imagen");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Descripción de la imagen");

        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraItent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraItent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraItent, IMAGE_PICK_CAMERA_CODE);
    }

    private  boolean checkStoragePermission(){
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission(){
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result && result2;
    }

    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
    }

    @Override
    public  boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
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
                        PickFromCamera();
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
                        PickFromGallery();
                    }else{
                        Toast.makeText(this, "Se necesita permiso de almacenamiento", Toast.LENGTH_LONG).show();
                    }
                }
            }
            break;
        }
    }

    protected  void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        if(resultCode == RESULT_OK){
            if(requestCode == IMAGE_PICK_GALLERY_CODE){
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);
            }else if(requestCode == IMAGE_PICK_CAMERA_CODE){
                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);
            }else if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK){
                    Uri resultUri = result.getUri();
                    imageUri = resultUri;
                    foto2.setImageURI(resultUri);
                }else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    Exception error = result.getError();
                    Toast.makeText(this,""+error, Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void bye(){
        Intent in = new Intent(this, MainAdmin.class);
        in.putExtra("ID", id);
        startActivity(in);
    }
    private void sinnada(){
        Intent intent = new Intent(this, MainAdmin.class);
        startActivity(intent);
    }
}
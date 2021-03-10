package com.example.projetmobile;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;


public class Ajouterproduit extends AppCompatActivity {
    EditText fullEmail,nomPro, prixPro;
    Button cher,upload;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog ;
    int Image_Request_Code = 7;
    Uri FilePathUri;
    ImageView imgview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouterproduit);
        cher=findViewById(R.id.btnchoix);
        upload=findViewById(R.id.btnsave);
        fullEmail=findViewById(R.id.mail);
        nomPro=findViewById(R.id.produit);
        prixPro=findViewById(R.id.prix);
        imgview = (ImageView)findViewById(R.id.imv);

        storageReference = FirebaseStorage.getInstance().getReference("Images");
        databaseReference = FirebaseDatabase.getInstance().getReference("Images");
        progressDialog = new ProgressDialog(Ajouterproduit.this);// context name as per your project name

        cher.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);
            }

        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImage();
            }
        });



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
                imgview.setImageBitmap(bitmap);
            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }


    public void UploadImage(){
        if (FilePathUri != null){
            progressDialog.setTitle("Image is Uploading...");
            progressDialog.show();
            StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));

            storageReference2.putFile(FilePathUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String emailVal =fullEmail.getText().toString().trim();
                    String nomProduit =nomPro.getText().toString().trim();
                    int prixproduit =Integer.parseInt(prixPro.getText().toString());
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();
                    @SuppressWarnings("VisibleForTests")
                            produit produit1 = new produit(nomProduit,emailVal,prixproduit,taskSnapshot.getUploadSessionUri().toString());
                    String ImageUpload = databaseReference.push().getKey();
                    databaseReference.child(ImageUpload).setValue(produit1);

                }
            });
        }
        else{
            Toast.makeText(Ajouterproduit.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();
        }
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();

    }
}

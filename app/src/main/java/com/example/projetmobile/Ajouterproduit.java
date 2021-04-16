package com.example.projetmobile;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projetmobile.firestorerecycleradapter.ProductsModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Ajouterproduit extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText nomPro, prixPro;
    Button cher,upload;
    StorageReference storageReference;
    //DatabaseReference databaseReference;
    ProgressDialog progressDialog ;
    int Image_Request_Code = 7;
    Uri FilePathUri;
    ImageView imgview;

    FirebaseFirestore fStore;
    String userID;
    FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouterproduit);
        cher=findViewById(R.id.btnchoix);
        upload=findViewById(R.id.btnsave);
        //fullEmail=findViewById(R.id.mail);
        nomPro=findViewById(R.id.produit);
        prixPro=findViewById(R.id.prix);
        imgview = findViewById(R.id.imv);

        storageReference = FirebaseStorage.getInstance().getReference("Images");

        progressDialog = new ProgressDialog(Ajouterproduit.this);// context name as per your project name
        fStore = FirebaseFirestore.getInstance();
        fauth= FirebaseAuth.getInstance();

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
                    //String emailVal =fullEmail.getText().toString().trim();
                    String nomProduit =nomPro.getText().toString().trim();

                    //statut=0;
                    int prixproduit =Integer.parseInt(prixPro.getText().toString());
                    userID = fauth.getCurrentUser().getUid();
                    DocumentReference documentReference =fStore.collection("Produits").document(nomProduit);



                    @SuppressWarnings("VisibleForTests")

                    Map<String,Object> produit1 = new HashMap<>();
                    produit1.put("nomPro",nomProduit);
                    produit1.put("prix_pro",prixproduit);
                    produit1.put("img_pro",System.currentTimeMillis()+"."+GetFileExtension(FilePathUri));
                    produit1.put("lien_pro",taskSnapshot.getUploadSessionUri().toString());
                    //produit1.put("id_user",userID);

                    ProductsModel produit =new ProductsModel();
                    produit.setImg_pro(System.currentTimeMillis()+"."+GetFileExtension(FilePathUri));
                    produit.setPrix_pro(prixproduit);
                    produit.setNomPro(nomProduit);
                    produit.setLien_pro(taskSnapshot.getUploadSessionUri().toString());

                    documentReference.set(produit).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "onSuccess:Produit ajouté "+userID);
                        }
                    });
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Produit ajoutèe avec succes  ", Toast.LENGTH_LONG).show();
                    Toast.makeText(Ajouterproduit.this,"Connecté !",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));


                }
            });
        }
        else{
            Toast.makeText(Ajouterproduit.this, "Veuillez selectionner une image !", Toast.LENGTH_LONG).show();
        }
    }

//    public void logout(View view) {
//        FirebaseAuth.getInstance().signOut();
//        startActivity(new Intent(getApplicationContext(),MainActivity.class));
//        finish();
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.menu_option2,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.voir2:

                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();

            case R.id.pan2:
                startActivity(new Intent(getApplicationContext(),Mon_Panier.class));
                finish();

            case R.id.dec2:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();

            default:

                return super.onOptionsItemSelected(item);

        }

    }
}

//Dans ajouter produit , voir nos produit ne marche pas
//Dans panier voir nos produit ne marche pas
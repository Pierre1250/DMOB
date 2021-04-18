package com.example.projetmobile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class Mon_Panier extends AppCompatActivity {

    adapterPanier pannier;
    Button send;
    String mRecipient;

    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon__panier);
        Intent intent=getIntent();
        ArrayList<product> listeEnCours =intent.getParcelableArrayListExtra("liste");
        lv = findViewById(R.id.pannier_id);
        //ArrayAdapter<product> adapter= new ArrayAdapter<product>(this, android.R.layout.simple_list_item_1,listeEnCours);
        //lv.setAdapter(adapter);

        send=findViewById(R.id.send);

        pannier=new adapterPanier(this,listeEnCours);
        lv.setAdapter(pannier);
        //mRecipient = emaill;



// Name, email address, and profile photo Url
        //String name = user.getDisplayName();

//        send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String recipent = mRecipient.trim();
//                String message ="ta commande";
//                sendEmail(recipent, message);
//            }
//        });



    }

//    private void sendEmail(String recipent, String message) {
//        Intent mEmail = new Intent(Intent.ACTION_SEND);
//
//        mEmail.setData(Uri.parse("mailto:"));
//        mEmail.setType("text/plain");
//        mEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{recipent});
//        mEmail.putExtra(Intent.EXTRA_SUBJECT, "Concerne commande");
//        mEmail.putExtra(Intent.EXTRA_TEXT, message);
//
//        try {
//            startActivity(Intent.createChooser(mEmail, "Choose an Email Client"));
//        }
//        catch (Exception e){
//            Toast.makeText( this, e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
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

                Intent intent = new Intent(Mon_Panier.this, MainActivity.class);
                startActivity(intent);

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
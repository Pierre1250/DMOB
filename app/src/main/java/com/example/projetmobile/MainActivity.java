package com.example.projetmobile;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetmobile.firestorerecycleradapter.ProductsModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth fauth;
    RecyclerView mFirestorelist;
    FirebaseFirestore firebaseFirestore;
    FirestoreRecyclerAdapter adapter;



    public final ArrayList<product>liste=new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fauth= FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = findViewById(R.id.fab);


        mFirestorelist= findViewById(R.id.firestore_list);
        firebaseFirestore =FirebaseFirestore.getInstance();

        Query query = firebaseFirestore.collection("Produits");

        FirestoreRecyclerOptions<ProductsModel> options =new FirestoreRecyclerOptions.Builder<ProductsModel>()
                .setQuery(query,ProductsModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<ProductsModel, ProductsViewHolder>(options) {
            @NonNull
            @Override
            public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items_single,parent,false);
                return new ProductsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final ProductsViewHolder holder, final int position, @NonNull final ProductsModel model) {
                holder.list_nomPro.setText("Nom :"+model.getNomPro());
                holder.list_prix_pro.setText("Prix :"+model.getPrix_pro()+""+"CHF");
                holder.list_type_pro.setText( model.getType_pro());
                String image_load="https://firebasestorage.googleapis.com/v0/b/connection-93827.appspot.com/o/Images%2F"+ model.getImg_pro()+"?alt=media&token=96aea1ac-7300-433a-b99d-7d2b9ad4ca80";

                System.out.println(image_load);
                Picasso.get().load(model.getLien_pro()).into(holder.list_image_pro);
                //Picasso.get().load(model.getLien_pro()).into(holder.list_image_pro);

                //holder.list_image_pro.set
                //holder.list_image_pro.setImageDrawable(Drawable.createFromPath("@drawable/preview"));
                //Picasso.get().load(model.getLien_pro()).into(holder.list_image_pro);
//                Glide.with(mFirestorelist)
//                        .load(model.getLien_pro())
//                        .into(holder.list_image_pro);

                //Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Ajoute);
                //holder.list_image_pro.setImageURI(Uri.parse("@drawable/

                

                // PANIER
                holder.commander.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        String nom= model.getNomPro();
                        Long prix=model.getPrix_pro();
                        product pro =new product();
                        pro.setNomPro(nom);
                        pro.setPrix_pro(prix);
                        liste.add(pro);
                        String message="Vous avez commandé :"+model.getNomPro()+"Total commande actuelle "+ liste.size();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();



                    }
                });






            }
        };
        adapter.startListening();
        mFirestorelist.setHasFixedSize(true);
        mFirestorelist.setLayoutManager(new LinearLayoutManager(this));
        mFirestorelist.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Mon_Panier.class);
                intent.putParcelableArrayListExtra("liste",liste);
                startActivity(intent);
            }
        });



    }

    private class ProductsViewHolder extends  RecyclerView.ViewHolder{

        TextView list_nomPro,list_prix_pro,list_img_pro,list_type_pro;
        ImageView list_image_pro;
        Button commander;

        public ProductsViewHolder(@NonNull View itemView ) {
            super(itemView);
            list_nomPro=itemView.findViewById(R.id.list_nomPro);
            list_prix_pro=itemView.findViewById(R.id.list_prix_pro);
            list_type_pro=itemView.findViewById(R.id.type_pro_li);
            list_image_pro=itemView.findViewById(R.id.list_image_pro);
            commander = itemView.findViewById(R.id.btnpan);










        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.menu_option,menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.dec2:
                FirebaseAuth.getInstance().signOut();
                Intent intent2 = new Intent(MainActivity.this, CreerCompte.class);
                startActivity(intent2);


            case R.id.ajpro1:
                Intent intent = new Intent(MainActivity.this, Ajouterproduit.class);
                startActivity(intent);



            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }


    public void commander(View view) {
        String message="Tu as commandeé";
        displayToast(message);
    }



}

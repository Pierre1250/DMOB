package com.example.projetmobile;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.se.omapi.Session;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static android.content.ContentValues.TAG;
import static androidx.core.content.ContextCompat.getSystemService;


public class adapterPanier extends BaseAdapter {
    private ArrayList<product> liste;
    private Context context;
    private LayoutInflater inflater;
    FirebaseFirestore fStore;
    FirebaseAuth fauth;
    String userID;
    @Override
    public int getCount() {
        return liste.size();
    }

    @Override
    public Object getItem(int position) {
        return liste.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView =inflater.inflate(R.layout.line_panier,null);
        TextView nom=convertView.findViewById(R.id.nom_li);
        TextView prix=convertView.findViewById(R.id.pri_li);
        Button commander =convertView.findViewById(R.id.button_li);
        final EditText ecrire =convertView.findViewById(R.id.qte_li);
        fauth= FirebaseAuth.getInstance();
        userID = fauth.getCurrentUser().getUid();
        fStore = FirebaseFirestore.getInstance();


        nom.setText("Nom du produit :"+liste.get(position).getNomPro());
        prix.setText("Prix du produit :"+(int) liste.get(position).getPrix_pro()+"");

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("My Notification","Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager;
            manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        commander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                final String username="kdreadzbarber3@gmail.com";
//                final String password="mdpadmin";


                String id = DateFormat.getDateTimeInstance().format(new Date());
                String val=ecrire.getText().toString().trim();
                long  i = Integer.parseInt ( val );
                long montant = (int) liste.get(position).getPrix_pro()*i;
//                ArrayList<String>litNot=new ArrayList<>();
//                litNot.add(liste.get(position).getNomPro());
//                litNot.add(val);
//                litNot.add(montant+"");
                final String message="La commande du produit "+liste.get(position).getNomPro()+" au prix de : "+ montant+""+"CHF a bien été enregistré";
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                //Notification
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"My Notification");
                builder.setContentTitle("Concerne votre commande");
                builder.setContentText(message);
                builder.setSmallIcon(R.drawable.panier_ic);
                builder.setAutoCancel(true);


                NotificationManagerCompat managerCompat=  NotificationManagerCompat.from(context);
                managerCompat.notify(1,builder.build());
//                Intent intent =new Intent(context,Notif_Activity.class);
//                intent.putStringArrayListExtra("liste",litNot);
//                PendingIntent pendingIntent = PendingIntent.getActivity(context,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                //builder.setContentIntent(pendingIntent);
                DocumentReference documentReference =fStore.collection("Commandes").document("Commande N ° : "+id);

                Map<String,Object> lineCommande = new HashMap<>();
                lineCommande.put("User_id",userID);
                lineCommande.put("Nom_produit",liste.get(position).getNomPro());
                lineCommande.put("Prix_pro",liste.get(position).getPrix_pro());
                lineCommande.put("Qté_pro",i);
                documentReference.set(lineCommande).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, message);
                    }
                });

                //Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        });
        return convertView;

    }



    public adapterPanier(Context context, ArrayList<product> liste){
        this.context=context;
        this.inflater=(LayoutInflater)  context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.liste=liste;
    }
}

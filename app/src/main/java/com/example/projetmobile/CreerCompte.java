package com.example.projetmobile;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreerCompte extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText fullName,fullEmail,fullpassword, fullnumber;
    Button fCreer;
    FirebaseAuth fauth;
    ProgressBar pgBar;
    FirebaseFirestore fStore;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_compte);

        fullName=findViewById(R.id.name);
        fullEmail=findViewById(R.id.mailN);
        fullpassword=findViewById(R.id.pwdN);
        fullnumber =findViewById(R.id.phoneN);
        fCreer=findViewById(R.id.btn2);
        pgBar=findViewById(R.id.proBar);
        fauth= FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        if (fauth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),Ajouterproduit.class));
            finish();
        }

        fCreer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailVal =fullEmail.getText().toString().trim();
                String pwdval = fullpassword.getText().toString().trim();
                final String phone = fullnumber.getText().toString().trim();
                final String name = fullName.getText().toString().trim();



                //Verification

                if (TextUtils.isEmpty(emailVal)){
                    fullEmail.setError("L'email est obligatoire");
                    return;
                }

                if (TextUtils.isEmpty(name)){
                    fullName.setError("L'email est obligatoire");
                    return;
                }
                if (TextUtils.isEmpty(pwdval)){
                    fullpassword.setError("le mot de passe est obligatoire ");
                    return;
                }

                if (TextUtils.isEmpty(phone)){
                    fullnumber.setError("le mot de passe est obligatoire ");
                    return;
                }
                if (pwdval.length() < 6){
                    fullpassword.setError("Le mot de passe doit etre  >= 6 caractères");
                    return;
                }


                pgBar.setVisibility(View.VISIBLE);
                //Creation du Users

                fauth.createUserWithEmailAndPassword(emailVal,pwdval).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(CreerCompte.this,"Votre compte a bien ete creé !",Toast.LENGTH_SHORT).show();
                            userID = fauth.getCurrentUser().getUid();
                            DocumentReference documentReference =fStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("userName",name);
                            user.put("email", emailVal);
                            user.put("phone",phone);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess:Utilisateur créé "+userID);
                                }
                            });
                            startActivity(new Intent(getApplicationContext(),Ajouterproduit.class));
                        }else{
                            Toast.makeText(CreerCompte.this,"Erreur de creation !"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });


    }

    public void connect(View view) {
        startActivity(new Intent(getApplicationContext(),Se_Connecter.class));
        finish();
    }
}
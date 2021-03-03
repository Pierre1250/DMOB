package com.example.projetmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreerCompte extends AppCompatActivity {
    EditText fullName,fullEmail,fullpassword, fullpwd2;
    Button fCreer;
    FirebaseAuth fauth;
    ProgressBar pgBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_compte);

        fullName=findViewById(R.id.name);
        fullEmail=findViewById(R.id.mailN);
        fullpassword=findViewById(R.id.pwdN);
        fullpwd2 =findViewById(R.id.phoneN);
        fCreer=findViewById(R.id.btn2);
        pgBar=findViewById(R.id.proBar);
        fauth= FirebaseAuth.getInstance();

        if (fauth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),Ajouterproduit.class));
            finish();
        }

        fCreer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailVal =fullEmail.getText().toString().trim();
                String pwdval = fullpassword.getText().toString().trim();
                String pwdval2 = fullpwd2.getText().toString().trim();


                //Verification

                if (TextUtils.isEmpty(emailVal)){
                    fullEmail.setError("L'email est obligatoire");
                    return;
                }
                if (TextUtils.isEmpty(pwdval)){
                    fullpassword.setError("le mot de passe est obligatoire ");
                    return;
                }

                if (TextUtils.isEmpty(pwdval2)){
                    fullpwd2.setError("le mot de passe est obligatoire ");
                    return;
                }
                if (pwdval.length() < 6){
                    fullpassword.setError("Le mot de passe doit etre  >= 6 caractères");
                    return;
                }
                if (pwdval2.length() < 6){
                    fullpwd2.setError("Le mot de passe doit etre  >= 6 caractères");
                    return;
                }
                //Check pour voir si le mdp est le meme
                /*if (pwdval2 != pwdval){
                    fullpwd2.setError("Les mots de passe sont differents");
                    return;
                }*/

                pgBar.setVisibility(View.VISIBLE);
                //Creation du Users

                fauth.createUserWithEmailAndPassword(emailVal,pwdval).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(CreerCompte.this,"Votre compte a bien ete creé !",Toast.LENGTH_SHORT).show();
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
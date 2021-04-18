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

public class Se_Connecter extends AppCompatActivity {
    EditText email,pwd;
    Button login;
    FirebaseAuth fAuth;
    ProgressBar pgBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connecter);
        email = findViewById(R.id.email);
        pwd = findViewById(R.id.pwd_txt);
        login = findViewById(R.id.btn_connect);
        pgBar=findViewById(R.id.progBar);

        fAuth=FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailVal =email.getText().toString().trim();
                String pwdval = pwd.getText().toString().trim();
                if (TextUtils.isEmpty(emailVal)){
                    email.setError("L'email est obligatoire");
                    return;
                }
                if (TextUtils.isEmpty(pwdval)){
                    pwd.setError("le mot de passe est obligatoire ");
                    return;
                }
                if (pwdval.length() < 6){
                    pwd.setError("Le mot de passe doit etre  >= 6 caractères");
                    return;
                }
                pgBar.setVisibility(View.VISIBLE);
                //Verification du users
                fAuth.signInWithEmailAndPassword(emailVal,pwdval).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Se_Connecter.this,"Connecté !",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(Se_Connecter.this,"Erreur de connection !"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });



    }

    public void creer(View view) {
        startActivity(new Intent(getApplicationContext(),CreerCompte.class));
        finish();
    }
}

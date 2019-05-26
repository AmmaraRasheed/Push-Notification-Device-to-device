package com.example.rabia.pushnotification;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class LoginUser extends AppCompatActivity {
    EditText email_user,password_user;
    Button login;
    TextView signUp;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        email_user=findViewById(R.id.email);
        password_user=findViewById(R.id.password);
        login=findViewById(R.id.login);
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        signUp=findViewById(R.id.signUp);
    }
    public void LoginUser(View view){
        String email,pass;
        email=email_user.getText().toString();
        pass=password_user.getText().toString();
        firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull final Task<AuthResult> task) {
                if(task.isSuccessful()){

                    String token= FirebaseInstanceId.getInstance().getToken();
                    String current_user=firebaseAuth.getCurrentUser().getUid();
                    Toast.makeText(getApplicationContext(),"hi"  +token,Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(),"hi" + current_user,Toast.LENGTH_SHORT).show();
                    Map<String,Object> tokenUser=new HashMap<>();
                    tokenUser.put("Token",token);
                    firebaseFirestore.collection("Users").document(current_user).update(tokenUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(), "hi", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(),"Failed login",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    public void  CreateNewUser(View v){
        Intent intent=new Intent(this,RegisterUser.class);
        startActivity(intent);
    }
}

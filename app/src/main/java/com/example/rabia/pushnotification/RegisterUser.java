package com.example.rabia.pushnotification;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterUser extends AppCompatActivity {
    EditText pass1,pass2,email;
    CircleImageView img;
    private static final int Pick_Image=1;
    private Uri imageUri;
    FirebaseAuth firebaseAuth;
    ProgressBar progress_bar;
    StorageReference storageReference;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        email=findViewById(R.id.email1);
        pass1=findViewById(R.id.password1);

        progress_bar=findViewById(R.id.progressBar);
        pass2=findViewById(R.id.password2);

        //save email and password to auth


        imageUri=null;
        firebaseAuth=FirebaseAuth.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference().child("image");
        firebaseFirestore=FirebaseFirestore.getInstance();
        img=findViewById(R.id.img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Image"),Pick_Image);
            }
        });

    }
    public void Register(View view){
        if(imageUri!=null){
            progress_bar.setVisibility(View.VISIBLE);
            final String email_user,pass_user1,pass_user2;
            email_user=email.getText().toString();
            pass_user1=pass1.getText().toString();
            pass_user2=pass2.getText().toString();
            if(email_user!=null && pass_user1!=null && pass_user2!=null){
                firebaseAuth.createUserWithEmailAndPassword(email_user,pass_user1)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    final String user_id=firebaseAuth.getCurrentUser().getUid();
                                    StorageReference profile=storageReference.child(user_id + ".jpg");
                                    profile.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                            if(task.isSuccessful()){
                                                final String downld_img=task.getResult().getDownloadUrl().toString();

                                                String token= FirebaseInstanceId.getInstance().getToken();
                                                Map<String,Object> userMap=new HashMap<>();
                                                userMap.put("email",email_user);
                                                userMap.put("Token",token);
                                                userMap.put("image",downld_img);
                                                firebaseFirestore.collection("Users").document(user_id).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()){
                                                                    newUser();

                                                                }
                                                                else{
                                                                    Toast.makeText(getApplicationContext(),"error firestre ",Toast.LENGTH_SHORT).show();
                                                                    progress_bar.setVisibility(View.INVISIBLE);
                                                                }
                                                            }
                                                });
                                            }else{
                                                Toast.makeText(getApplicationContext(),"error storage ",Toast.LENGTH_SHORT).show();
                                                progress_bar.setVisibility(View.INVISIBLE);
                                            }

                                        }
                                    });

                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"error auth ",Toast.LENGTH_SHORT).show();
                                    progress_bar.setVisibility(View.INVISIBLE);
                                }

                            }
                        });
            }
        }
    }

    private void newUser() {
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Pick_Image){
            imageUri=data.getData();
            img.setImageURI(imageUri);
        }
    }

    public void SignUp(View view){
        finish();
    }
}

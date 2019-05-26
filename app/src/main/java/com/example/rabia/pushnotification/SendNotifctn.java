package com.example.rabia.pushnotification;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SendNotifctn extends AppCompatActivity {
    TextView email,notification;
    private String userid;
    Button sendNotifictn;
    String sender_id,receiver_id,Receiver_email;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notifctn);
        email=findViewById(R.id.namenotifctn);
        firebaseFirestore=FirebaseFirestore.getInstance();
        notification=findViewById(R.id.notification1);
        receiver_id=getIntent().getStringExtra("UserId");
        Receiver_email=getIntent().getStringExtra("email");
        sender_id= FirebaseAuth.getInstance().getUid();

        email.setText(Receiver_email);
        sendNotifictn=findViewById(R.id.NotificationButtn);
        sendNotifictn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message;
                message=notification.getText().toString();
                if(message!=null){
                    Map<String,Object> notifctnMsg=new HashMap<>();
                    notifctnMsg.put("message",message);
                    notifctnMsg.put("from",sender_id);
                    firebaseFirestore.collection("Users").document(receiver_id).collection("Notification").add(notifctnMsg).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getApplicationContext(),"Send",Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"Error"+e,Toast.LENGTH_SHORT).show();

                        }
                    });

                }

            }
        });


    }
}

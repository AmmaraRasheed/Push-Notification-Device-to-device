package com.example.rabia.pushnotification;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileUser extends Fragment {
    FirebaseAuth firebaseAuth;
    Button logout;
    FirebaseFirestore firebaseFirestore;
    String userId;
    CircleImageView circleImageView;
    TextView email;


    public ProfileUser() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile_user, container, false);
        logout=view.findViewById(R.id.logoutProfile);
        email=view.findViewById(R.id.emailProfile);
        circleImageView=view.findViewById(R.id.imageProfile);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        userId=firebaseAuth.getCurrentUser().getUid();

        //get data from firebase firesto

        firebaseFirestore.collection("Users").document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String email2=documentSnapshot.getString("email");
                String img2=documentSnapshot.getString("image");
                email.setText(email2);
               RequestOptions requestOptions=new RequestOptions();
                requestOptions.placeholder(R.mipmap.ic_launcher);
               Glide.with(container.getContext()).setDefaultRequestOptions(requestOptions).load(img2).into(circleImageView);

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //delete token id
                Map<String,Object> deteletoken=new HashMap<>();
                deteletoken.put("Token",FieldValue.delete());
                firebaseFirestore.collection("Users").document(userId).update(deteletoken).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        firebaseAuth.signOut();
                        Intent intent=new Intent(container.getContext(),LoginUser.class);
                        startActivity(intent);
                    }
                });

            }
        });
        return view;
    }

}

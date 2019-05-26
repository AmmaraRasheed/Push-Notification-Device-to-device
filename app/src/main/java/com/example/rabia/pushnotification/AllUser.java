package com.example.rabia.pushnotification;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllUser extends Fragment {
    private RecyclerView recyclerView;
    private List<Users> usersList;
    private FirebaseFirestore firebaseFirestore;
    private UserAdapter userAdapter;


    public AllUser() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_all_user, container, false);
        usersList=new ArrayList<>();
        recyclerView=view.findViewById(R.id.AllUserRecycleView);
        firebaseFirestore=FirebaseFirestore.getInstance();
        userAdapter=new UserAdapter(container.getContext(),usersList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setAdapter(userAdapter);
        return view;
    }

   @Override
    public void onStart() {
        super.onStart();
        firebaseFirestore.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                usersList.clear();
                for(DocumentChange ds:documentSnapshots.getDocumentChanges()){
                    if(ds.getType()==DocumentChange.Type.ADDED){
                        String user_id=ds.getDocument().getId();
                        Users users=ds.getDocument().toObject(Users.class).withId(user_id);
                        usersList.add(users);
                        userAdapter.notifyDataSetChanged();
                    }
                }

            }
        });
    }
}

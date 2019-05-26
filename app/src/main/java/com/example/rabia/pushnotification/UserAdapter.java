package com.example.rabia.pushnotification;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter  extends  RecyclerView.Adapter<UserAdapter.ViewHolder>{
    private List<Users> list;
    private  Context context;
    public UserAdapter(Context context,List<Users> list){
        this.list=list;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_user_adapter,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final String email2=list.get(i).getEmail();
        viewHolder.email.setText(list.get(i).getEmail());
        final CircleImageView cr=viewHolder.img;
        Glide.with(context).load(list.get(i).getImage()).into(cr);
        final String id=list.get(i).user_id;

        viewHolder.v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,SendNotifctn.class);
                intent.putExtra("UserId",id);
                intent.putExtra("email",email2);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView img;
        private TextView email;
        private View v1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            v1=itemView;
            img=itemView.findViewById(R.id.adapterImage);
            email=itemView.findViewById(R.id.emailAdapter);
        }
    }

}

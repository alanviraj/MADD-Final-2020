package com.example.vehiclebath.UserHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.vehiclebath.Interface.ItemClickListner1;
import com.example.vehiclebath.R;

public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView UserName,UserPhone,UserEmail,UserPassword;
    public ItemClickListner1 listner;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);

        UserName = (TextView) itemView.findViewById(R.id.user_name);
        UserPhone = (TextView) itemView.findViewById(R.id.user_phone);
        UserEmail = (TextView) itemView.findViewById(R.id.user_email);
        UserPassword = (TextView) itemView.findViewById(R.id.user_password);

    }

    public void setItemClickListner(ItemClickListner1 listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View view) {
        listner.onClick(view,getAdapterPosition(),false);
    }
}

package com.example.vehiclebath.UserHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.vehiclebath.Interface.ItemClickListner1;
import com.example.vehiclebath.R;

public class UserViewHolderReport extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView UserName,UserPhone,UserEmail,UserPassword;
    public ItemClickListner1 listner;

    public UserViewHolderReport(@NonNull View itemView) {
        super(itemView);

        UserName = (TextView) itemView.findViewById(R.id.user_name_report);
        UserPhone = (TextView) itemView.findViewById(R.id.user_phone_report);
        UserEmail = (TextView) itemView.findViewById(R.id.user_email_report);
        UserPassword = (TextView) itemView.findViewById(R.id.user_password_report);

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

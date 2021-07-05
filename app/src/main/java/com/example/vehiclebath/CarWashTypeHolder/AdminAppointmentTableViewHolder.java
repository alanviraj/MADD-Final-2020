package com.example.vehiclebath.CarWashTypeHolder;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vehiclebath.Interface.ItemClickListener;
import com.example.vehiclebath.R;

public class AdminAppointmentTableViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView C_Name, type, dateTime;
    public ImageButton btnF;
    public ItemClickListener listner;

    public AdminAppointmentTableViewHolder (@NonNull View itemView){
        super(itemView);

        C_Name = (android.widget.TextView)itemView.findViewById(R.id.col1);
        type = (TextView)itemView.findViewById(R.id.col2);
        dateTime = (TextView)itemView.findViewById(R.id.col3);
        btnF = (ImageButton)itemView.findViewById(R.id.btn_fail);

    }

    public void setItemClickListner(ItemClickListener listner){
        this.listner =listner;
    }

    @Override
    public void onClick(View v)  {
        listner.onClick(v, getAdapterPosition(), false);
    }
}


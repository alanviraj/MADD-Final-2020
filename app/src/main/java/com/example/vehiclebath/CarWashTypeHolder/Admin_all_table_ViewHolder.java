package com.example.vehiclebath.CarWashTypeHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vehiclebath.Interface.ItemClickListener;
import com.example.vehiclebath.R;

public class Admin_all_table_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

    public TextView CName1, type1, dateTime1;
    public ItemClickListener listener;

    public Admin_all_table_ViewHolder(@NonNull View itemView) {
        super(itemView);

        CName1 = (TextView)itemView.findViewById(R.id.acol1);
        type1 = (TextView)itemView.findViewById(R.id.acol2);
        dateTime1 = (TextView)itemView.findViewById(R.id.acol3);
    }

    public void setItemClickListner(ItemClickListener listner){
        this.listener = listner;
    }

    @Override
    public void onClick(View v)   {
        listener.onClick(v, getAdapterPosition(), false);
    }
}

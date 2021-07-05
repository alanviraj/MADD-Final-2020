package com.example.vehiclebath.CarWashTypeHolder;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vehiclebath.Interface.ItemClickListener;
import com.example.vehiclebath.R;

public class adminProgressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView CName, type, dateTime;
    public ImageButton pbtnF;
    public ItemClickListener listner;

    public adminProgressViewHolder (@NonNull View itemView){
        super(itemView);

        CName = (TextView)itemView.findViewById(R.id.bcol);
        type = (TextView)itemView.findViewById(R.id.bcol2);
        dateTime = (TextView)itemView.findViewById(R.id.bcol3);
        pbtnF = (ImageButton)itemView.findViewById(R.id.pbtnF);

    }

    public void setItemClickListner(ItemClickListener listner){
        this.listner =listner;
    }

    @Override
    public void onClick(View v)  {
        listner.onClick(v, getAdapterPosition(), false);
    }
}

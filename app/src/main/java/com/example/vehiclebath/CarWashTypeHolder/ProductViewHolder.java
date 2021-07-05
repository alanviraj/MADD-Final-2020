package com.example.vehiclebath.CarWashTypeHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vehiclebath.Interface.CarWashTypeClickListener;

import com.example.vehiclebath.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView typeName, typeDescription, typePrice;
    public CardView productView;
    public CarWashTypeClickListener listner;
    public ImageView productImage;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        typeName = (TextView) itemView.findViewById(R.id.productName);
        typeDescription = (TextView) itemView.findViewById(R.id.productDescription);
        typePrice = (TextView) itemView.findViewById(R.id.productPrice);
        productView = (CardView)itemView.findViewById(R.id.productView);
        productImage = (ImageView)itemView.findViewById(R.id.productImage);
    }



    public void setItemClickListner(View.OnClickListener listner) {
        this.listner = (CarWashTypeClickListener) listner;
    }

    @Override
    public void onClick(View view) {
        listner.onClick(view, getAdapterPosition(),false);
    }

}

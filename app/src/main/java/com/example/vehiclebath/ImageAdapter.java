package com.example.vehiclebath;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context context;
    private List<CarWashType> carWashTypes;

    public ImageAdapter(Context context, List<CarWashType> carWashTypes) {
        this.context = context;
        this.carWashTypes = carWashTypes;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_layout, parent,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        CarWashType carWashType = carWashTypes.get(position);
        holder.typeName.setText(carWashType.getTypeName());
        holder.typeDescription.setText(carWashType.getTypeDescription());
        holder.typePrice.setText(carWashType.getTypePrice());
        Picasso.get().load(carWashType.getImgUrl()).into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return carWashTypes.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        public TextView typeName, typeDescription, typePrice;
        public ImageView productImage;


        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            typeName = (TextView) itemView.findViewById(R.id.productName );
            typeDescription = (TextView) itemView.findViewById(R.id.productDescription);
            typePrice = (TextView) itemView.findViewById(R.id.productPrice);
            productImage = (ImageView)itemView.findViewById(R.id.productImage);
        }
    }
}

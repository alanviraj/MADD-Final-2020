package com.example.vehiclebath.View_Holder1;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vehiclebath.Interface1.UserAddClickListner;
import com.example.vehiclebath.R;

public class Advertisement_View_Holder1 extends RecyclerView.ViewHolder implements View.OnClickListener{


    public TextView useradDesc;
    public ImageView userimageViewAd;
    public UserAddClickListner useradClickListner;

    public Advertisement_View_Holder1(@NonNull View itemView) {
        super(itemView);

        userimageViewAd = (ImageView) itemView.findViewById(R.id.viewuserAdds);
        useradDesc = (TextView) itemView.findViewById(R.id.viewuserDes);
    }

    public void setAdsClickListner (UserAddClickListner useradClickListner) {
        this.useradClickListner = useradClickListner;
    }


    @Override
    public void onClick(View view) {
        useradClickListner.onClick(view, getAdapterPosition(), false);
    }

}

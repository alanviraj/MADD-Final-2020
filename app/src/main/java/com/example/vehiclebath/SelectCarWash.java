package com.example.vehiclebath;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vehiclebath.CarWashTypeHolder.ProductViewHolder;
import com.example.vehiclebath.Prevalent1.Prevalent;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SelectCarWash extends AppCompatActivity {

    private DatabaseReference reference;

    //new Method
    private RecyclerView product_recycler;
    private ImageAdapter imageAdapter;
    private List<CarWashType> carWashTypes;
    private String passTypeName, logged ;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_car_wash);

        logged = getIntent().getStringExtra("logged");

        reference = FirebaseDatabase.getInstance().getReference().child("CarWashType");

        product_recycler = findViewById(R.id.product_recycler);
        product_recycler.setHasFixedSize(true);
        product_recycler.setLayoutManager(new LinearLayoutManager(this));
        progressBar = findViewById(R.id.progress_Bar);


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<CarWashType> options =
                new FirebaseRecyclerOptions.Builder<CarWashType>().setQuery(reference, CarWashType.class).build();

        FirebaseRecyclerAdapter<CarWashType, ProductViewHolder> adapter=
                new FirebaseRecyclerAdapter<CarWashType, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final CarWashType model) {

                        holder.typeName.setText(model.getTypeName());
                        holder.typeDescription.setText(model.getTypeDescription());
                        holder.typePrice.setText(model.getTypePrice());
                        Picasso.get().load(model.getImgUrl()).into(holder.productImage);

//                        passTypeName = model.getTypeName();

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(SelectCarWash.this, viewCarWash.class);
                                intent.putExtra("TypeName", model.getTypeName());
                                intent.putExtra("logged", logged);
                                startActivity(intent);
                            }
                        });

                        progressBar.setVisibility(View.INVISIBLE);

                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout,parent,false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };
        product_recycler.setAdapter(adapter);
        adapter.startListening();
    }
}
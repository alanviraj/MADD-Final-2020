package com.example.vehiclebath;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class adminAddCarwashType extends AppCompatActivity {

    private Button btn_addTypeDB;
    private EditText edt_typeName, edt_TypeDesc, edt_TypePrice;
    private ProgressDialog loadingBar;

    //image
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button btn_choose_img;
    private ImageView imageView2;
    private ProgressBar progressBar;
    private Uri mImageUri;
    private String downloadImgUrl;

    private StorageReference mStorageRef;
    private DatabaseReference RootRef;
    private UploadTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_carwash_type);

        edt_typeName =(EditText) findViewById(R.id.edt_typeName);
        edt_TypeDesc =(EditText) findViewById(R.id.edt_TypeDesc);
        edt_TypePrice =(EditText) findViewById(R.id.edt_TypePrice);
        btn_addTypeDB = (Button) findViewById(R.id.btn_addTypeDB);

        btn_choose_img = findViewById(R.id.btn_choose_img);
        imageView2 = findViewById(R.id.imageView2);
        progressBar = findViewById(R.id.progressBar);

        loadingBar = new ProgressDialog(this);

        mStorageRef = FirebaseStorage.getInstance().getReference("CarWashType");
        RootRef = FirebaseDatabase.getInstance().getReference();

        btn_choose_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });


        btn_addTypeDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createType();
                Intent intent = new Intent(adminAddCarwashType.this, admin_main.class);
                startActivity(intent);
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            mImageUri = data.getData();

            Picasso.get().load(mImageUri).into(imageView2);
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cR.getType(uri));
    }

    private void createType() {
        String TypeName = edt_typeName.getText().toString();
        String TypeDesc = edt_TypeDesc.getText().toString();
        String TypePrice = edt_TypePrice.getText().toString();

        //Image upload
        if (mImageUri == null){
            Snackbar.make(getWindow().getDecorView().getRootView(), "Please select the Image", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

        else if (TextUtils.isEmpty(TypeName)){
            Snackbar.make(getWindow().getDecorView().getRootView(), "Please Enter the Name", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        else if (TextUtils.isEmpty(TypeDesc)){
            Snackbar.make(getWindow().getDecorView().getRootView(), "Please Enter the Description", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        else if (TextUtils.isEmpty(TypePrice)){
            Snackbar.make(getWindow().getDecorView().getRootView(), "Please Enter the Price", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        else{

            loadingBar.setTitle("Add Type");
            loadingBar.setMessage("We are Adding the Type");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidateTypeName(TypeName, TypeDesc, TypePrice );
        }
    }

    private void ValidateTypeName(final String typeName, final String typeDes, final String typePrice) {

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("CarWashType").child(typeName).exists())){

                    final StorageReference fileRef = mStorageRef.child(typeName+"."+getFileExtension(mImageUri));
                    uploadTask=fileRef.putFile(mImageUri);
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                    if(!task.isSuccessful()){
                                        throw task.getException();
                                    }
                                    downloadImgUrl=fileRef.getDownloadUrl().toString();
                                    return fileRef.getDownloadUrl();
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.isSuccessful()){
                                        downloadImgUrl = task.getResult().toString();
                                        insertTypetoDB(typeName,typeDes,typePrice);
                                    }
                                }
                            });


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(adminAddCarwashType.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                else{
                    Toast.makeText(adminAddCarwashType.this, typeName+ " Type Already Exists", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        loadingBar.dismiss();
    }

    private void insertTypetoDB(String typeName, String typeDes, String typePrice) {
        CarWashType carWashType = new CarWashType
                (typeName, typeDes, typePrice, downloadImgUrl);

        RootRef.child("CarWashType").child(typeName).setValue(carWashType).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(adminAddCarwashType.this, "New CarWash Type is Added", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(adminAddCarwashType.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(adminAddCarwashType.this, "Failed to Add New CarWash Type", Toast.LENGTH_LONG).show();
                }
                loadingBar.dismiss();
            }
        });
    }

}

package com.example.sabinmj.blueb;

/**
 * Created by Sabin M J on 05-Oct-16.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sabinmj.blueb.model.BlogPost;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class blogreader extends AppCompatActivity {

    private static final int GALLERY=100;
    private ImageView pickimagebtn;
    private Uri Imageuri;
    private ProgressDialog mprogressDialog;

    StorageReference storageReference= FirebaseStorage.getInstance().getReference("image");
    DatabaseReference databaseReference;
    private EditText etTitle;
    private EditText etDesc;

    private static final int REQUEST_RUNTIME_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blogspot);









        databaseReference= FirebaseDatabase.getInstance().getReference("blueb");
        pickimagebtn=(ImageView)findViewById(R.id.ibtnadd);
        etTitle=(EditText)findViewById(R.id.etBlogTitel);
        etDesc=(EditText)findViewById(R.id.etdesc);
        mprogressDialog=new ProgressDialog(this);








checkPremission();


    }

    public void pickupimage(View view) {
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if ((requestCode==GALLERY)&&(resultCode==RESULT_OK)){
            Imageuri=data.getData();
            pickimagebtn.setImageURI(Imageuri);

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void uploaddatas(View view) {
        mprogressDialog.setMessage("uploading.....");
        mprogressDialog.show();
        storageReference.child(Imageuri.getLastPathSegment()).putFile(Imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadURI = taskSnapshot.getDownloadUrl();
                String dow= String.valueOf(downloadURI);
                DatabaseReference daRef = databaseReference.push();
                BlogPost blogPost =new BlogPost(etTitle.getText().toString(),etDesc.getText().toString(),dow);
                daRef.setValue(blogPost);
                mprogressDialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("failed",e.getMessage());
                Toast.makeText(getApplicationContext(),"Image failed",Toast.LENGTH_LONG).show();
            }
        });
    }






    boolean checkPremission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if(checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                //your permission is granted
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else {
            //permission is automatically granted on devices lower than android M upon installation

            return true;
        }
    }








}

package com.example.jeekota;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, ChildEventListener  {


    DatabaseReference reference;
    List<Model> modellist = new ArrayList<>();
    //  FirebaseDatabase firebaseDatabase;
    StorageReference storageReference;

    EditText pdfName;
    Button btn;
    ImageView image;
    Context context;
    Uri filepath;
    String subject;
    Spinner spinner;
    //ChatFragment.listener listener;
    private StorageReference ref;
    Viewmodel viewmodel;
    Context mainActivitycontext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        pdfName = findViewById(R.id.edit_pdf_name);
        image = findViewById(R.id.imageView);
        btn = findViewById(R.id.button);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPdf();
            }
        });

        reference = FirebaseDatabase.getInstance().getReference().child("PdfDocuments");
        storageReference = FirebaseStorage.getInstance().getReference("pdfFiles");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(pdfName.getText().toString())) {
                    UploadPdf(filepath);
                } else {
                    Toast.makeText(context, "Enter pdfname", Toast.LENGTH_LONG).show();
                }
            }
        });


        spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.subject_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Toast.makeText(AdminActivity.this, "resultCode" + resultCode, Toast.LENGTH_LONG).show();
            filepath = uri;
        }

    }

    private void UploadPdf(Uri uri) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("file uploading...!!");
        pd.show();
        StorageReference ref = storageReference.child(String.valueOf(System.currentTimeMillis()));
        ref.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(AdminActivity.this, "file uploaded in storage", Toast.LENGTH_LONG).show();
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                pd.dismiss();
                                Toast.makeText(AdminActivity.this, "successfully got url", Toast.LENGTH_LONG).show();
                                String pdf = pdfName.getText().toString();
                                Model model = new Model(pdf, subject, uri.toString());
                                reference.push().setValue(model);
                                Toast.makeText(AdminActivity.this, "successfully uploaded in database", Toast.LENGTH_LONG).show();
                                pdfName.setText("");
                               reference.addChildEventListener(AdminActivity.this);


                            }
                        });

                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        float percent = (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        pd.setMessage("uploaded :" + (int) percent + "%");
                    }
                });

    }


    private void pickPdf() {

        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent i = new Intent();
                        i.setType("application/pdf");
                        i.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(i, "select pdf files"), 100);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        subject = (String) parent.getItemAtPosition(position);


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onStop() {
        reference.removeEventListener(this);
        super.onStop();
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
           Toast.makeText(this,"child has been added",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }


}
package com.example.zotfit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home_Fragment extends Fragment {
    ListView listview;
    List friendslist=new ArrayList<>();
    ArrayAdapter adapter;
    Calparse calparse;
    TextView Name;
    Database db;
    final static int gallerypic=1;
    de.hdodenhof.circleimageview.CircleImageView circleImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home,null);

    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       circleImageView= (CircleImageView) view.findViewById(R.id.circleimage);
        db=new Database(getContext());
        Name=view.findViewById(R.id.nametext);
        Name.setText(MainActivity.user);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery=new Intent();
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery,gallerypic);
            }
        });

        this.calparse = new Calparse();
        calparse.execute();



    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==gallerypic && data!=null){
            Uri imaguri=data.getData();
            circleImageView.setImageURI(imaguri);

            /*
            CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1,1).start(getActivity());
            if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                CropImage.ActivityResult result=CropImage.getActivityResult(data);
                Uri resulturi=result.getUri();

            }*/

        }
    }





}

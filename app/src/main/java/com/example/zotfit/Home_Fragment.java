package com.example.zotfit;

import android.content.Intent;
import android.graphics.Path;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home_Fragment extends Fragment {
    PieChart pieChart;
    BarChart barChart;
    TextView Name;
    Database db;
    final static int GALLERY_PIC =1;
    final static int OPEN_IMAGE = 2;
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
        pieChart = view.findViewById(R.id.piechart);
       // barChart = view.findViewById(R.id.barChart);
        Name.setText(MainActivity.user);
        setUpPieChart();
       // setUpBarChart();
        if(db.getimage(Preferences.INSTANCE.getUsername())!= null){

            Uri uri = Uri.parse(db.getimage(Preferences.INSTANCE.getUsername()));
            try {
                InputStream input = getContext().getContentResolver().openInputStream(uri);
                assert input != null;
                circleImageView.setImageResource(input.read());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery=new Intent();
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery, GALLERY_PIC);
            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== GALLERY_PIC && data!=null){
            Uri imaguri=data.getData();
            circleImageView.setImageURI(imaguri);
            db.insertimage(imaguri);

        }
//        else if( requestCode == OPEN_IMAGE && data !=null){
//
//        }

    }

    private void openFile(String uri){
        Intent openImage = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        openImage.addCategory(Intent.CATEGORY_OPENABLE);
        openImage.setFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION| Intent.FLAG_GRANT_READ_URI_PERMISSION| Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        openImage.setData(Uri.parse(uri));
        startActivityForResult(openImage, OPEN_IMAGE);
    }


    private void setUpPieChart(){
        ArrayList<PieEntry> NoOfEmp = new ArrayList();
        NoOfEmp.add(new PieEntry(945f, 0));
        NoOfEmp.add(new PieEntry(1040f, 1));
        NoOfEmp.add(new PieEntry(1133f, 2));
        NoOfEmp.add(new PieEntry(1240f, 3));
        NoOfEmp.add(new PieEntry(1369f, 4));
        NoOfEmp.add(new PieEntry(1487f, 5));
        NoOfEmp.add(new PieEntry(1501f, 6));
        NoOfEmp.add(new PieEntry(1645f, 7));
        NoOfEmp.add(new PieEntry(1578f, 8));
        NoOfEmp.add(new PieEntry(1695f, 9));
        PieDataSet dataSet = new PieDataSet(NoOfEmp, "Number Of Employees");

        ArrayList year = new ArrayList();

        year.add("2008");
        year.add("2009");
        year.add("2010");
        year.add("2011");
        year.add("2012");
        year.add("2013");
        year.add("2014");
        year.add("2015");
        year.add("2016");
        year.add("2017");
        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);
        pieChart.animateXY(2000, 2000);
    }

    private void setUpBarChart(){
        ArrayList<BarEntry> healthData = new ArrayList();

        healthData.add(new BarEntry(200, 0));
        healthData.add(new BarEntry(14f, 1));
        healthData.add(new BarEntry(200, 2));
        healthData.add(new BarEntry(100, 3));

        ArrayList year = new ArrayList();

        year.add("2008");
        year.add("2009");
        year.add("2010");
        year.add("2011");
        year.add("2012");
        year.add("2013");
        year.add("2014");
        year.add("2015");
        year.add("2016");
        year.add("2017");

        BarDataSet bardataset = new BarDataSet(healthData, "No Of Employee");
        barChart.animateY(1000);
        BarData data = new BarData(bardataset);
        bardataset.setColors(ColorTemplate.PASTEL_COLORS);
        barChart.setData(data);

    }



}

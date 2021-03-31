package com.example.zotfit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home_Fragment extends Fragment {
    PieChart pieChart;
    BarChart barChart;
    TextView Name;
    Database db;
    Integer calories;
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
        db=Database.getInstance(getContext());
        Name=view.findViewById(R.id.nametext);
        pieChart = view.findViewById(R.id.piechart);
        Name.setText(MainActivity.user);
        setUpPieChart();
        if(calories != null){
            String cals = getString(R.string.calorie_amount, calories);
            TextView calorie_amount = view.findViewById(R.id.calorie_amount);
            calorie_amount.setText(cals);
        }
        if(db.getimage(Preferences.INSTANCE.getUsername())!= null){

            Uri uri = Uri.parse(db.getimage(Preferences.INSTANCE.getUsername()));
            try {
                InputStream input = Objects.requireNonNull(getContext()).getContentResolver().openInputStream(uri);
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
    }

    private void openFile(String uri){
        Intent openImage = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        openImage.addCategory(Intent.CATEGORY_OPENABLE);
        openImage.setFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION| Intent.FLAG_GRANT_READ_URI_PERMISSION| Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        openImage.setData(Uri.parse(uri));
        startActivityForResult(openImage, OPEN_IMAGE);
    }


    private void setUpPieChart(){
        DailyData dailyData = db.getDailyData(Preferences.INSTANCE.getUsername());
        ArrayList<PieEntry> healthData = new ArrayList();
        calories = (int) dailyData.getCalorories();
        healthData.add(new PieEntry(dailyData.getProtein(), "Protein(g)"));
        healthData.add(new PieEntry(dailyData.getFat(), "Fat(g)"));
        healthData.add(new PieEntry(dailyData.getCarbohydrates(), "Carbs(g)"));
        PieDataSet dataSet = new PieDataSet(healthData, "");
        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        dataSet.setValueLineColor(R.color.black);
        data.setValueTextSize(14f);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieChart.animateXY(1000, 1000);
    }


}

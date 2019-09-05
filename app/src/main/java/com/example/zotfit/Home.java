package com.example.zotfit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.List;


public class Home extends AppCompatActivity {
    Toolbar toolbar;
    List food;
    Calparse calparse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("ZOTFIT");

        this.calparse=new Calparse(this);
        //calparse.execute();

            food=calparse.searchfood();

       /* for(Object item:food){
            System.out.println(item);

        }*/

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menufile,menu);
        return true;


    }

    public void parsecalorie(){

       /* try {
            JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
            String requestID = object.getString("requestId");
            int likelihood = object.getInt("likelihood");
            JSONArray photos = object.getJSONArray("photos");

        } catch (JSONException e) {
            // Appropriate error handling code
        }*/
    }

    private void setuppiechart(){




    }
}

package com.example.zotfit;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.fatsecret.platform.model.CompactFood;
import com.fatsecret.platform.model.CompactRecipe;
import com.fatsecret.platform.model.Food;
import com.fatsecret.platform.services.Response;
import com.fatsecret.platform.services.android.Request;

import com.fatsecret.platform.model.Recipe;
import com.fatsecret.platform.services.FatsecretService;
import com.fatsecret.platform.services.android.ResponseListener;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.List;


public class Home extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    List food;
    Fragment fragment;
    Calparse calparse;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        String key = "2bddcb3b9540419c83d720f4eef90eb8";
        String secret = "13e285dbfec5421a94a705f20659eaa2";


       // FatsecretService service = new FatsecretService(key, secret);
        String query = "pasta"; //Your query string
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Listener listener = new Listener();

        Request req = new Request(key, secret, listener);
        req.getFoods(requestQueue, query,3);
        req.getFood(requestQueue, 29304L);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //  toolbar.setTitle("ZOTFIT");
        // toolbar.setTitleTextColor(getResources().getColor(R.color.babyblue));
        toolbar.setLogo(R.drawable.zotfit);

        this.calparse = new Calparse(this);
        //calparse.execute();

//        food = calparse.searchfood();


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.action_home); // change to whichever id should be default
        }



    }


    class Listener implements ResponseListener {

        @Override
        public void onFoodListRespone(Response<CompactFood> foods) {
            System.out.println("TOTAL FOOD ITEMS: " + foods.getTotalResults());

            List<CompactFood> foodlist = foods.getResults();
            //This list contains summary information about the food items

            System.out.println("=========FOODS============");
            for (CompactFood food: foodlist) {
                System.out.println(food.getName());
            }
        }

        @Override
        public void onRecipeListRespone(Response<CompactRecipe> recipes) {
            System.out.println("TOTAL RECIPES: " + recipes.getTotalResults());

            List<CompactRecipe> recipelist = recipes.getResults();
            System.out.println("=========RECIPES==========");
            for (CompactRecipe recipe: recipelist) {
                System.out.println(recipe.getName());
            }

        }

        @Override
        public void onFoodResponse(Food food) {
            System.out.println("FOOD NAME: " + food.getName());
        }

        @Override
        public void onRecipeResponse(Recipe recipe) {
            System.out.println("RECIPE NAME: " + recipe.getName());
        }
    }




    @Override
    public boolean onNavigationItemSelected( @NonNull  MenuItem item){
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;
        if (id == R.id.action_friends) {
            fragmentClass = Friends_Fragment.class;
        } else if (id == R.id.action_progress) {
            fragmentClass = Progress_Fragment.class;
        }
        else if (id==R.id.action_search){
            fragmentClass= Search_Fragment.class;
        }
        else if(id==R.id.action_home){
            fragmentClass=Home_Fragment.class;
        }




        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(fragment!=null){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_layout, fragment).commit();}
       /* for(Object item:food){
            System.out.println(item);

        }*/

       return true;

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

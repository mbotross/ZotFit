package com.example.zotfit;

import android.os.AsyncTask;

import com.fatsecret.platform.model.CompactFood;
import com.fatsecret.platform.services.FatsecretService;
import com.fatsecret.platform.services.Request;
import com.fatsecret.platform.services.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class Calparse extends AsyncTask<Void, Void, String> {

    Home home;
    public Request request;

    @Override
    protected String doInBackground(Void... voids) {
        try {
            //URL url= new URL("https://platform.fatsecret.com/js?key=2bddcb3b9540419c83d720f4eef90eb8");
            URL url = new URL("https://platform.fatsecret.com/js?key=2bddcb3b9540419c83d720f4eef90eb8");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;


                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();

                return stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                con.disconnect();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }


        //    con.setRequestProperty("42680355", "c551adace3f64dc6d6e6b92bacfcb66a","0");


        return null;
    }


    public List searchfood() {
        String key = "2bddcb3b9540419c83d720f4eef90eb8";
        String secret = "13e285dbfec5421a94a705f20659eaa2";


        FatsecretService service = new FatsecretService(key, secret);

        String query = "pasta"; //Your query string
        //  RequestQueue requestQueue = Volley.newRequestQueue();
        // com.android.volley.Response.Listener listener = new com.android.volley.Response.Listener();

        //Request req = new Request(key, secret, listener);
        Response<CompactFood> response = service.searchFoods(query);

        //This response contains the list of food items at zeroth page for your query

        List<CompactFood> results = response.getResults();
        // return results;
        return results;
    }

    protected void onPostExecute(String response) {
        if (response == null) {
            //response = "THERE WAS AN ERROR";
            System.out.println("im nto ai");
        } else {
            System.out.println("Successs");
        }


    }
}

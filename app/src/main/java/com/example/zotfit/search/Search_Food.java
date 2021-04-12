package com.example.zotfit.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;

import com.example.zotfit.Calparse;
import com.example.zotfit.DataViewModel;
import com.example.zotfit.R;
import com.fatsecret.platform.model.CompactFood;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Search_Food extends AppCompatActivity implements onItemClick{
    String fooditem;
    List <CompactFood> food;
    Calparse calparse = new Calparse();
    FoodAdapter adapter;
    RecyclerView recyclerView;
    DataViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__food);
        SearchView searchView=findViewById(R.id.searchView);
        adapter = new FoodAdapter(getApplicationContext(), this);
        recyclerView = findViewById(R.id.search_recycler);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        executeSearch("p");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fooditem = query;
                executeSearch(fooditem);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                executeSearch(newText);
                return false;
            }
        });


    }



    private void executeSearch(String searchItem){
        Callable callable = new Callable<List<CompactFood>>() {
            @Override
            public List<CompactFood> call() throws Exception {
                return calparse.searchfood(searchItem);

            }
        };


        Observable<List<CompactFood>> observable = Observable.fromCallable(callable);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Observer<List<CompactFood>>() {

                            @Override
                            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<CompactFood> compactFoods) {
                                food = compactFoods;
                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onComplete() {
                                adapter.setWords(food);
                            }
                        });

    }

    @Override
    public void onItemClick(@NotNull CompactFood itemsList, int position) {
        Intent intent = new Intent(getApplicationContext(), Item_Details.class);
        intent.putExtra("url", itemsList.getUrl());
        intent.putExtra("description", itemsList.getDescription());
        startActivity(intent);
    }
}
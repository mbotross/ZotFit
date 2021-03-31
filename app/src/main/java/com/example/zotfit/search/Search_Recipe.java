package com.example.zotfit.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;

import com.example.zotfit.Calparse;
import com.example.zotfit.R;
import com.fatsecret.platform.model.CompactFood;
import com.fatsecret.platform.model.CompactRecipe;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Search_Recipe extends AppCompatActivity implements onRecipeClick {
    String recipeitem;
    List<CompactRecipe> recipes;
    Calparse calparse = new Calparse();
    Recipe_Adapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__recipe);
        SearchView searchView=findViewById(R.id.searchView);
        adapter = new Recipe_Adapter(getApplicationContext(), this);
        recyclerView = findViewById(R.id.search_recycler);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        executeSearch("");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recipeitem = query;
                executeSearch(recipeitem);
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
        Callable callable = new Callable<List<CompactRecipe>>() {
            @Override
            public List<CompactRecipe> call() throws Exception {
                return calparse.searchRecipe(searchItem);

            }
        };


        Observable<List<CompactRecipe>> observable = Observable.fromCallable(callable);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Observer<List<CompactRecipe>>() {

                            @Override
                            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<CompactRecipe> compactRecipes) {
                                recipes= compactRecipes;
                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onComplete() {
                                adapter.setWords(recipes);
                            }
                        });

    }

    @Override
    public void onRecipeClick(@NotNull CompactRecipe itemsList, int position) {
        Intent intent = new Intent(getApplicationContext(), Item_Details.class);
        intent.putExtra("url", itemsList.getUrl());
        startActivity(intent);
    }
}
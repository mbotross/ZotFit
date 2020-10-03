package com.example.zotfit.search;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zotfit.Calparse;
import com.example.zotfit.R;
import com.fatsecret.platform.model.CompactFood;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Search_Fragment extends Fragment{
    @Nullable
    String fooditem;
    List <CompactFood> food;
    Calparse calparse = new Calparse();
    SearchAdapter adapter;
    RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getActivity()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search,null);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button searchFood = view.findViewById(R.id.search_food);
        searchFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Search_Food.class);
                startActivity(intent);
            }
        });
//        SearchView searchView=view.findViewById(R.id.searchView);
//        adapter = new SearchAdapter(Objects.requireNonNull(getContext()), this);
//        recyclerView = view.findViewById(R.id.search_recycler);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//
//                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                fooditem = query;
//                executeSearch(fooditem);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                executeSearch(newText);
//                return false;
//            }
//        });
//



    }

//    private void executeSearch(String searchItem){
//        Callable callable = new Callable<List<CompactFood>>() {
//            @Override
//            public List<CompactFood> call() throws Exception {
//                return calparse.searchfood(searchItem);
//
//            }
//        };
//
//
//        Observable<List<CompactFood>> observable = Observable.fromCallable(callable);
//        observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//            new Observer<List<CompactFood>>() {
//
//            @Override
//            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<CompactFood> compactFoods) {
//                food = compactFoods;
//            }
//
//            @Override
//            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onComplete() {
//                adapter.setWords(food);
//            }
//        });
//
//    }
//
//    @Override
//    public void onItemClick(@NotNull CompactFood itemsList, int position) {
//        Intent intent = new Intent(getContext(), Item_Details.class);
//        intent.putExtra("url", itemsList.getUrl());
//        intent.putExtra("description", itemsList.getDescription());
//        startActivity(intent);
//    }
}



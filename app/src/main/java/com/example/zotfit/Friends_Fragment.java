package com.example.zotfit;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Friends_Fragment extends Fragment {
    ListView listview;
    View view;
    List friendslist=new ArrayList<>();
    ArrayAdapter adapter;
    Database db;
    String[] userlist,passlist;
    Button addbutton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment,null);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listview=(ListView)view.findViewById(R.id.friends_list_view);


        db=new Database(getContext());
        showusers();
        if(userlist!=null){
            for(String fitter:userlist){
                friendslist.add(fitter);}}

        CustomAdapter customAdapter=new CustomAdapter();

      //  adapter=new ArrayAdapter(getActivity(),R.layout.fragment,R.id.friendstext, friendslist);
        listview.setAdapter(customAdapter);
        addbutton=view.findViewById(R.id.addbutton);
/*        addbutton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {


            }
        });*/


    }



    public void showusers(){
        int i=0;
        Cursor cursor= db.getuser();
        if(cursor.moveToFirst()){

            userlist = new String[cursor.getCount()];
            passlist=new String[cursor.getCount()];
            do{
                userlist[i]=cursor.getString(cursor.getColumnIndex(Database.COL1));
                passlist[i]=cursor.getString(cursor.getColumnIndex(Database.COL2));
                i++;

            }while (cursor.moveToNext());
        }

    }

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return userlist.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            showusers();
            view=getLayoutInflater().inflate(R.layout.customlayout,null);
            ImageView imageView=(ImageView)view.findViewById(R.id.friendimage);
            TextView textView=(TextView)view.findViewById(R.id.friendname);
            de.hdodenhof.circleimageview.CircleImageView circleImageView= (CircleImageView) view.findViewById(R.id.circleimage);
            circleImageView.setImageResource(R.drawable.zotfit);
            textView.setText(userlist[position]);
            return view;
        }
    }
}

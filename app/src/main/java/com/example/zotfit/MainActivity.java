package com.example.zotfit;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {


    EditText username,password;
    Button login,register;
    static String user, pass;
    Database mydb;
    Intent intent;
    String[] userlist,passlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);



        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.login);
        register=(Button) findViewById(R.id.register);
        mydb=new Database(this);

        intent=new Intent(this,Home.class);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user=username.getText().toString();
                pass=password.getText().toString();

                if(user.length()!=0 && pass.length()!=0){
                    if(mydb.checkuser(user,pass)==true){
                        startActivity(intent);
                    }
                    else{
                        showmessage(3);
                    }

                }
                else{
                    showmessage(1);
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                user=username.getText().toString();
                pass=password.getText().toString();


                int clear=0;

                showusers();
                if(user.length()!=0 && pass.length()!=0){
                    //log them in
                    if(userlist!=null){
                    for(String fitter:userlist){
                        if(user.equals(fitter)){
                           clear=1;
                           showmessage(2);
                        }

                    }}

                    if(clear==0){
                        mydb.adduser(user,pass);

                        startActivity(intent);
                    }






                }
                else{
                    showmessage(1);

                }



            }
        });







        System.out.println(user);


    }

    //for adding friends looiking at who is registered in database
    public void showusers(){
        int i=0;
        Cursor cursor= mydb.getuser();
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
    public void showmessage(int i){
        String message=" ";

        switch(i){
            case 1:
                message="Please Enter a Username and Password";
                break;
            case 2:
                message="Username" +user + "already taken";
                break;
            case 3:
                message="Invalid Username or Password";
                break;
        }


        Toast.makeText(this,message,Toast.LENGTH_LONG).show();

    }
}




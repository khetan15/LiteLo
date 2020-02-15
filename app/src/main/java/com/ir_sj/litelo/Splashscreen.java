package com.ir_sj.litelo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
public class Splashscreen extends AppCompatActivity {

    Handler handle;
    ProgressBar progressBar;
    static FirebaseUser user;
    private DatabaseReference ref;
    private FirebaseAuth mAuth;
    private StorageReference sref;
    private TextView quotes;
    private int i;
    private String j,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        Random rand=new Random();
        //user = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference("Quotes");
        quotes=(TextView) findViewById(R.id.my_quotes);
        i = rand.nextInt(2)+1;
        j=i+"";
        Toast.makeText(this, i+j, Toast.LENGTH_SHORT).show();

        ref.child("two").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {


                    String quote = dataSnapshot.getValue(String.class);
                    Toast.makeText(Splashscreen.this, quote, Toast.LENGTH_SHORT).show();
                    quotes.setText(quote);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        progressBar=findViewById(R.id.pbar);
        progressBar.getProgress();
        SharedPreferences settings=getSharedPreferences("PREFS",0);
        password= settings.getString("password","");
        handle=new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {

                if(password.equals(""))
                {
                    Intent intent=new Intent(getApplicationContext(),CreatePasswordActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent=new Intent(getApplicationContext(),EnterPassword.class);
                    startActivity(intent);
                    finish();
                }

                /*if(FirebaseAuth.getInstance().getCurrentUser() ==  null)
                    startActivity(new Intent(Splashscreen.this, FirstPage.class));
                else
                    startActivity(new Intent(Splashscreen.this, MainActivity.class));
                finish();*/
            }
        },4000);
    }
}
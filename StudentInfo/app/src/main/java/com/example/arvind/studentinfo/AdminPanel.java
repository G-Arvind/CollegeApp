package com.example.arvind.studentinfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class AdminPanel extends AppCompatActivity {

    Button addhod,delhod,edithod,addnews,logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        addhod=(Button)findViewById(R.id.addhod);
        delhod=(Button)findViewById(R.id.delhod);
        edithod=(Button)findViewById(R.id.edithod);
        addnews=(Button)findViewById(R.id.addnews);
        logout=(Button)findViewById(R.id.logout);

        addhod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminPanel.this,AddHod.class);
                startActivity(intent);
            }
        });
        delhod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminPanel.this,RemoveHod.class);
                startActivity(intent);
            }
        });
        edithod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminPanel.this,EditHod.class);
                startActivity(intent);
            }
        });
        addnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Intent intent=new Intent(AdminPanel.this,AddNews.class);
               // startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"click",Toast.LENGTH_LONG).show();
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(AdminPanel.this,LoginPage.class);
                finish();
                startActivity(intent);

            }
        });

    }
}

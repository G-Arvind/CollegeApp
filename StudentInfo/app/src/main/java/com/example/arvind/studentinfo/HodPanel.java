package com.example.arvind.studentinfo;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import at.markushi.ui.CircleButton;

public class HodPanel extends AppCompatActivity {

    CircleButton addstaff, delstaff, editstaff, addhnews, vhnews, exithod;
    DatabaseReference mref;
    Map<String, String> map;
    TextView wel;
    String val, department;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hod_panel);
        addstaff = (CircleButton) findViewById(R.id.addstaff);
        delstaff = (CircleButton) findViewById(R.id.delstaff);
        editstaff = (CircleButton) findViewById(R.id.editstaff);
        addhnews = (CircleButton) findViewById(R.id.addhnews);
        vhnews = (CircleButton) findViewById(R.id.vhnews);
        exithod = (CircleButton) findViewById(R.id.exithod);
        wel = (TextView) findViewById(R.id.wel);
        mref= FirebaseDatabase.getInstance().getReference();

        if (getIntent().getExtras() != null) {
            val = getIntent().getExtras().getString("link");
        }

        Log.v("TAGGGG",val);
        DatabaseReference tmref = mref.child("hod").child(val);
        tmref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                map = (Map<String, String>) dataSnapshot.getValue();
                if (map != null) {
                    String ta = map.get("hname");
                    Log.v("TAG", "val" + ta);
                    wel.setText("Welcome: " + ta);
                    // origid=map.get("hid");
                    // hid.setText(map.get("hid"));
                    department = map.get("hdept");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        addstaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HodPanel.this,AddStaff.class);
                intent.putExtra("dpval",department);
                intent.putExtra("original",val);
                startActivity(intent);
            }
        });
        delstaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HodPanel.this,RemoveStaff.class);
                intent.putExtra("dpval",department);
                startActivity(intent);
            }
        });
        editstaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HodPanel.this,EditStaff.class);
                intent.putExtra("dpval",department);
                intent.putExtra("original",val);
                startActivity(intent);
            }
        });
        addhnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HodPanel.this,Addnews.class);
                finish();
                startActivity(intent);
            }
        });
        vhnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HodPanel.this,ViewNews.class);
                intent.putExtra("dpval",department);
                intent.putExtra("original",val);
                intent.putExtra("type","hod");
                startActivity(intent);
            }
        });
        exithod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder=new AlertDialog.Builder(HodPanel.this);
                builder.setTitle("Exit");
                builder.setMessage("Are you sure ?");
                builder.setCancelable(true);

                builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });

                builder.show();
            }
        });
    }
}

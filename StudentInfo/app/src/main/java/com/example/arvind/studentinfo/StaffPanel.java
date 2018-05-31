package com.example.arvind.studentinfo;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import at.markushi.ui.CircleButton;

public class StaffPanel extends AppCompatActivity {

    CircleButton addstud,delstud,editstud,addstnews,vstnews,exitstaff,atten;
    DatabaseReference mref;
    Map<String, String> map;
    TextView wels;
    String val, department;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_panel);
        addstud=(CircleButton)findViewById(R.id.addstud);
        delstud=(CircleButton)findViewById(R.id.delstud);
        editstud=(CircleButton)findViewById(R.id.editstud);
        addstnews=(CircleButton)findViewById(R.id.addstnews);
        vstnews=(CircleButton)findViewById(R.id.vstnews);
        exitstaff=(CircleButton)findViewById(R.id.exitstaff);
        atten=(CircleButton)findViewById(R.id.atten);
        wels = (TextView) findViewById(R.id.wels);
        mref= FirebaseDatabase.getInstance().getReference();

        if (getIntent().getExtras() != null) {
            val = getIntent().getExtras().getString("link");
            department = getIntent().getExtras().getString("depval");
            Log.v("TAGGGGGG", "val" + val);
        }


        Log.v("TAGGGGGG", "val" + department);
        Log.v("TAGGGGGG", "val" + val);
        DatabaseReference tmref = mref.child("staff").child(department).child(val);

        tmref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                map = (Map<String, String>) dataSnapshot.getValue();
                if (map != null) {
                    String ta = map.get("sname");
                    Log.v("TAG", "val" + ta);
                    wels.setText("Welcome: " + ta);
                    // origid=map.get("hid");
                    // hid.setText(map.get("hid"));
                   // department = map.get("hdept");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        addstud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StaffPanel.this,AddStudent.class);
                intent.putExtra("dpval",department);
                Log.v("TAGGGGGG", "val" + department);
                intent.putExtra("original",val);
                startActivity(intent);
            }
        });
        delstud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StaffPanel.this,RemoveStudent.class);
                intent.putExtra("dpval",department);
                intent.putExtra("original",val);
                startActivity(intent);
            }
        });
        editstud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StaffPanel.this,EditStudent.class);
                intent.putExtra("dpval",department);
                intent.putExtra("original",val);
                startActivity(intent);
            }
        });
        atten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StaffPanel.this,ClassList.class);
                intent.putExtra("dpval",department);
                intent.putExtra("original",val);
                startActivity(intent);
            }
        });
        addstnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StaffPanel.this,Addnews.class);
                finish();
                startActivity(intent);
            }
        });
        vstnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StaffPanel.this,ViewNews.class);
                intent.putExtra("dpval",department);
                intent.putExtra("original",val);
                intent.putExtra("type","staff");
                startActivity(intent);
            }
        });
        exitstaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder=new AlertDialog.Builder(StaffPanel.this);
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

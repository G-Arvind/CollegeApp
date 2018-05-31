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

public class StudentPanel extends AppCompatActivity {

    CircleButton exitstud,vstudnews;
    TextView welstud;
    DatabaseReference mref;
    Map<String, String> map;
    TextView wels;
    String val,dep,classid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_panel);
        exitstud=(CircleButton)findViewById(R.id.exitstud);
        vstudnews=(CircleButton)findViewById(R.id.vstudnews);
        welstud=(TextView)findViewById(R.id.welstud);
        mref= FirebaseDatabase.getInstance().getReference();

        if (getIntent().getExtras() != null) {
            val = getIntent().getExtras().getString("link");
            dep = getIntent().getExtras().getString("depval");
            classid = getIntent().getExtras().getString("classid");

            Log.v("TAGGGGGG", "val" + val);
        }
        DatabaseReference tmref = mref.child("student").child(dep).child(classid).child(val);
        Log.v("TAGGGGGG", "val" + tmref);

        tmref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                map = (Map<String, String>) dataSnapshot.getValue();
                if (map != null) {
                    String ta = map.get("stuname");
                    Log.v("TAG", "val" + ta);
                    welstud.setText("Welcome: " + ta);
                    // origid=map.get("hid");
                    // hid.setText(map.get("hid"));
                    // department = map.get("hdept");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        vstudnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StudentPanel.this,ViewNews.class);
                intent.putExtra("dpval",dep);
                intent.putExtra("original",val);
                intent.putExtra("type","student");
                startActivity(intent);
            }
        });

        exitstud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder=new AlertDialog.Builder(StudentPanel.this);
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

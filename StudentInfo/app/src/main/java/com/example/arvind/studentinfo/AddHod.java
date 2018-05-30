package com.example.arvind.studentinfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class AddHod extends AppCompatActivity {

    DatabaseReference mref;
    Map<String,String>map;
    EditText hname,hid,hpwd,hemail;
    Spinner dept;
    Button addhodsubmit;
    String catval,val,origid;
    int flag=0;
    String[] depts={"SELECT","CSE","IT","EEE","ECE","MECH"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hod);
        hname=(EditText)findViewById(R.id.hname);
        hid=(EditText)findViewById(R.id.hid);
        hpwd=(EditText)findViewById(R.id.hpwd);
        hemail=(EditText)findViewById(R.id.hemail);
        dept=(Spinner)findViewById(R.id.addhoddept);
        addhodsubmit=(Button)findViewById(R.id.addhodsubmit);
        mref= FirebaseDatabase.getInstance().getReference();


        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, depts);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dept.setAdapter(aa);

        dept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                catval=depts[i];
              //  Toast.makeText(getApplicationContext(),"succ"+catval,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if(getIntent().getExtras()!=null) {
            val = getIntent().getExtras().getString("link");
            flag=1;
        }
       // Toast.makeText(this,"Mesg"+val,Toast.LENGTH_LONG).show();

        if(val!=null)
        {
            setdetails();
        }

       //mref= FirebaseDatabase.getInstance().getReference().child("hod");

        addhodsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String depart=catval;
                String name=hname.getText().toString().trim();
                String email=hemail.getText().toString().trim();
                String id=hid.getText().toString().trim();
                String pass=hpwd.getText().toString().trim();

                    DatabaseReference tempmref = mref.child("hod").child(id);
                    tempmref.child("hname").setValue(name);
                    tempmref.child("hid").setValue(id);
                    tempmref.child("hpwd").setValue(pass);
                    tempmref.child("hemail").setValue(email);
                    tempmref.child("hdept").setValue(depart);


                if(flag==1)
                {
                    DatabaseReference tmref=mref.child("hod").child(val);
                    val=hid.getText().toString();
                    tmref.removeValue();
                }
                Intent intent =new Intent(AddHod.this,AdminPanel.class);
                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        });

    }

    void setdetails() {
            DatabaseReference tmref = mref.child("hod").child(val);
            //Toast.makeText(getApplicationContext(),"succ"+val,Toast.LENGTH_LONG).show();
            tmref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    map = (Map<String, String>) dataSnapshot.getValue();
                    if(map!=null) {
                        String ta = map.get("hname");
                        Log.v("TAG", "val" + ta);
                        hname.setText(ta);
                        origid=map.get("hid");
                        hid.setText(map.get("hid"));
                        hemail.setText(map.get("hemail"));
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

    }
}

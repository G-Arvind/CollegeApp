package com.example.arvind.studentinfo;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;

public class TakeAttendance extends AppCompatActivity {

    ArrayList<String> names=new ArrayList<String>(1);

    DatabaseReference mref;
    ListView attendancelist;
    Map<String,String> map;
    String dpval,classid,oid,subid;
    EditText subcode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);
        subcode=(EditText)findViewById(R.id.subcode);

        attendancelist=(ListView)findViewById(R.id.attendancelist);
        mref= FirebaseDatabase.getInstance().getReference();
        if(getIntent().getExtras()!=null) {
            dpval = getIntent().getExtras().getString("depval");
            oid = getIntent().getExtras().getString("original");
            classid = getIntent().getExtras().getString("classid");
            Log.v("TAGGRSI", "val" + dpval);
            Log.v("TAGGRSI", "val" + classid);
        }
        DatabaseReference tmref=mref.child("student").child(dpval).child(classid);
        Log.v("TAG", "VALUEREF" + tmref);

        tmref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // names.add(dataSnapshot.getValue().toString());
                // Log.v("TAG","VALUE"+dataSnapshot.getValue().toString());
                if(!(dataSnapshot.getValue() instanceof String)) {
                    map = (Map<String, String>) dataSnapshot.getValue();
                    names.add(map.get("stuid"));
                    Log.v("TAG", "VALUE" + map.get("stuid"));
                    Log.v("TAG", "VALUE" + names.get(0));
                }
                setList();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    void setList()
    {
        ListAdapter listAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);
        attendancelist.setAdapter(listAdapter);


        attendancelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                attendancelist.getChildAt(i).setBackgroundColor(Color.GREEN);
                String val=String.valueOf(adapterView.getItemAtPosition(i));
                subid=subcode.getText().toString().trim();


                long millis=System.currentTimeMillis();
                java.sql.Date date=new java.sql.Date(millis);
                String datestring=date.toString();


                DatabaseReference tmref=mref.child("attendance").child(classid).child(datestring).child(subid);
                tmref.child(val).setValue("p");
                /*

                Intent intent=new Intent(EditIndStudent.this,AddStudent.class);
                intent.putExtra("link",oid);
                Log.v("TAGGEDIT",oid);
                intent.putExtra("original",oid);
                intent.putExtra("dpval",dpval);
                intent.putExtra("classid",classid);
                intent.putExtra("studid",val);
                //   Log.v("TAGGEDIT",oid);
                //  intent.putExtra("original",oid);
                //  Toast.makeText(getApplicationContext(),"Mesg"+val,Toast.LENGTH_LONG).show();
                startActivity(intent);
                */
            }
        });
        attendancelist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                attendancelist.getChildAt(i).setBackgroundColor(Color.RED);
                String val=String.valueOf(adapterView.getItemAtPosition(i));
                subid=subcode.getText().toString().trim();
                long millis=System.currentTimeMillis();
                java.sql.Date date=new java.sql.Date(millis);
                String datestring=date.toString();
                DatabaseReference tmref=mref.child("attendance").child(classid).child(datestring).child(subid);
                tmref.child(val).setValue("a");
                return true;
            }
        });
    }
}

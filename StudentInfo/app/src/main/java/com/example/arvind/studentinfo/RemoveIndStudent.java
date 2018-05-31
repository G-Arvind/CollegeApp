package com.example.arvind.studentinfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;

public class RemoveIndStudent extends AppCompatActivity {

    ArrayList<String> names=new ArrayList<String>(1);

    DatabaseReference mref;
    ListView studlist;
    Map<String,String> map;
    String dpval,classid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_ind_student);

        studlist=(ListView)findViewById(R.id.indstudremovelist);
        mref= FirebaseDatabase.getInstance().getReference();
        if(getIntent().getExtras()!=null) {
            dpval = getIntent().getExtras().getString("depval");
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
        final ListAdapter listAdapter=new ArrayAdapter<String>(this,R.layout.list_item_1,names);
        studlist.setAdapter(listAdapter);
        studlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String val=String.valueOf(adapterView.getItemAtPosition(i));
                   DatabaseReference tempref=mref.child("student").child(dpval).child(classid).child(val);
                  tempref.removeValue();
                names.remove(i);
                studlist.setAdapter(listAdapter);
                Toast.makeText(getApplicationContext(),"Removed",Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
}

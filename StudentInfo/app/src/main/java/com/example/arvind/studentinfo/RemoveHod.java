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

public class RemoveHod extends AppCompatActivity {

    ArrayList<String> names=new ArrayList<String>(1);

    DatabaseReference mref;
    ListView hodlist;
    Map<String,String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_hod);

        hodlist=(ListView)findViewById(R.id.hodremovelist);
        mref= FirebaseDatabase.getInstance().getReference();
        DatabaseReference tmref=mref.child("hod");
        Log.v("TAG", "VALUEREF" + tmref);

        tmref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // names.add(dataSnapshot.getValue().toString());
                // Log.v("TAG","VALUE"+dataSnapshot.getValue().toString());
                if(!(dataSnapshot.getValue() instanceof String)) {
                    map = (Map<String, String>) dataSnapshot.getValue();
                    names.add(map.get("hid"));
                    Log.v("TAG", "VALUE" + map.get("hid"));
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
        final ListAdapter listAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);
        hodlist.setAdapter(listAdapter);

        hodlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String val=String.valueOf(adapterView.getItemAtPosition(i));
                DatabaseReference tempref=mref.child("hod").child(val);
                tempref.removeValue();
                names.remove(i);
                hodlist.setAdapter(listAdapter);
                Toast.makeText(getApplicationContext(),"Removed",Toast.LENGTH_SHORT).show();
                return true;
            }
        });


    }


}

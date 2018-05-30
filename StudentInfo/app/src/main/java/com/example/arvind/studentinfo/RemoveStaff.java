package com.example.arvind.studentinfo;

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

public class RemoveStaff extends AppCompatActivity {

    ArrayList<String> names=new ArrayList<String>(1);

    DatabaseReference mref;
    ListView slist;
    Map<String,String> map;
    String dpval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_staff);
        slist=(ListView)findViewById(R.id.sremovelist);
        mref= FirebaseDatabase.getInstance().getReference();
        if(getIntent().getExtras()!=null) {
            dpval = getIntent().getExtras().getString("dpval");
        }
        DatabaseReference tmref=mref.child("staff").child(dpval);
        Log.v("TAG", "VALUEREF" + tmref);

        tmref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // names.add(dataSnapshot.getValue().toString());
                // Log.v("TAG","VALUE"+dataSnapshot.getValue().toString());
                if(!(dataSnapshot.getValue() instanceof String)) {
                    map = (Map<String, String>) dataSnapshot.getValue();
                    names.add(map.get("sid"));
                    Log.v("TAG", "VALUE" + map.get("sid"));
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
        slist.setAdapter(listAdapter);

        slist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String val=String.valueOf(adapterView.getItemAtPosition(i));
                DatabaseReference tempref=mref.child("staff").child(dpval).child(val);
                tempref.removeValue();
                names.remove(i);
                slist.setAdapter(listAdapter);
                Toast.makeText(getApplicationContext(),"Removed",Toast.LENGTH_SHORT).show();
                return true;
            }
        });


    }
}

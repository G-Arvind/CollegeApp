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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;

public class EditStaff extends AppCompatActivity {

    ArrayList<String> names=new ArrayList<String>(1);
    Map<String,String> map;
    DatabaseReference mref;
    ListView slist;
    String dpval,oid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_staff);
        if(getIntent().getExtras()!=null) {
            dpval = getIntent().getExtras().getString("dpval");
        }
        if(getIntent().getExtras()!=null) {
            oid = getIntent().getExtras().getString("original");
        }

        slist=(ListView)findViewById(R.id.slist);
        mref= FirebaseDatabase.getInstance().getReference();
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
        ListAdapter listAdapter=new ArrayAdapter<String>(this,R.layout.list_item_1,names);
        slist.setAdapter(listAdapter);

        slist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String val=String.valueOf(adapterView.getItemAtPosition(i));

                Intent intent=new Intent(EditStaff.this,AddStaff.class);
                intent.putExtra("link",val);
                intent.putExtra("dpval",dpval);
                Log.v("TAGGEDIT",oid);
                intent.putExtra("original",oid);
                //  Toast.makeText(getApplicationContext(),"Mesg"+val,Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });
    }

}

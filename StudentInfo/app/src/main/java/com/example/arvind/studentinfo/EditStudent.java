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

public class EditStudent extends AppCompatActivity {

    ArrayList<String> names=new ArrayList<String>(1);
    Map<String,String> map;
    DatabaseReference mref;
    ListView studlist;
    String dpval,oid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);

        studlist=(ListView)findViewById(R.id.studlist);
        mref= FirebaseDatabase.getInstance().getReference();
        if(getIntent().getExtras()!=null) {
            dpval = getIntent().getExtras().getString("dpval");
            oid = getIntent().getExtras().getString("original");
            Log.v("TAGGGG", "VALUEREF" + dpval);
        }
        DatabaseReference tmref=mref.child("student").child(dpval);
        Log.v("TAG", "VALUEREF" + tmref);

        tmref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // names.add(dataSnapshot.getValue().toString());
                // Log.v("TAG","VALUE"+dataSnapshot.getValue().toString());
                if(!(dataSnapshot.getValue() instanceof String)) {
                    map = (Map<String, String>) dataSnapshot.getValue();
                    names.add(map.get("classid"));
                    Log.v("TAGGGG", "VALUEG" + map.get("classid"));
                    Log.v("TAGGGG", "VALUER" + names.get(0));
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
        studlist.setAdapter(listAdapter);

        studlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String val=String.valueOf(adapterView.getItemAtPosition(i));

                Intent intent =new Intent(EditStudent.this,EditIndStudent.class);
                //    Log.v("TAGG", "val" + oid);
                //   intent.putExtra("link",oid);
                intent.putExtra("depval",dpval);
                intent.putExtra("original",oid);
                Log.v("TAGGRS", "val" + dpval);
                intent.putExtra("classid",val);
                Log.v("TAGGRS", "val" + val);
                startActivity(intent);
                finish();
            }
        });



    }

}

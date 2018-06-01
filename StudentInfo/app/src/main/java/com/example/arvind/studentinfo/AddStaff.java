package com.example.arvind.studentinfo;

import android.app.ProgressDialog;
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

public class AddStaff extends AppCompatActivity {

    DatabaseReference mref;
    Map<String,String> map;
    EditText sname,sid,spwd,semail;
    Button addssubmit;
    String catval,val,dpval,oid;
    ProgressDialog progressDialog;
    int flag=0,exec=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);

        sname=(EditText)findViewById(R.id.sname);
        sid=(EditText)findViewById(R.id.sid);
        spwd=(EditText)findViewById(R.id.spwd);
        semail=(EditText)findViewById(R.id.semail);
        addssubmit=(Button)findViewById(R.id.addssubmit);
        mref= FirebaseDatabase.getInstance().getReference();
        progressDialog = new ProgressDialog(this);

        if(getIntent().getExtras()!=null) {
            val = getIntent().getExtras().getString("link");
            if(val!=null)
            flag=1;
            //Toast.makeText(getApplicationContext(),"succ"+val,Toast.LENGTH_LONG).show();
        }
        if(getIntent().getExtras()!=null) {
            dpval = getIntent().getExtras().getString("dpval");
           // Toast.makeText(getApplicationContext(),"succ"+dpval,Toast.LENGTH_LONG).show();
        }
        if(getIntent().getExtras()!=null) {
            oid = getIntent().getExtras().getString("original");
        }

        // Toast.makeText(this,"Mesg"+val,Toast.LENGTH_LONG).show();

        if(val!=null)
        {
           // Toast.makeText(this,"MesgInside",Toast.LENGTH_LONG).show();
            setdetails();
        }

        //mref= FirebaseDatabase.getInstance().getReference().child("hod");

        addssubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.setTitle("please wait");
                progressDialog.setMessage("Adding...");
                progressDialog.show();

                String name=sname.getText().toString().trim();
                String email=semail.getText().toString().trim();
                String id=sid.getText().toString().trim();
                String pass=spwd.getText().toString().trim();

                DatabaseReference tempmref= mref.child("staff").child(dpval).child(id);
                tempmref.child("sname").setValue(name);
                tempmref.child("sid").setValue(id);
                tempmref.child("spwd").setValue(pass);
                tempmref.child("semail").setValue(email);
                tempmref.child("sdept").setValue(dpval);

                if(flag==1)
                {
                    DatabaseReference tmref=mref.child("staff").child(dpval).child(val);
                    val=sid.getText().toString();
                    tmref.removeValue();
                }
                Intent intent =new Intent(AddStaff.this,HodPanel.class);
                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                Log.v("TAGG", "val" + oid);
                intent.putExtra("link",oid);
                startActivity(intent);
                finish();
            }
        });
    }

    void setdetails() {
            DatabaseReference tmref = mref.child("staff").child(dpval).child(val);
            //Toast.makeText(getApplicationContext(),"succ"+val+dpval,Toast.LENGTH_LONG).show();
            tmref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    map = (Map<String, String>) dataSnapshot.getValue();
                    if(map!=null) {
                        map = (Map<String, String>) dataSnapshot.getValue();
                        String ta = map.get("sname");
                        Log.v("TAG", "val" + ta);
                        sname.setText(ta);
                        sid.setText(map.get("sid"));
                        semail.setText(map.get("semail"));
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
    }

}

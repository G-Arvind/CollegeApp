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

public class AddStudent extends AppCompatActivity {

    DatabaseReference mref;
    Map<String,String> map;
    EditText stuname,stuid,stupwd,stuemail,classuid;
    Button addstudentsubmit;
    String catval,val,dpval,oid,classid;
    ProgressDialog progressDialog;
    String tempval,studid;
    int flag=0,exec=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        stuname=(EditText)findViewById(R.id.stuname);
        stuid=(EditText)findViewById(R.id.stuid);
        stupwd=(EditText)findViewById(R.id.stupwd);
        stuemail=(EditText)findViewById(R.id.stuemail);
        classuid=(EditText)findViewById(R.id.classuid);
        addstudentsubmit=(Button)findViewById(R.id.addstudentsubmit);
        mref= FirebaseDatabase.getInstance().getReference();
        progressDialog = new ProgressDialog(this);

        if(getIntent().getExtras()!=null) {
            val = getIntent().getExtras().getString("link");
            if(val!=null) {
                classid = getIntent().getExtras().getString("classid");
                studid = getIntent().getExtras().getString("studid");
                flag = 1;
            }
        }
        if(getIntent().getExtras()!=null) {
            dpval = getIntent().getExtras().getString("dpval");

            // Toast.makeText(getApplicationContext(),"succ"+dpval,Toast.LENGTH_LONG).show();
        }

        if(getIntent().getExtras()!=null) {
            oid = getIntent().getExtras().getString("original");
            Log.v("TAGGEDITADD",oid);
        }
        // Toast.makeText(this,"Mesg"+val,Toast.LENGTH_LONG).show();

        if(val!=null)
        {
            setdetails();

        }

        //mref= FirebaseDatabase.getInstance().getReference().child("hod");

        addstudentsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setTitle("please wait");
                progressDialog.setMessage("Adding...");
                progressDialog.show();

                String name=stuname.getText().toString().trim();
                String email=stuemail.getText().toString().trim();
                String id=stuid.getText().toString().trim();
                String pass=stupwd.getText().toString().trim();
                classid=classuid.getText().toString().trim();
                Log.v("TAGGADD", "val" + classid);
                Log.v("TAGGADD", "val" + dpval);
                Log.v("TAGGADD", "val" + id);

                DatabaseReference tempmref= mref.child("student").child(dpval).child(classid).child(id);
                tempmref.child("stuname").setValue(name);
                tempmref.child("stuid").setValue(id);
                tempmref.child("stupwd").setValue(pass);
                tempmref.child("stuemail").setValue(email);
                tempmref.child("studept").setValue(catval);
                tempmref.child("classid").setValue(classid);

                if(flag==1)
                {
                    DatabaseReference tmref=mref.child("student").child(dpval).child(classid).child(studid);
                    val=stuid.getText().toString();
                    tmref.removeValue();
                }
                Intent intent =new Intent(AddStudent.this,StaffPanel.class);
                progressDialog.dismiss();
                Log.v("TAGG", "val" + oid);
                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                intent.putExtra("link",oid);
                intent.putExtra("depval",dpval);
                startActivity(intent);
                finish();
            }
        });
    }

    void setdetails() {

            DatabaseReference tmref = mref.child("student").child(dpval).child(classid).child(studid);
            Log.v("TAGGGAS", "val" + tmref);
            //Toast.makeText(getApplicationContext(),"succ"+val,Toast.LENGTH_LONG).show();
            tmref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                        map = (Map<String, String>) dataSnapshot.getValue();
                    if(map!=null) {
                        String ta = map.get("stuname");
                        Log.v("TAG", "val" + ta);
                        stuname.setText(ta);
                        stuid.setText(map.get("stuid"));
                        stuemail.setText(map.get("stuemail"));
                        classuid.setText(map.get("classid"));
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
}

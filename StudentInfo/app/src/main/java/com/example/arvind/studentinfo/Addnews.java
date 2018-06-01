package com.example.arvind.studentinfo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Addnews extends AppCompatActivity {

    CheckBox Hod,Staff,Student,ECE,CSE,IT,MECH,EEE;
    EditText Title,Desc;
    Button submit,viewnews;
    String Department = "";
    String role = "";
    String title,descr;
    ProgressDialog progressDialog;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnews);

        // EditText
        Title = (EditText)findViewById(R.id.postname);
        Desc = (EditText)findViewById(R.id.postdesc);
        // Checkbox
        Hod = (CheckBox)findViewById(R.id.checkBoxHod);
        Staff = (CheckBox)findViewById(R.id.checkBoxStaff);
        Student = (CheckBox)findViewById(R.id.checkBoxStudent);
        CSE = (CheckBox)findViewById(R.id.checkBoxCSE);
        EEE = (CheckBox)findViewById(R.id.checkBoxEEE);
        ECE = (CheckBox)findViewById(R.id.checkBoxECE);
        IT = (CheckBox)findViewById(R.id.checkBoxIT);
        MECH = (CheckBox)findViewById(R.id.checkBoxMECH);
        progressDialog = new ProgressDialog(this);


        submit = (Button)findViewById(R.id.button);




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setTitle("please wait");
                progressDialog.setMessage("Adding...");
                progressDialog.show();

                title = Title.getText().toString();

                DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference tempref=mRef.child("news").child(title);
                Log.d("TAG",mRef.toString());

                tempref.child("ntitle").setValue(title);
                descr = Desc.getText().toString();
                tempref.child("ndescription").setValue(descr);

                if(Hod.isChecked())
                    tempref.child("hod").setValue("yes");
                else
                    tempref.child("hod").setValue("no");
                if(Staff.isChecked())
                    tempref.child("staff").setValue("yes");
                else
                    tempref.child("staff").setValue("no");
                if(Student.isChecked())
                    tempref.child("student").setValue("yes");
                else
                    tempref.child("student").setValue("no");
                if(ECE.isChecked())
                    tempref.child("ECE").setValue("yes");
                else
                    tempref.child("ECE").setValue("no");
                if(EEE.isChecked())
                    tempref.child("EEE").setValue("yes");
                else
                    tempref.child("EEE").setValue("no");
                if(IT.isChecked())
                    tempref.child("IT").setValue("yes");
                else
                    tempref.child("IT").setValue("no");
                if(MECH.isChecked())
                    tempref.child("MECH").setValue("yes");
                else
                    tempref.child("MECH").setValue("no");
                if(CSE.isChecked())
                    tempref.child("CSE").setValue("yes");
                else
                    tempref.child("CSE").setValue("no");


                long millis = System.currentTimeMillis();
                java.sql.Date date = new java.sql.Date(millis);
                tempref.child("ndate").setValue(date.toString());


                Log.d("TAG","Role :"+ role + "\n" + "Department : " + Department + "\n");

                Toast.makeText(getApplicationContext(),"Published",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                Intent intent =new Intent(Addnews.this,AdminPanel.class);
                startActivity(intent);

            }
        });

    }
}

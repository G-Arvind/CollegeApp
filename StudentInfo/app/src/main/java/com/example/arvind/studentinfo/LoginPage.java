package com.example.arvind.studentinfo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class LoginPage extends AppCompatActivity {

    DatabaseReference mref;
    DatabaseReference tmref;
    FirebaseAuth auth;
    Map<String,String>map;
    ProgressDialog progressDialog;
    EditText email,pass;
    TextView signup;
    Button login;
    String eval,passval;
    String[] category={"student","staff","hod","admin"};
    String[] depts={"SELECT","CSE","IT","EEE","ECE","MECH"};
    Spinner spinner,storstdep;
    String catval,ta,ia,depval;
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        progressDialog=new ProgressDialog(this);

        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
        login = (Button) findViewById(R.id.login);
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        spinner=(Spinner)findViewById(R.id.spinner);
        storstdep=(Spinner)findViewById(R.id.storstdep);
        ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinner_item, category);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);
        mref= FirebaseDatabase.getInstance().getReference();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 catval=category[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ArrayAdapter aa1 = new ArrayAdapter(this, R.layout.spinner_item, depts);
        aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        storstdep.setAdapter(aa1);

        storstdep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                depval=depts[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setTitle("please wait");
                progressDialog.setMessage("LoggingIn");
                progressDialog.show();
                loginfun();
            }
        });
    }

        @Override
        protected void onStart () {
            super.onStart();
            if (auth.getCurrentUser() != null) {
                finish();
                Intent intent = new Intent(LoginPage.this, AdminPanel.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }


    }

    void loginfun()
    {
        Log.v("TAGGGSTUD","VALUE"+catval);
        progressDialog.setMessage("Please Wait");


        if(catval=="admin") {
            eval = email.getText().toString().trim();
            passval = pass.getText().toString().trim();
            if (!Patterns.EMAIL_ADDRESS.matcher(eval).matches()) {
                progressDialog.dismiss();
                email.setError("invalid email id");
                return;
            }
            if (passval.length() < 6) {
                progressDialog.dismiss();
                pass.setError("Password should contain atleast 6 character");
                return;
            }
            auth.signInWithEmailAndPassword(eval, passval).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        finish();
                        Intent intent = new Intent(LoginPage.this, AdminPanel.class);
                        progressDialog.dismiss();
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            });
        }
        else if (catval=="hod") {
            eval = email.getText().toString().trim();
            passval = pass.getText().toString().trim();

            if (mref.child("hod").child(eval)!= null) {

                tmref = mref.child("hod").child(eval);

                tmref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        map = (Map<String, String>) dataSnapshot.getValue();
                        if(map!=null) {
                            ta = map.get("hpwd");
                        }
                        if(map!=null) {
                            ia = map.get("hid");
                        }
                        else {
                            ta="xx";
                            ia="xx";
                        }
                        // Toast.makeText(getApplicationContext(),passval+eval,Toast.LENGTH_LONG).show();
                        // hemail.setText(map.get("hemail"));
                        if (ta.equals(passval) && ia.equals(eval)) {
                            progressDialog.dismiss();
                            finish();
                            Intent intent = new Intent(LoginPage.this, HodPanel.class);
                            intent.putExtra("link",eval);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_SHORT).show();
                        }
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                //Toast.makeText(getApplicationContext(),"succ"+val,Toast.LENGTH_LONG).show();
            }

        }
        else if (catval=="staff") {
            eval = email.getText().toString().trim();
            passval = pass.getText().toString().trim();

            if (mref.child("staff").child(eval) != null) {

                tmref = mref.child("staff").child(depval).child(eval);

                tmref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        map = (Map<String, String>) dataSnapshot.getValue();
                        if (map != null) {
                            ta = map.get("spwd");
                        }
                        if (map != null) {
                            ia = map.get("sid");
                        } else {
                            ta = "xx";
                            ia = "xx";
                        }
                        // Toast.makeText(getApplicationContext(),passval+eval,Toast.LENGTH_LONG).show();
                        // hemail.setText(map.get("hemail"));
                        if (ta.equals(passval) && ia.equals(eval)) {
                            progressDialog.dismiss();
                            finish();
                            Intent intent = new Intent(LoginPage.this, StaffPanel.class);
                            intent.putExtra("link",eval);
                            intent.putExtra("depval",depval);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                //Toast.makeText(getApplicationContext(),"succ"+val,Toast.LENGTH_LONG).show();
            }
        }
        else if (catval=="student") {
            eval = email.getText().toString().trim();
            passval = pass.getText().toString().trim();


                tmref = mref.child("student").child(depval);

            tmref.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                @Override
                public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                    for(com.google.firebase.database.DataSnapshot d:dataSnapshot.getChildren()) {
                        if (d.child(eval).getValue() != null) {
                            Log.v("TAG", "ACTUAL:" +d.child("classid").getValue().toString() );
                            String lin=d.child("classid").getValue().toString();
                            String pass=d.child(eval).child("stupwd").getValue().toString();
                            String idval=d.child(eval).child("stuid").getValue().toString();
                            Log.v("TAG", "INSIDEFORRRR:" +d.child(eval).child("stupwd").getValue() );
                            Log.v("TAG", "INSIDEFORRRR:" +d.child(eval).child("stuid").getValue() );
                            if(passval.equals(pass)) {
                                Log.v("TAG", "LINK:" +tmref+"/"+lin+"/"+d.child(eval).child("stuid").getValue().toString());
                              String orglink=  tmref+"/"+lin+"/"+d.child(eval).child("stuid").getValue().toString();
                                Intent intent = new Intent(LoginPage.this, StudentPanel.class);
                                progressDialog.dismiss();
                                intent.putExtra("depval",depval);
                                intent.putExtra("classid",lin);
                                intent.putExtra("link",eval);
                                startActivity(intent);
                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Invalid Login",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }



                @Override
                public void onCancelled(DatabaseError databaseError) {

                }


            });

            }
        }
}


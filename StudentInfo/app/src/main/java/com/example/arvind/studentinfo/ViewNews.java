package com.example.arvind.studentinfo;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Map;

import static com.example.arvind.studentinfo.R.styleable.RecyclerView;

public class ViewNews extends AppCompatActivity {

    RecyclerView recyclerView;
    String depval,val,type,title;
    ArrayList<String> nmval=new ArrayList<String>(1);
    int count=0;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_news);
        recyclerView = (RecyclerView) findViewById(R.id.rec);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.v("TAG", "TEST");

        if (getIntent().getExtras() != null) {
            val = getIntent().getExtras().getString("link");
            depval = getIntent().getExtras().getString("dpval");
            type = getIntent().getExtras().getString("type");
        }

        mRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference tempRef = mRef.child("news");
        Log.v("TAG", tempRef.toString());


        tempRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();
                String temp = map.get(type);
                Log.v("TAGUERY", "MSGISinside:" + temp);
                if(temp.equals("yes")){
                    Log.v("TAGUERY", "COUNSET:" + temp);
                    count=1;
                    setr();
                }
                else {
                    nmval.add(map.get("ntitle"));
                }
                // String temp1 = map.get("ndate");
                // Log.v("TAG", "MSGISinside:" + temp1);
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
        Log.v("TAGUERY", "MSGISinside:" + count);




    }

    public void setr(){
        Query qtempRef = mRef.child("news").orderByChild(depval).equalTo("yes");
        // Log.v("QUERYTAG", "MSGISinside:" + qtempRef.toString());


        FirebaseRecyclerAdapter<Blog, BlogViewHolder> adapter = new FirebaseRecyclerAdapter<Blog, BlogViewHolder>(
                Blog.class,
                R.layout.individual_row,
                BlogViewHolder.class,
                qtempRef) {
            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, Blog model, int position) {

                if(nmval.contains(model.getNtitle()))
                    viewHolder.setVisibility(false);


                viewHolder.setTitle(model.getNtitle());
                Log.v("TAG", model.getNtitle());
                viewHolder.setDescription(model.getNdescription());
                viewHolder.setDate(model.getNdate());
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }

        };
        recyclerView.setAdapter(adapter);
    }


    public static class BlogViewHolder extends RecyclerView.ViewHolder {

        TextView title, description, date;

        public void setVisibility(boolean isVisible){
            RecyclerView.LayoutParams param = (RecyclerView.LayoutParams)itemView.getLayoutParams();
            if (isVisible){
                param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                param.width = LinearLayout.LayoutParams.MATCH_PARENT;
                itemView.setVisibility(View.VISIBLE);
            }else{
                itemView.setVisibility(View.GONE);
                param.height = 0;
                param.width = 0;
            }
            itemView.setLayoutParams(param);
        }

        public BlogViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.name11);
            description = (TextView) itemView.findViewById(R.id.desc);
            date = (TextView) itemView.findViewById(R.id.date11);
        }


        public void setTitle(String Title) {
            if (Title != null)
                title.setText(Title + "");
        }

        public void setDescription(String desc) {
            if (desc != null) {
                description.setText(desc + "");
            }

        }

        public void setDate(String date1) {
            if (date1 != null) {
                date.setText(date1 + "");
            }
        }
    }

}

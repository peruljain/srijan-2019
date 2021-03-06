package iit.ism.dhanbad.srijan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import iit.ism.dhanbad.srijan.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class informalslatest extends MainActivity {
    private List<cards> cardsList;
    adaptermain adapter;
    TextView slide1HeadingTextView;
    FirebaseDatabase highlights;
    ProgressDialog progressDialog;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.replaceContentLayout(R.layout.new_slide2, R.id.main);
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ImageView imageView = (ImageView) findViewById(R.id.nav);
        slide1HeadingTextView = (TextView) findViewById(R.id.slide2_heading_textView);
        slide1HeadingTextView.setText("INFORMALS");

        highlights = FirebaseDatabase.getInstance();
        final DatabaseReference obs = highlights.getReference("informals");
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        // recycler view

        cardsList = new ArrayList<>();
        if (InternetConnection.checkConnection(getApplicationContext())) {

            final ProgressDialog dialog;
            /**
             * Progress Dialog for User Interaction
             */
            dialog = new ProgressDialog(informalslatest.this);
            dialog.setTitle("Srijan");
            dialog.setMessage("Loading");
            dialog.show();
            final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler2);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);
            //addimg items to list
            //yaha pr <<done>>,drawable me image club name se save krna;
            //  cardsList.add(new cards(R.color.card4,"Roadies","Ready For Roadies",R.drawable.paintball,"stars"));
            adapter = new adaptermain(informalslatest.this, cardsList);
            recyclerView.setAdapter(adapter);
            obs.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    dataSnapshot = dataSnapshot.child("stars");
                    if(dataSnapshot.hasChildren()){
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            Log.e("hello", "onDataChange: " + snapshot.child("name").getValue().toString() );

                            cardsList.add(new cards(R.color.card4, snapshot.child("name").getValue().toString(), snapshot.child("description").getValue().toString(), R.drawable.paintball,"stars"));

                        }
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



        }
        }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(informalslatest.this, MainActivity.class));
    }
}

package iit.ism.dhanbad.srijan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import iit.ism.dhanbad.srijan.R;

import java.util.ArrayList;
import java.util.List;

public class Literary extends MainActivity {


    private List<cards> cardsList;
    danceadapter adapter;
    TextView slide2HeadingTextView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.replaceContentLayout(R.layout.new_slide2, R.id.main);
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ImageView imageView = (ImageView) findViewById(R.id.nav);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });



        // recycler view
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        slide2HeadingTextView = (TextView) findViewById(R.id.slide2_heading_textView);
        slide2HeadingTextView.setText("LITERARY");


        cardsList = new ArrayList<>();




        //addimg items to list
        //R.color.card1,R.color.card2,R.color.card3;
        cardsList.add(new cards(R.color.card1, "Standup", R.drawable.standup));//yha pr salsa ki jagah event name or image bhi change krna
//        cardsList.add(new cards(R.color.card2, "Trichonia",  R.drawable.trichonia));
        cardsList.add(new cards(R.color.card2, "Head on",  R.drawable.heattoheat));
        cardsList.add(new cards(R.color.card3, "Fanfic",  R.drawable.doodle));
        cardsList.add(new cards(R.color.card1, "Operation Campaign",  R.drawable.workshoplci));
        cardsList.add(new cards(R.color.card2, "Poetry Slam",  R.drawable.kavyanjali));


        adapter = new danceadapter(Literary.this, cardsList);

        recyclerView.setAdapter(adapter);

    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_out_out,R.anim.slide_in_in);
    }
}

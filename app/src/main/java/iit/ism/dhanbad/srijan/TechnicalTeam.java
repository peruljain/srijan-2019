package iit.ism.dhanbad.srijan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import iit.ism.dhanbad.srijan.R;

import java.util.ArrayList;

public class TechnicalTeam extends AppCompatActivity {


    private ArrayList<ContactModel> contactList = new ArrayList<>();
    private ListView contactListView;
    private ContactUsAdapter contactAdapter;
    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technical_team);
        contactListView = findViewById(R.id.technicalteam_listview);
        back = (ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TechnicalTeam.super.onBackPressed();
            }
        });

        contactList.add(new ContactModel(R.drawable.manan_jhaveri,"Manan Jhaveri","Technical team member","+91 9702492111"));
        contactList.add(new ContactModel(R.drawable.pranjal,"Pranjal Gupta","Technical team member","+91 9709372706"));
        contactList.add(new ContactModel(R.drawable.mistry,"Mehul Mistry","Technical team member","+91 8698706444"));
        contactList.add(new ContactModel(R.drawable.raushan_kr,"Raushan Singh","Technical team member","+91 8210096847"));

        contactAdapter = new ContactUsAdapter(TechnicalTeam.this,contactList);
        contactListView.setAdapter(contactAdapter);
    }
}
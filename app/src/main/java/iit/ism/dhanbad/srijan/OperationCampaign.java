package iit.ism.dhanbad.srijan;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import iit.ism.dhanbad.srijan.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

import static android.widget.Toast.LENGTH_LONG;

public class OperationCampaign extends MainActivity {

    ViewPager viewPager;
    Button button,regis;//new line
    List<String> imagesList;
    AlertDialog.Builder placess;
    ProgressDialog progressDialog;
    ProgressDialog Dialog;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    SharedPreferenceConfig sharedPreferenceConfig;
    boolean   connected = false;//new line
    ConnectivityManager connectivityManager;//new line

    //todo: photos url from firebase
    String[] photos = {"https://firebasestorage.googleapis.com/v0/b/srijan-6df05.appspot.com/o/Literary%2FOperationCampaign%2Ftrichon1.jpg?alt=media&token=d106dee1-cc3c-4f02-a185-1cc615d4a69c","https://firebasestorage.googleapis.com/v0/b/srijan-6df05.appspot.com/o/Literary%2FOperationCampaign%2Ftrichon2.jpg?alt=media&token=98c8a9b4-968c-46ef-9ab7-e62f41c1a68e","https://firebasestorage.googleapis.com/v0/b/srijan-6df05.appspot.com/o/Literary%2FOperationCampaign%2Ftrichon3.jpg?alt=media&token=830cbc55-8d72-4aaa-b4ed-7c03c08e166a"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.replaceContentLayout(R.layout.new_slide3, R.id.main);


        imagesList = new ArrayList<>();
        imagesList.add(photos[0]);
        imagesList.add(photos[1]);
        imagesList.add(photos[2]);
        init();
        nav();
        final TextView headingTextView = (TextView)findViewById(R.id.slide3_heading_textView);
        //todo: change heading text
        headingTextView.setText("Operation Campaign");
        notification();
        place();
        details();
        register();//new line
        sharedPreferenceConfig = new SharedPreferenceConfig(getApplicationContext());
        final ImageView imageView = (ImageView)findViewById(R.id.notification);
        if(sharedPreferenceConfig.getstatus29()){
            //todo:set context
            imageView.setImageResource(R.drawable.bell);

        }else{
            imageView.setImageResource(R.drawable.belloff);
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo : copy
                if(InternetConnection.checkConnection(getApplicationContext())){
                    connected = true;
                }else{
                    connected = false;
                }

                if(connected){
                    if(!sharedPreferenceConfig.getstatus29()){
                        //todo:set context
                        Toast.makeText(OperationCampaign.this,"Unsubscribed from event's notifications", LENGTH_LONG).show();
                        imageView.setImageResource(R.drawable.bell);
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("OperationCampaign");//event name
                        sharedPreferenceConfig.writeImagestatus29(true);
                    }else{

                        FirebaseMessaging.getInstance().subscribeToTopic("OperationCampaign");
                        //todo:set context
                        Toast.makeText(OperationCampaign.this,"Successfully subscribed for notifications", LENGTH_LONG).show();
                        sharedPreferenceConfig.writeImagestatus29(false);
                        imageView.setImageResource(R.drawable.belloff);
                    }
                }
                else{
                    Toast.makeText(OperationCampaign.this,"Please Check Your Internet Connection", Toast.LENGTH_LONG).show();
                }


            }
        });








    }private void nav(){
        ImageView nav = (ImageView)findViewById(R.id.nav);
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    //todo : copy
    private void register() {
        Button register = (Button)findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://srijaniitism.org/register");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://srijaniitism.org/register")));
                }
            }
        });
    }

    private void details() {
        button = (Button)findViewById(R.id.detailsss);
        final DatabaseReference ref= FirebaseDatabase.getInstance().getReference("details");
        final String[] details = new String[1];
        //todo:set context
        final Intent intent = new Intent(OperationCampaign.this,Details.class);
        //todo:set context
        Dialog = new ProgressDialog(OperationCampaign.this);
        Dialog.setMessage("Downloading....");
        Dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        Dialog.setIndeterminate(true);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo : copy
                if(InternetConnection.checkConnection(getApplicationContext())){
                    connected = true;
                }else{
                    connected = false;
                }

                if(connected){
                    Dialog.show();
                    //event name
                    //todo:set firebase details
                    ref.child("OperationCampaign").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                details[0]  = dataSnapshot.getValue().toString();
                                intent.putExtra("details",details[0]);
                                Dialog.dismiss();
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else{
                    Toast.makeText(OperationCampaign.this,"Please Check Your Internet Connection", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void place() {
        ImageView place = (ImageView)findViewById(R.id.place);
        final DatabaseReference ref= FirebaseDatabase.getInstance().getReference("places");
        final String[] places = new String[1];
        //todo:set context
        placess = new AlertDialog.Builder(OperationCampaign.this);//class ka name
        placess.setTitle("Venue");
        placess.create();
        //todo:set context
        progressDialog = new ProgressDialog(OperationCampaign.this);
        progressDialog.setMessage("Wait");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        placess.setIcon(R.drawable.location);


        placess.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        place.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //todo : copy
                if(InternetConnection.checkConnection(getApplicationContext())){
                    connected = true;
                }else{
                    connected = false;
                }


                if(connected){
                    progressDialog.show();
                    //child me event ka name;
                    //todo:set venue firebase
                    ref.child("OperationCampaign").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                places[0] = dataSnapshot.getValue().toString();
                                placess.setMessage(places[0]);
                                progressDialog.dismiss();
                                placess.show();



                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                else{
                    Toast.makeText(OperationCampaign.this,"Please Check Your Internet Connection", Toast.LENGTH_LONG).show();
                }
            }
        });



    }

    //image slider code
    private void init() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            viewPager = (ViewPager)findViewById(R.id.viewPager);
            //todo:set context
            viewPager.setAdapter(new adapterimage(getApplicationContext(),imagesList));
            CircleIndicator circleIndicator = (CircleIndicator)findViewById(R.id.indicator);
            circleIndicator.setViewPager(viewPager);

            connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);//new line

            //final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
            //  circleIndicator.set(5 * density);

            NUM_PAGES =imagesList.size();

            // Auto start of viewpager
            final Handler handler = new Handler();
            final Runnable Update = new Runnable() {
                public void run() {
                    if (currentPage == NUM_PAGES) {
                        currentPage = 0;
                    }
                    viewPager.setCurrentItem(currentPage++, true);
                }
            };
            Timer swipeTimer = new Timer();
            swipeTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, 3000, 3000);

            // Pager listener over indicator
            circleIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageSelected(int position) {
                    currentPage = position;

                }

                @Override
                public void onPageScrolled(int pos, float arg1, int arg2) {

                }

                @Override
                public void onPageScrollStateChanged(int pos) {

                }
            });
            // Call some material design APIs here
        } else {
            viewPager = (ViewPager)findViewById(R.id.viewPager);
            //todo:set context
            viewPager.setAdapter(new adapterimage(getApplicationContext(),imagesList));
            // Implement this feature without material design
        }


    }
    //notification
    private void notification(){

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_out_out,R.anim.slide_in_in);
    }
}

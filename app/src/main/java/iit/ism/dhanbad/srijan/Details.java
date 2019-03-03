package iit.ism.dhanbad.srijan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import iit.ism.dhanbad.srijan.R;

public class Details extends AppCompatActivity {
ImageView back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        back = (ImageView)findViewById(R.id.back1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Details.super.onBackPressed();
            }
        });
        TextView textView = (TextView)findViewById(R.id.detailss );
        String details = getIntent().getStringExtra("details");
        textView.setText(details);
    }
}

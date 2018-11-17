package com.example.amanat.rcuproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.jsibbold.zoomage.ZoomageView;
import com.squareup.picasso.Picasso;

public class ImageFullScreen extends AppCompatActivity {
    private Bundle bundle;
    private String image;
    private ZoomageView zoomageView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_image_full_screen);

        bundle = getIntent().getExtras();
        image = bundle.getString("image");

        zoomageView = findViewById(R.id.imageZoom);
        button = findViewById(R.id.btClose);

        Picasso.get().load(image).fit().into(zoomageView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ImageFullScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}

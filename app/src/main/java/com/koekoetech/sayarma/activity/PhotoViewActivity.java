package com.koekoetech.sayarma.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.koekoetech.sayarma.R;

public class PhotoViewActivity extends AppCompatActivity {

    public static String IMAGE_EXTRA = "IMAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);

        PhotoView photoView = findViewById(R.id.photo);

        String image_url = getIntent().getStringExtra(IMAGE_EXTRA);

        Glide.with(PhotoViewActivity.this)
                .load(image_url)
                .placeholder(R.drawable.article_placeholder)
                .into(photoView);
    }
}

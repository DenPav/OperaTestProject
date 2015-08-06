package com.example.hp.operatestproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Denis Pavlovsky on 26.07.15.
 */
public class ProductActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_activity);
        Intent intent = getIntent();
        ImageView icon = (ImageView) findViewById(R.id.icon_image_view);
        TextView title = (TextView) findViewById(R.id.title_text_view);
        TextView shortDescription = (TextView) findViewById(R.id.short_description_text_view);
        TextView description = (TextView) findViewById(R.id.description_text_view);

        Picasso.with(this)
                .load(intent.getStringExtra(Constants.ICON))
                .into(icon);

        title.setText(intent.getStringExtra(Constants.TITLE));
        shortDescription.setText(intent.getStringExtra(Constants.SHORT_DESCRIPTION));
        description.setText(intent.getStringExtra(Constants.FULL_DESCRIPTION));

        final ViewFlipper flipper = (ViewFlipper) findViewById(R.id.screenshots_flipper);
        ArrayList<String> screenshots = intent.getStringArrayListExtra(Constants.SCREENSHOTS);
        for(int i=0; i < screenshots.size(); i++)
        {
            ImageView image = new ImageView(getApplicationContext());
            Picasso.with(this)
                    .load(screenshots.get(i))
                    .into(image);
            flipper.addView(image);
        }

        Button flipperNextButton = (Button) findViewById(R.id.flipper_next);
        Button flipperBackButton = (Button) findViewById(R.id.flipper_previous);

        flipperNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipper.showNext();
            }
        });

        flipperBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipper.showPrevious();
            }
        });
    }
}

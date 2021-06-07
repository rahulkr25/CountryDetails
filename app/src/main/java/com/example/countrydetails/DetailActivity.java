package com.example.countrydetails;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;

public class DetailActivity extends AppCompatActivity {
     String WikiUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detial_activity);
        TextView nameView=(TextView)findViewById(R.id.name);
        TextView region=(TextView)findViewById(R.id.region);
        TextView subregion=(TextView)findViewById(R.id.subregion);
        TextView capital=(TextView)findViewById(R.id.capital);
        TextView population=(TextView)findViewById(R.id.population);
        TextView languages=(TextView)findViewById(R.id.languages);
        TextView borders=(TextView)findViewById(R.id.borders);


        ImageView imageView=(ImageView)findViewById(R.id.imageview);
        Intent intent=getIntent();



            Bundle bundle=intent.getExtras();
            nameView.setText(bundle.getString(String.valueOf(R.string.COUNTRY_NAME)));
            region.setText(bundle.getString(String.valueOf(R.string.COUNTRY_REGION)));
            subregion.setText(bundle.getString(String.valueOf(R.string.COUNTRY_SUBREGION)));
            population.setText(bundle.getString(String.valueOf(R.string.COUNTRY_POPULATION)));
            capital.setText(bundle.getString(String.valueOf(R.string.COUNTRY_CAPITAL)));
            languages.setText(bundle.getString(String.valueOf(R.string.COUNTRY_LANGUAGES)));
            borders.setText(bundle.getString(String.valueOf(R.string.COUNTRY_BORDERS)));
            //Picasso.get().load(bundle.getString(String.valueOf(R.string.COUNTRY_FLAG))).into(imageView);
        GlideToVectorYou.init().with(this).load(Uri.parse(bundle.getString
                (String.valueOf(R.string.COUNTRY_FLAG))),imageView);



    }


}

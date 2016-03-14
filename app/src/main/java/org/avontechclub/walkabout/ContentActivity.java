package org.avontechclub.walkabout;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ContentActivity extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Intent intent = getIntent();
        String locationID = intent.getStringExtra(HomeActivity.LOC_ID);

        int titleID = getResources().getIdentifier("title_" + locationID, "string", "org.avontechclub.walkabout");
        int imageID = getResources().getIdentifier("image_" + locationID, "drawable", "org.avontechclub.walkabout");

        String title = getString(titleID);
        getSupportActionBar().setTitle(title);

        ImageView imageView = (ImageView)findViewById(R.id.actionbar_image);
        imageView.setImageResource(imageID);

        TextView textView = (TextView) findViewById(R.id.content);
        AssetManager assetManager = getResources().getAssets();
        InputStream inputStream;
        String locationContent = "";
        try {
            inputStream = assetManager.open(locationID + ".txt");
            locationContent = convert_to_String(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        textView.setText(locationContent);
        Bitmap image = BitmapFactory.decodeResource(getResources(), imageID);
        Palette p = Palette.from(image).generate();
        int color = 1;
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setContentScrimColor(p.getMutedColor(color));
        Window window = getWindow();
        window.setStatusBarColor(p.getDarkMutedColor(color));
    }
    public String convert_to_String(InputStream inputStream)
    {

        BufferedReader bufferedReader=null;
        StringBuilder stringBuilder=new StringBuilder();
        String line;
        try{
            bufferedReader= new BufferedReader(new InputStreamReader(inputStream));
            while ((line=bufferedReader.readLine())!=null)
            {    stringBuilder.append("\n");
                stringBuilder.append(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return  stringBuilder.toString();

    }



}
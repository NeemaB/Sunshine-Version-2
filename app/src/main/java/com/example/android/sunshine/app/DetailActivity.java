package com.example.android.sunshine.app;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import Fragments.ForecastDetailsFragment;

public class DetailActivity extends ActionBarActivity {

    private String forecastDetails;
    private Bundle arguments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();

        ActionBar ab = getSupportActionBar();
        ab.setIcon(R.mipmap.sun_launcher_icon);
        ab.setDisplayShowHomeEnabled(true);

        if (intent.hasExtra(Intent.EXTRA_TEXT)){
            forecastDetails = intent.getStringExtra(Intent.EXTRA_TEXT);
            arguments = new Bundle();
            arguments.putString("forecastDetails", forecastDetails);
        }
        ForecastDetailsFragment fragment = new ForecastDetailsFragment();
        fragment.setArguments(arguments);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        }


        return super.onOptionsItemSelected(item);
    }


}

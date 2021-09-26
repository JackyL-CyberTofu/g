package com.cmpt276.scoreapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;

import com.cmpt276.scoreapp.databinding.ActivityAddDaThingBinding;

public class AddGame extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityAddDaThingBinding binding;


    private static final String EXTRA_MESSAGE = "Extra - message";
    public static Intent makeLaunchIntent(Context c, String message){
        Intent intent = new Intent(c, AddGame.class);
        intent.putExtra(EXTRA_MESSAGE,message);
        return intent;
    }


    @NonNull
    private static String getName() {
        return "Extra - message";
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setTitle("Add Game");
        super.onCreate(savedInstanceState);

        binding = ActivityAddDaThingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // UP/BACK Button
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_add_da_thing);
        //appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        //Handle the extra
        Intent i = getIntent();
        String message = i.getStringExtra(EXTRA_MESSAGE);
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();


    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if (item.getItemId() == R.id.saveButton){
            Toast.makeText(this,"Save", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


    //@Override
    //public boolean onSupportNavigateUp() {
        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_add_da_thing);
        //return NavigationUI.navigateUp(navController, appBarConfiguration)
                //|| super.onSupportNavigateUp();
    //}
}
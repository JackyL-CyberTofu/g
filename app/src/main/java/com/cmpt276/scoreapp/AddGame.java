package com.cmpt276.scoreapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
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
        //Intent i = getIntent();
        //String message = i.getStringExtra(EXTRA_MESSAGE);
        //Toast.makeText(this,message,Toast.LENGTH_LONG).show();

        TextView scoresOne = (TextView)findViewById(R.id.scoresOne);
        TextView scoresTwo = (TextView)findViewById(R.id.scoresTwo);

        EditText player1Cards = (EditText)findViewById(R.id.player1Cards);
        EditText player2Cards = (EditText)findViewById(R.id.player2Cards);
        EditText player1Points = (EditText)findViewById(R.id.player1Points);
        EditText player2Points = (EditText)findViewById(R.id.player2Points);
        EditText player1Wager = (EditText)findViewById(R.id.player1Wager);
        EditText player2Wager = (EditText)findViewById(R.id.player2Wager);

        // Read Text
        player1Cards.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //scoresOne.setText(player1Cards.getText().toString());
                //int a = calculateScore( Integer.parseInt(player1Cards.getText().toString()) ,Integer.parseInt(player1Wager.getText().toString()), Integer.parseInt(player1Points.getText().toString()) );
                //int a = 32;
                //String b = a.toString();
                if (charSequence.length() != 0 && player1Points.length() != 0 && player1Wager.length() !=0 )
                    scoresOne.setText(Integer.toString(calculateScore(Integer.parseInt(player1Cards.getText().toString()), Integer.parseInt(player1Wager.getText().toString()), Integer.parseInt(player1Points.getText().toString()))));
                else {
                    scoresOne.setText("-");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });

        player2Cards.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0 && player2Points.length() != 0 && player2Wager.length() !=0 ){
                    //scoresTwo.setText(player2Cards.getText().toString());
                    scoresTwo.setText(Integer.toString(calculateScore(Integer.parseInt(player2Cards.getText().toString()), Integer.parseInt(player2Wager.getText().toString()), Integer.parseInt(player2Points.getText().toString()))));

                }
                else {
                    scoresTwo.setText("-");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });

        player1Points.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0 && player1Cards.length() != 0 && player1Wager.length() !=0 ){
                    //scoresOne.setText(player1Cards.getText().toString());
                    scoresOne.setText(Integer.toString(calculateScore(Integer.parseInt(player1Cards.getText().toString()), Integer.parseInt(player1Wager.getText().toString()), Integer.parseInt(player1Points.getText().toString()))));
                }
                else {
                    scoresOne.setText("-");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });

        player2Points.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0 && player2Cards.length() != 0 && player2Wager.length() !=0 ){
                    //scoresTwo.setText(player2Cards.getText().toString());
                    scoresTwo.setText(Integer.toString(calculateScore(Integer.parseInt(player2Cards.getText().toString()), Integer.parseInt(player2Wager.getText().toString()), Integer.parseInt(player2Points.getText().toString()))));

                }
                else {
                    scoresTwo.setText("-");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });

        player1Wager.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0 && player1Points.length() != 0 && player1Cards.length() !=0 ){
                    //scoresOne.setText(player1Cards.getText().toString());
                    scoresOne.setText(Integer.toString(calculateScore(Integer.parseInt(player1Cards.getText().toString()), Integer.parseInt(player1Wager.getText().toString()), Integer.parseInt(player1Points.getText().toString()))));

                }
                else {
                    scoresOne.setText("-");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });

        player2Wager.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0 && player2Points.length() != 0 && player2Cards.length() !=0 ){
                    //scoresTwo.setText(player2Cards.getText().toString());
                    scoresTwo.setText(Integer.toString(calculateScore(Integer.parseInt(player2Cards.getText().toString()), Integer.parseInt(player2Wager.getText().toString()), Integer.parseInt(player2Points.getText().toString()))));

                }
                else {
                    scoresTwo.setText("-");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });

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

    public int calculateScore(int cards, int wager, int points){
        int temp = (points-20)*(wager+1);
        if (cards >=8){
            temp = temp+20;
        }
        return temp;
    }

    //@Override
    //public boolean onSupportNavigateUp() {
        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_add_da_thing);
        //return NavigationUI.navigateUp(navController, appBarConfiguration)
                //|| super.onSupportNavigateUp();
    //}
}
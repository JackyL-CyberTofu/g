package com.cmpt276.scoreapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import com.cmpt276.scoreapp.MainActivity;
import com.google.gson.Gson;


import java.text.BreakIterator;

public class AddGame extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityAddDaThingBinding binding;
    GameManager gameManager = GameManager.getInstance();

    int position=0;
    boolean isEdit=false;
    String title;
    boolean hasEdit = false;


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

        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();

        if(extras!=null){
            position = extras.getInt("position");
            isEdit = extras.getBoolean("isEdit");
            title = extras.getString("title");
        }

        if(isEdit){
            Toast.makeText(this,"editing with title "+title,Toast.LENGTH_SHORT).show();
            //this.setTitle("Edit Game");
        }

        this.setTitle(title);

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

        //AlertDialog dialog3 = new AlertDialog.Builder(AddGame.this).setTitle("WARNING").setMessage("Unsaved Changed. Do you want to continue?").setPositiveButton("Confirm", null).setNegativeButton("Back", null).create();


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddGame.this,"back button", Toast.LENGTH_SHORT).show();
                if(hasEdit){
                    Toast.makeText(AddGame.this,"entered loop", Toast.LENGTH_SHORT).show();

                    AlertDialog dialog2 = new AlertDialog.Builder(AddGame.this).setTitle("WARNING").setMessage("Unsaved Changed. Do you want to continue?").setPositiveButton("Confirm", null).setNegativeButton("Back", null).show();

                    Button positiveButton = dialog2.getButton(AlertDialog.BUTTON_POSITIVE);
                    positiveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //gameManager.deleteGame(position+1);
                            //Toast.makeText(AddGame.this,"deleted game "+position,Toast.LENGTH_SHORT).show();
                            dialog2.dismiss();
                            finish();
                        }
                    });

                    Button negativeButton = dialog2.getButton(AlertDialog.BUTTON_NEGATIVE);
                    negativeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(AddGame.this, "Aborted", Toast.LENGTH_SHORT).show();
                            dialog2.dismiss();
                        }
                    });
                }
                else{
                    finish();
                }
            }
        });



        TextView scoresOne = (TextView)findViewById(R.id.scoresOne);
        TextView scoresTwo = (TextView)findViewById(R.id.scoresTwo);

        EditText player1Cards = (EditText)findViewById(R.id.player1Cards);
        EditText player2Cards = (EditText)findViewById(R.id.player2Cards);
        EditText player1Points = (EditText)findViewById(R.id.player1Points);
        EditText player2Points = (EditText)findViewById(R.id.player2Points);
        EditText player1Wager = (EditText)findViewById(R.id.player1Wager);
        EditText player2Wager = (EditText)findViewById(R.id.player2Wager);



        if(isEdit){
            player1Cards.setText(Integer.toString(gameManager.getGame(position).playerOneCards), TextView.BufferType.EDITABLE);
            player2Cards.setText(Integer.toString(gameManager.getGame(position).playerTwoCards), TextView.BufferType.EDITABLE);
            player1Points.setText(Integer.toString(gameManager.getGame(position).playerOnePoints), TextView.BufferType.EDITABLE);
            player2Points.setText(Integer.toString(gameManager.getGame(position).playerTwoPoints), TextView.BufferType.EDITABLE);
            player1Wager.setText(Integer.toString(gameManager.getGame(position).playerOneWager), TextView.BufferType.EDITABLE);
            player2Wager.setText(Integer.toString(gameManager.getGame(position).playerTwoWager), TextView.BufferType.EDITABLE);
            scoresOne.setText(Integer.toString(calculateScore(Integer.parseInt(player1Cards.getText().toString()), Integer.parseInt(player1Wager.getText().toString()), Integer.parseInt(player1Points.getText().toString()))));
            scoresTwo.setText(Integer.toString(calculateScore(Integer.parseInt(player2Cards.getText().toString()), Integer.parseInt(player2Wager.getText().toString()), Integer.parseInt(player2Points.getText().toString()))));
        }

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
                hasEdit = true;
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
                hasEdit = true;

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
                hasEdit = true;

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
                hasEdit = true;

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
                hasEdit = true;

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
                hasEdit = true;

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

    //Pressed Action bar button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if (item.getItemId() == R.id.saveButton){

                TextView temp = (TextView)findViewById(R.id.scoresOne);
                String temp3 = temp.getText().toString();

                temp = (TextView)findViewById(R.id.scoresTwo);
                String temp4 = temp.getText().toString();

                temp = (TextView)findViewById(R.id.player1Cards);
                String temp2 = temp.getText().toString();
                int player1cards = Integer.parseInt(temp2);

                temp = (TextView)findViewById(R.id.player2Cards);
                temp2 = temp.getText().toString();
                int player2cards = Integer.parseInt(temp2);

                temp = (TextView)findViewById(R.id.player1Points);
                temp2 = temp.getText().toString();
                int player1points = Integer.parseInt(temp2);

                temp = (TextView)findViewById(R.id.player2Points);
                temp2 = temp.getText().toString();
                int player2points = Integer.parseInt(temp2);

                temp = (TextView)findViewById(R.id.player1Wager);
                temp2 = temp.getText().toString();
                int player1wager = Integer.parseInt(temp2);

                temp = (TextView)findViewById(R.id.player2Wager);
                temp2 = temp.getText().toString();
                int player2wager = Integer.parseInt(temp2);

                if (!temp3.equals("-") && !temp4.equals("-") && player1cards!=0  && player1points!=0 && player1wager!=0 && player2cards!=0  && player2points!=0 && player2wager!=0||
                        !temp3.equals("-") && !temp4.equals("-") && player1cards==0  && player1points==0 && player1wager==0 && player2cards!=0  && player2points!=0 && player2wager!=0 ||
                        !temp3.equals("-") && !temp4.equals("-") && player1cards!=0  && player1points!=0 && player1wager!=0 && player2cards==0  && player2points==0 && player2wager==0||
                        !temp3.equals("-") && !temp4.equals("-") && player2cards==0  && player2points==0 && player2wager==0 && player1cards==0  && player1points==0 && player1wager==0

                ){

                    int score1 = Integer.parseInt(temp3);
                    int score2 = Integer.parseInt(temp4);

                    if (!isEdit){
                    Game test = new Game(player1cards,player2cards,player1points,player2points,player1wager,player2wager,score1,score2);
                    gameManager.addGame(test);}
                    else {
                        gameManager.getGame(position).playerOneWager = player1wager;
                        gameManager.getGame(position).playerOneCards = player1cards;
                        gameManager.getGame(position).playerOnePoints = player1points;
                        gameManager.getGame(position).playerOneScore = score1;

                        gameManager.getGame(position).playerTwoWager = player2wager;
                        gameManager.getGame(position).playerTwoCards = player2cards;
                        gameManager.getGame(position).playerTwoPoints = player2points;
                        gameManager.getGame(position).playerTwoScore = score2;

                        if(score1==score2){
                            gameManager.getGame(position).tie = true;
                            gameManager.getGame(position).playerOneWin = false;
                            gameManager.getGame(position).playerTwoWin = false;
                        }
                        else if (score1 > score2){
                            gameManager.getGame(position).tie = false;
                            gameManager.getGame(position).playerOneWin = true;
                            gameManager.getGame(position).playerTwoWin = false;
                        }
                        else{
                            gameManager.getGame(position).tie = false;
                            gameManager.getGame(position).playerOneWin = false;
                            gameManager.getGame(position).playerTwoWin = true;
                        }
                    }

                    //saveData();
                    Toast.makeText(this,"Added "+Integer.toString(gameManager.gameCount), Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    if (player1cards==0 || player2cards==0){
                        Toast.makeText(this,"If 0 Cards Played, others must be 0", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(this, "Empty fields detected", Toast.LENGTH_SHORT).show();
                    }
                }
        }
        else if (item.getItemId() == R.id.deleteButton){

            //gameManager.deleteGame(position+1);
            //Toast.makeText(AddGame.this,"deleted game "+position,Toast.LENGTH_SHORT).show();
            //finish();

            AlertDialog dialog = new AlertDialog.Builder(this).setTitle("WARNING").setMessage("Are you sure you want to delete?").setPositiveButton("Confirm", null).setNegativeButton("I'm scared", null).show();

            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isEdit) {
                        gameManager.deleteGame(position + 1);
                        Toast.makeText(AddGame.this, "deleted game " + position, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        finish();
                    }
                    else{
                        Toast.makeText(AddGame.this, "Game never saved", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
            });

            Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            negativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(AddGame.this, "Aborted", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
            //finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public int calculateScore(int cards, int wager, int points){
        int temp = (points-20)*(wager+1);
        if (cards >=8){
            temp = temp+20;
        }
        else if (cards == 0){
            return 0;
        }
        return temp;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Toast.makeText(this,"back button", Toast.LENGTH_SHORT).show();
            Intent refresh = new Intent(this, MainActivity.class);
            startActivity(refresh);
            this.finish();
        }
    }

    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(gameManager.games);
        editor.putString("games", json);
        editor.apply();
    }

    @Override
    public void onBackPressed(){
        //super.onBackPressed();
        //Toast.makeText(AddGame.this,"bottom back button", Toast.LENGTH_SHORT).show();
        //AlertDialog dialog3 = new AlertDialog.Builder(this).setTitle("WARNING").setMessage("Unsaved Changed. Do you want to continue?").setPositiveButton("Confirm", null).setNegativeButton("Back", null).show();

        //AlertDialog.Builder dialog3 = new AlertDialog.Builder(this);
        //dialog3.setTitle("Exit");
        //dialog3.setMessage("Are you sure you want to exit?");

        //dialog3.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            //@Override
            //public void onClick(DialogInterface dialogInterface, int i) {
                //finish();
            //}
        //});

        //dialog3.setNegativeButton("No", new DialogInterface.OnClickListener() {
            //@Override
            //public void onClick(DialogInterface dialogInterface, int i) {
                //dialogInterface.cancel();
            //}
        //});
        //dialog3.create().show();

        Toast.makeText(AddGame.this,"back button", Toast.LENGTH_SHORT).show();
        if(hasEdit){
            Toast.makeText(AddGame.this,"entered loop", Toast.LENGTH_SHORT).show();

            AlertDialog dialog2 = new AlertDialog.Builder(this).setTitle("WARNING").setMessage("Unsaved Changed. Do you want to continue?").setPositiveButton("Confirm", null).setNegativeButton("Back", null).show();

            Button positiveButton = dialog2.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //gameManager.deleteGame(position+1);
                    //Toast.makeText(AddGame.this,"deleted game "+position,Toast.LENGTH_SHORT).show();
                    dialog2.dismiss();
                    finish();
                }
            });

            Button negativeButton = dialog2.getButton(AlertDialog.BUTTON_NEGATIVE);
            negativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(AddGame.this, "Aborted", Toast.LENGTH_SHORT).show();
                    dialog2.dismiss();
                }
            });
        }
        else{
            finish();
        }

    }
    //@Override
    //public boolean onSupportNavigateUp() {
        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_add_da_thing);
        //return NavigationUI.navigateUp(navController, appBarConfiguration)
                //|| super.onSupportNavigateUp();
    //}
}
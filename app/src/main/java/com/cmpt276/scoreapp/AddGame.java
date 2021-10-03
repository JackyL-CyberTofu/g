// Add/Edit Game screen
// Allows you to change the values of the game.

package com.cmpt276.scoreapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;

import com.cmpt276.scoreapp.databinding.ActivityAddDaThingBinding;
import com.cmpt276.scoreapp.models.Game;
import com.cmpt276.scoreapp.models.MainActivity;

import java.util.Objects;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);



        Bundle extras = getIntent().getExtras();

        if(extras!=null){
            position = extras.getInt("position");
            isEdit = extras.getBoolean("isEdit");
            title = extras.getString("title");
        }

        this.setTitle(title);

        binding = ActivityAddDaThingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(isEdit){
            TextView time2 = (TextView) findViewById(R.id.timeText);
            time2.setText(gameManager.getGame(position).time);
        }
        // UP/BACK Button
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hasEdit){

                    AlertDialog dialog2 = new AlertDialog.Builder(AddGame.this).setTitle("WARNING").setMessage("Unsaved Changed. Do you want to continue?").setPositiveButton("Confirm", null).setNegativeButton("Back", null).show();

                    Button positiveButton = dialog2.getButton(AlertDialog.BUTTON_POSITIVE);
                    positiveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dialog2.dismiss();
                            finish();
                        }
                    });

                    Button negativeButton = dialog2.getButton(AlertDialog.BUTTON_NEGATIVE);
                    negativeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
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
            loadGameData(scoresOne, scoresTwo, player1Cards, player2Cards, player1Points, player2Points, player1Wager, player2Wager);
        }

        // Read Text
        player1Cards.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                hasEdit = true;
                if (charSequence.length() != 0 && player1Points.length() != 0 && player1Wager.length() !=0 )
                    temporarySave(scoresOne, player1Cards, player1Wager, player1Points);
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

                    temporarySave(scoresTwo, player2Cards, player2Wager, player2Points);

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

            @Override

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                hasEdit = true;

                if (charSequence.length() != 0 && player1Cards.length() != 0 && player1Wager.length() !=0 ){

                    temporarySave(scoresOne, player1Cards, player1Wager, player1Points);
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

                    temporarySave(scoresTwo, player2Cards, player2Wager, player2Points);

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

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                hasEdit = true;

                if (charSequence.length() != 0 && player1Points.length() != 0 && player1Cards.length() !=0 ){

                    temporarySave(scoresOne, player1Cards, player1Wager, player1Points);

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

                    temporarySave(scoresTwo, player2Cards, player2Wager, player2Points);

                }
                else {
                    scoresTwo.setText("-");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });

    }

    @SuppressLint("SetTextI18n")
    private void temporarySave(TextView scoresOne, EditText player1Cards, EditText player1Wager, EditText player1Points) {
        scoresOne.setText(Integer.toString(calculateScore(Integer.parseInt(player1Cards.getText().toString()), Integer.parseInt(player1Wager.getText().toString()), Integer.parseInt(player1Points.getText().toString()))));
    }

    @SuppressLint("SetTextI18n")
    private void loadGameData(TextView scoresOne, TextView scoresTwo, EditText player1Cards, EditText player2Cards, EditText player1Points, EditText player2Points, EditText player1Wager, EditText player2Wager) {
        player1Cards.setText(Integer.toString(gameManager.getGame(position).playerOneCards), TextView.BufferType.EDITABLE);
        player2Cards.setText(Integer.toString(gameManager.getGame(position).playerTwoCards), TextView.BufferType.EDITABLE);
        player1Points.setText(Integer.toString(gameManager.getGame(position).playerOnePoints), TextView.BufferType.EDITABLE);
        player2Points.setText(Integer.toString(gameManager.getGame(position).playerTwoPoints), TextView.BufferType.EDITABLE);
        player1Wager.setText(Integer.toString(gameManager.getGame(position).playerOneWager), TextView.BufferType.EDITABLE);
        player2Wager.setText(Integer.toString(gameManager.getGame(position).playerTwoWager), TextView.BufferType.EDITABLE);
        temporarySave(scoresOne, player1Cards, player1Wager, player1Points);
        temporarySave(scoresTwo, player2Cards, player2Wager, player2Points);
    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
                String temp6 = temp.getText().toString();
                temp = (TextView)findViewById(R.id.player2Cards);
                String temp7 = temp.getText().toString();

                if (!temp3.equals("-") && !temp4.equals("-") && !temp6.equals("0") && !temp7.equals("0")){
                    temp = (TextView)findViewById(R.id.scoresOne);
                    temp3 = temp.getText().toString();

                    temp = (TextView)findViewById(R.id.scoresTwo);
                    temp4 = temp.getText().toString();

                    temp = (TextView)findViewById(R.id.player1Cards);
                    String temp2 = temp.getText().toString();
                    int player1cards = 0;
                    if (!temp2.equals("")){
                    player1cards = Integer.parseInt(temp2);}

                    temp = (TextView)findViewById(R.id.player2Cards);
                    temp2 = temp.getText().toString();
                    int player2cards=0;
                    if (!temp2.equals("")){
                    player2cards = Integer.parseInt(temp2);}

                    temp = (TextView)findViewById(R.id.player1Points);
                    temp2 = temp.getText().toString();
                    int player1points=0;
                    if (!temp2.equals("")){
                    player1points = Integer.parseInt(temp2);}

                    temp = (TextView)findViewById(R.id.player2Points);
                    temp2 = temp.getText().toString();
                    int player2points=0;
                    if (!temp2.equals("")){
                    player2points = Integer.parseInt(temp2);}

                    temp = (TextView)findViewById(R.id.player1Wager);
                    temp2 = temp.getText().toString();
                    int player1wager=0;
                    if (!temp2.equals("")){
                    player1wager = Integer.parseInt(temp2);}

                    temp = (TextView)findViewById(R.id.player2Wager);
                    temp2 = temp.getText().toString();
                    int player2wager=0;
                    if (!temp2.equals("")){
                    player2wager = Integer.parseInt(temp2);
                    }

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
                        updateGameData(player1cards, player2cards, player1points, player2points, player1wager, player2wager, score1, score2);

                        findVictoryPlayer(score1, score2);
                    }}
                    finish();
                }
                else {
                    temp = (TextView)findViewById(R.id.player1Cards);
                    temp6 = temp.getText().toString();
                    temp = (TextView)findViewById(R.id.player2Cards);
                    temp7 = temp.getText().toString();
                    if (temp6.equals("0")){
                        Toast.makeText(this,"If 0 Cards Played, others must be 0", Toast.LENGTH_SHORT).show();
                    }
                    else if(temp7.equals("0")){
                        Toast.makeText(this,"If 0 Cards Played, others must be 0", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(this, "Empty fields detected", Toast.LENGTH_SHORT).show();
                    }
                }
        }
        else if (item.getItemId() == R.id.deleteButton){

            AlertDialog dialog = new AlertDialog.Builder(this).setTitle("WARNING").setMessage("Are you sure you want to delete?").setPositiveButton("Confirm", null).setNegativeButton("I'm scared", null).show();

            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isEdit) {
                        gameManager.deleteGame(position + 1);
                        dialog.dismiss();
                        finish();
                    }
                    else{
                        dialog.dismiss();
                    }
                }
            });

            Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            negativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

        }

        return super.onOptionsItemSelected(item);
    }

    private void updateGameData(int player1cards, int player2cards, int player1points, int player2points, int player1wager, int player2wager, int score1, int score2) {
        updatePlayer1(player1cards, player1points, player1wager, score1);
        updatePlayer2(player2cards, player2points, player2wager, score2);
    }

    private void updatePlayer2(int player2cards, int player2points, int player2wager, int score2) {
        gameManager.getGame(position).playerTwoWager = player2wager;
        gameManager.getGame(position).playerTwoCards = player2cards;
        gameManager.getGame(position).playerTwoPoints = player2points;
        gameManager.getGame(position).playerTwoScore = score2;
    }

    private void updatePlayer1(int player1cards, int player1points, int player1wager, int score1) {
        gameManager.getGame(position).playerOneWager = player1wager;
        gameManager.getGame(position).playerOneCards = player1cards;
        gameManager.getGame(position).playerOnePoints = player1points;
        gameManager.getGame(position).playerOneScore = score1;
    }

    private void findVictoryPlayer(int score1, int score2) {
        if(score1 == score2){
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
            Intent refresh = new Intent(this, MainActivity.class);
            startActivity(refresh);
            this.finish();
        }
    }

    @Override
    public void onBackPressed(){

        if(hasEdit){

            AlertDialog dialog2 = new AlertDialog.Builder(this).setTitle("WARNING").setMessage("Unsaved Changed. Do you want to continue?").setPositiveButton("Confirm", null).setNegativeButton("Back", null).show();

            Button positiveButton = dialog2.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog2.dismiss();
                    finish();
                }
            });

            Button negativeButton = dialog2.getButton(AlertDialog.BUTTON_NEGATIVE);
            negativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog2.dismiss();
                }
            });
        }
        else{
            finish();
        }

    }

}
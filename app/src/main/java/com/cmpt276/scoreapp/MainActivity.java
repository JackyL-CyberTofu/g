package com.cmpt276.scoreapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.cmpt276.scoreapp.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    GameManager gameManager = GameManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        //TextView testView = (TextView)findViewById(R.id.test);

        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        //appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(view -> {
            Intent i = AddGame.makeLaunchIntent(MainActivity.this,"Add New Game");
            startActivity(i);
        });

        populateListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //Update UI after backing out
    @Override
    protected  void onResume(){
        super.onResume();
        updateUI();
    }

    private void updateUI(){
        //TextView a = findViewById(R.id.test);
        //Toast.makeText(this,"Exit Edit", Toast.LENGTH_SHORT).show();
        //if (gameManager.gameCount >= 1){
            //Toast.makeText(this,"Max score "+gameManager.getGame(0).maxScore, Toast.LENGTH_SHORT).show();
            //a.setText( gameManager.getGame(0).getMaxScoreString() );
        //}
    }

    private void populateListView(){
        ArrayAdapter<Game> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.gamelist);
        list.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<Game>{
        public MyListAdapter(){
            super(MainActivity.this,R.layout.item_view,gameManager.games);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View itemView = convertView;
            if (itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
            }

            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView1);
            TextView test = (TextView) itemView.findViewById(R.id.textView1);
            test.setText(gameManager.getGame(position).getMaxScoreString());

            //Fill the view
            return itemView;
        }
    }



}
// Main Activity App
// Displays the Empty State and ListView
// Jacky Lim

package com.cmpt276.scoreapp.models;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.cmpt276.scoreapp.AddGame;
import com.cmpt276.scoreapp.GameManager;
import com.cmpt276.scoreapp.R;
import com.cmpt276.scoreapp.databinding.ActivityMainBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    GameManager gameManager = GameManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        loadData();
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        this.setTitle("Score App");

        binding.fab.setOnClickListener(view -> {
            Intent i = AddGame.makeLaunchIntent(MainActivity.this,"Add New Game");
            i.putExtra("position", 0);
            i.putExtra("isEdit", false);
            i.putExtra("title", "New Game");
            startActivity(i);
        });

        ArrayAdapter<Game> adapter = new MyListAdapter();
        populateListView();
        ListView list = (ListView) findViewById(R.id.gamelist);
        list.setAdapter(adapter);
        list.setEmptyView(findViewById(R.id.emptyList));

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent k = AddGame.makeLaunchIntent(MainActivity.this,"Add New Game");
                k.putExtra("position", i);
                k.putExtra("isEdit", true);
                k.putExtra("title", "Edit Game");
                startActivity(k);

            }
        });



    }

    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(gameManager.games);
        editor.putString("games", json);
        editor.apply();
    }

    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("games", "");
        Type type = new TypeToken<ArrayList<Game>>() {}.getType();
        gameManager.games = gson.fromJson(json,type);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
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
        saveData();
        ArrayAdapter<Game> adapter = new MyListAdapter();
        populateListView();
        ListView list = (ListView) findViewById(R.id.gamelist);
        list.setAdapter(adapter);
        if(adapter.isEmpty()){
            ImageView emptyImage = (ImageView) findViewById(R.id.emptyImage);
            emptyImage.setVisibility(View.VISIBLE);
        }
        else{
            ImageView emptyImage = (ImageView) findViewById(R.id.emptyImage);
            emptyImage.setVisibility(View.GONE);
        }
        adapter.notifyDataSetChanged();
    }

    private void populateListView(){

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


            TextView upper = (TextView) itemView.findViewById(R.id.textView1);
            upper.setText(String.format("%s vs %s", gameManager.getGame(position).getScore1(), gameManager.getGame(position).getScore2()));

            TextView under = (TextView) itemView.findViewById(R.id.textView2);
            if (gameManager.getGame(position).tie){
                under.setText(gameManager.getGame(position).time);
                imageView.setImageResource(R.drawable.ic_baseline_crop_square_24);
            }
            else if(gameManager.getGame(position).playerOneWin){
                under.setText(gameManager.getGame(position).time);
                imageView.setImageResource(R.drawable.ic_baseline_looks_one_24);
            }
            else if (gameManager.getGame(position).playerTwoWin){
                under.setText(gameManager.getGame(position).time);
                imageView.setImageResource(R.drawable.ic_baseline_looks_two_24);

            }

            //Fill the view
            return itemView;
        }
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


}
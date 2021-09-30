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
import android.widget.AdapterView;
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


    //ArrayAdapter<Game> adapter = new MyListAdapter();
    //ArrayAdapter<Game> adapter;


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
            i.putExtra("position", 0);
            i.putExtra("isEdit", false);
            i.putExtra("title", "New Game");
            startActivity(i);
        });

        ArrayAdapter<Game> adapter = new MyListAdapter();
        populateListView();
        ListView list = (ListView) findViewById(R.id.gamelist);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (gameManager.gameCount!=0){
                Toast.makeText(view.getContext(), "Clicked on item "+i, Toast.LENGTH_SHORT).show();}
                Intent k = AddGame.makeLaunchIntent(MainActivity.this,"Add New Game");
                k.putExtra("position", i);
                k.putExtra("isEdit", true);
                k.putExtra("title", "Edit Game");
                startActivity(k);
                //adapter.notifyDataSetChanged();
            }
        });

        //adapter.notifyDataSetChanged();

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

    }

    private void updateUI(){

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
            upper.setText(gameManager.getGame(position).getScore1() + " vs " + gameManager.getGame(position).getScore2());

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
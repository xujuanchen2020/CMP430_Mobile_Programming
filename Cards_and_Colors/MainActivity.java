package com.example.cards_and_colors;

import android.content.ClipData;
import android.content.res.TypedArray;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Sport> sportsData;
    private SportsAdapter sportsAdapter;

    private int dragDirections = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN;
    private int swipeDirections = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // how many columns to display
        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);
        if(gridColumnCount >1){
            swipeDirections = 0;
        }

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(dragDirections, swipeDirections) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();
                Collections.swap(sportsData, from, to);
                sportsAdapter.notifyItemMoved(from, to);
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                sportsData.remove(viewHolder.getAdapterPosition());
                sportsAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, gridColumnCount));

        itemTouchHelper.attachToRecyclerView(recyclerView);

        sportsData = new ArrayList<>();
        sportsAdapter = new SportsAdapter(this, sportsData);
        recyclerView.setAdapter(sportsAdapter);

        //loading sportsData
        loadSportData();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                loadSportData();
            }
        });
    }

    private void loadSportData() {
        sportsData.clear();
        String[] sportsTitles = getResources().getStringArray(R.array.sports_titles);
        String[] sportsInfo = getResources().getStringArray(R.array.sports_info);
        TypedArray sportsImages = getResources().obtainTypedArray(R.array.sports_images);


        for(int i=0; i<sportsTitles.length; i++){
            sportsData.add(new Sport(sportsTitles[i],sportsInfo[i],sportsImages.getResourceId(i,0)));
        }

        sportsAdapter.notifyDataSetChanged();
        sportsImages.recycle();
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
}

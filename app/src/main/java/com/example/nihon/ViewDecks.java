package com.example.nihon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ViewDecks extends AppCompatActivity {
    // creating variables for the array list, dbhandler, adapter and recycler view.
    private ArrayList<Flashcard> decksArrayList;
    private DBHandler dbHandler;
    private DeckRVAdapter deckRVAdapter;
    private RecyclerView decksRV;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_decks);

        // initializing all variables.
        decksArrayList = new ArrayList<>();
        dbHandler = new DBHandler(ViewDecks.this);

        // getting the words array list from db handler class.
        decksArrayList = dbHandler.readDecks();

        // passing the array list to the adapter class.
        deckRVAdapter = new DeckRVAdapter(decksArrayList, ViewDecks.this);
        decksRV = findViewById(R.id.idRVDecks);

        // setting layout manager for the recycler view.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewDecks.this, RecyclerView.VERTICAL, false);
        decksRV.setLayoutManager(linearLayoutManager);

        // setting the adapter to recycler view.
        decksRV.setAdapter(deckRVAdapter);
    }

    public void open_home_page (View view){
        Intent it_home_page = new Intent(this, HomeActivity.class);
        startActivity(it_home_page);
    }
}

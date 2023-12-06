package com.example.nihon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ViewCards extends AppCompatActivity {
    // creating variables for the array list, dbhandler, adapter and recycler view.
    private ArrayList<Flashcard> cardsArrayList;
    private DBHandler dbHandler;
    private CardRVAdapter cardRVAdapter;
    private RecyclerView cardsRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cards);

        // initializing all variables.
        cardsArrayList = new ArrayList<>();
        dbHandler = new DBHandler(ViewCards.this);

        // getting the words array list from db handler class.
        cardsArrayList = dbHandler.readCards();

        // passing the array list to the adapter class.
        cardRVAdapter = new CardRVAdapter(cardsArrayList, ViewCards.this);
        cardsRV = findViewById(R.id.idRVDecks);

        // setting layout manager for the recycler view.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewCards.this, RecyclerView.VERTICAL, false);
        cardsRV.setLayoutManager(linearLayoutManager);

        // setting the adapter to recycler view.
        cardsRV.setAdapter(cardRVAdapter);
    }

    public void open_add_card_view (View view){
        Intent it_add_card = new Intent(this, AddCardActivity.class);
        startActivity(it_add_card);
    }

    public void open_home_page (View view){
        Intent it_home_page = new Intent(this, HomeActivity.class);
        startActivity(it_home_page);
    }
}

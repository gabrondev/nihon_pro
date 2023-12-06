package com.example.nihon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void open_view_cards(View view){
        Intent it_view_cards = new Intent(HomeActivity.this, ViewCards.class);
        startActivity(it_view_cards);
    }

    public void open_play(View view){
        Intent it_play = new Intent(HomeActivity.this, ViewDecks.class);
        startActivity(it_play);
    }
}
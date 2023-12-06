package com.example.nihon;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddCardActivity extends AppCompatActivity {
    // creating variables for our edittext, button and dbhandler
    private EditText edtDeckName, edtWordJapanese, edtWordRomaji, edtWordTranslated;
    private Spinner spnDeckName;
    private ImageButton btnViewCards;
    private ImageButton btnAddCard;
    private DBHandler dbHandler;
    boolean newDeck = true;
    String deckName;



    private SQLiteDatabase database;
    private Spinner spinnerDeckName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);


        database = openOrCreateDatabase("nihondb", MODE_PRIVATE, null);
        spinnerDeckName = findViewById(R.id.spnDeckName);

        String query = "SELECT DISTINCT deckName FROM flashcards";
        Cursor cursor = database.rawQuery(query, null);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        if (cursor.moveToFirst()) {
            do {
                String deckNameResult = cursor.getString(cursor.getColumnIndex("deckName"));
                adapter.add(deckNameResult);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        spinnerDeckName.setAdapter(adapter);




        // initializing all our variables.
        edtDeckName = findViewById(R.id.txtDeckName);
        spnDeckName = findViewById(R.id.spnDeckName);
        edtWordJapanese = findViewById(R.id.idEdtJapanese);
        edtWordRomaji = findViewById(R.id.idEdtTranslated);
        edtWordTranslated = findViewById(R.id.idTxtTranslation);
        btnAddCard = findViewById(R.id.idBtnEditWord);

        // creating a new dbhandler class
        // and passing our context to it.
        dbHandler = new DBHandler(AddCardActivity.this);

        // below line is to add on click listener for our add course button.
        btnAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinnerDeckName.getVisibility() == View.VISIBLE){
                    deckName = spinnerDeckName.getSelectedItem().toString();
                } else{
                    deckName = edtDeckName.getText().toString();
                }

                String wordJapanese = edtWordJapanese.getText().toString();
                String wordRomaji = edtWordRomaji.getText().toString();
                String wordTranslated = edtWordTranslated.getText().toString();

                // validating if the text fields are empty or not.
                if (wordJapanese.isEmpty() || wordRomaji.isEmpty()
                        || deckName.isEmpty() || wordTranslated.isEmpty()) {
                    Toast.makeText(AddCardActivity.this, "Por favor, preencha todos os campos...", Toast.LENGTH_SHORT).show();
                    return;
                }

                // on below line we are calling a method to add new
                // course to sqlite data and pass all our values to it.
                dbHandler.addCard(deckName, wordJapanese, wordRomaji, wordTranslated);

                // after adding the data we are displaying a toast message.
                Toast.makeText(AddCardActivity.this, "Cartão criado com sucesso.", Toast.LENGTH_SHORT).show();
//                edtDeckName.setText("");
                edtWordJapanese.setText("");
                edtWordRomaji.setText("");
                edtWordTranslated.setText("");
            }
        });

        btnViewCards = findViewById(R.id.idViewCardsBtn);
        btnViewCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // opening a new activity via a intent.
                Intent i = new Intent(AddCardActivity.this, ViewCards.class);
                startActivity(i);
            }
        });
    }
    public void open_flashcards_screen(View view){
        Intent it_flashcards = new Intent(this, ViewCards.class);
        startActivity(it_flashcards);
    }

    public void add_new_deck(View view){
        ImageButton button;
        button = findViewById(R.id.btnChooseDeck);
        if(newDeck){
            edtDeckName.setVisibility(View.VISIBLE);
            spnDeckName.setVisibility(View.GONE);
        } else {
            edtDeckName.setVisibility(View.GONE);
            spnDeckName.setVisibility(View.VISIBLE);
        }

        newDeck = !newDeck;
    }
}

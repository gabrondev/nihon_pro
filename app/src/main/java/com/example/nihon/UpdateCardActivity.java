package com.example.nihon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateCardActivity extends AppCompatActivity {
    // variables for our edit text, button, strings and dbhandler class.
    private EditText edtDeckName, wordJapaneseEdt, wordRomajiEdt, wordTranslatedEdt;
    private ImageButton btnUpdateCard, btnDeleteCard;
    private DBHandler dbHandler;
    String deckName, wordJapanese, wordRomaji, wordTranslated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_card);

        // initializing all our variables.
        edtDeckName = findViewById(R.id.idEdtDeck);
        wordJapaneseEdt = findViewById(R.id.idEdtJapaneseWord);
        wordRomajiEdt = findViewById(R.id.idEdtTranslatedWord);
        wordTranslatedEdt = findViewById(R.id.idEdtTranslation);
        btnUpdateCard = findViewById(R.id.idBtnEditWord);
        btnDeleteCard = findViewById(R.id.idBtnDelete);

        // on below line we are initializing our dbhandler class.
        dbHandler = new DBHandler(UpdateCardActivity.this);

        // on below lines we are getting data which
        // we passed in our adapter class.
        deckName = getIntent().getStringExtra("deckName");
        wordJapanese = getIntent().getStringExtra("wordJapanese");
        wordRomaji = getIntent().getStringExtra("wordRomaji");
        wordTranslated = getIntent().getStringExtra("wordTranslated");

        // setting data to edit text
        // of our update activity.
        edtDeckName.setText(deckName);
        wordJapaneseEdt.setText(wordJapanese);
        wordRomajiEdt.setText(wordRomaji);
        wordTranslatedEdt.setText(wordTranslated);

        // adding on click listener to our update course button.
        btnUpdateCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // inside this method we are calling an update course
                // method and passing all our edit text values.
                dbHandler.updateCard(wordJapanese, edtDeckName.getText().toString(), wordJapaneseEdt.getText().toString(),
                                        wordRomajiEdt.getText().toString(), wordTranslatedEdt.getText().toString());

                // displaying a toast message that our course has been updated.
                Toast.makeText(UpdateCardActivity.this, "Cartão editado", Toast.LENGTH_SHORT).show();

                // launching our main activity.
                Intent i = new Intent(UpdateCardActivity.this, ViewCards.class);
                startActivity(i);
            }
        });

        // adding on click listener for delete button to delete our course.
        btnDeleteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling a method to delete our course.
                dbHandler.deleteCard(wordJapanese);
                Toast.makeText(UpdateCardActivity.this, "Cartão apagado", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(UpdateCardActivity.this, ViewCards.class);
                startActivity(i);
            }
        });
    }

    public void open_flashcards_screen(View view){
        Intent it_flashcards = new Intent(this, ViewCards.class);
        startActivity(it_flashcards);
    }
}

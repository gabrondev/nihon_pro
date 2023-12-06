package com.example.nihon;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class PlayActivity extends AppCompatActivity {
    private SQLiteDatabase database;
    private Cursor cursorWordsJapanese, cursorWordsRomaji, cursorWordsTranslated;
    private int wordsJapaneseCurrentIndex = 0, wordsRomajiCurrentIndex = 0, wordsTranslatedCurrentIndex = 0;
    private Button btnFlashcard;
    private TextView txtDeckName, txtWordJapanese, txtWordRomaji, txtWordTranslated;
    private String[] wordsJapanese, wordsRomaji, wordsTranslated;
    private int totalWordsJapanese, totalWordsRomaji, totalWordsTranslated;
    private boolean romajiRevealed, cardJustEntered;
    String deckName, queryWordsJapanese, queryWordsRomaji, queryWordsTranslated;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        btnFlashcard = findViewById(R.id.btnFlashcard);
        txtDeckName = findViewById(R.id.txtDeckName);
        txtWordJapanese = findViewById(R.id.txtWordJapanese);
        txtWordRomaji = findViewById(R.id.txtWordRomaji);
        txtWordTranslated = findViewById(R.id.txtWordTranslated);
        database = openOrCreateDatabase("nihondb", MODE_PRIVATE, null);

        deckName = getIntent().getStringExtra("deckName");
        txtDeckName.setText(deckName);

        queryWordsJapanese = "SELECT wordJapanese FROM flashcards WHERE deckName = ?";
        queryWordsRomaji = "SELECT wordRomaji FROM flashcards WHERE deckName = ?";
        queryWordsTranslated = "SELECT wordTranslated FROM flashcards WHERE deckName = ?";

        cursorWordsJapanese = database.rawQuery(queryWordsJapanese, new String[] { deckName });
        cursorWordsRomaji = database.rawQuery(queryWordsRomaji, new String[] { deckName });
        cursorWordsTranslated = database.rawQuery(queryWordsTranslated, new String[] { deckName });

        totalWordsJapanese = cursorWordsJapanese.getCount();
        totalWordsRomaji = cursorWordsRomaji.getCount();
        totalWordsTranslated = cursorWordsTranslated.getCount();

        wordsJapanese = new String[totalWordsJapanese];
        wordsRomaji = new String[totalWordsRomaji];
        wordsTranslated = new String[totalWordsTranslated];

        if (totalWordsJapanese > 0) {
            cursorWordsJapanese.moveToFirst();
            for (int i = 0; i < totalWordsJapanese; i++) {
                wordsJapanese[i] = cursorWordsJapanese.getString(0);
                cursorWordsJapanese.moveToNext();
            }
        } else {wordsJapanese = new String[]{"Nenhum cartão cadastrado..."};}

        if (totalWordsRomaji > 0) {
            cursorWordsRomaji.moveToFirst();
            for (int i = 0; i < totalWordsRomaji; i++) {
                wordsRomaji[i] = cursorWordsRomaji.getString(0);
                cursorWordsRomaji.moveToNext();
            }
        } else {wordsRomaji = new String[]{"Nenhum cartão cadastrado..."};}

        if (totalWordsTranslated > 0) {
            cursorWordsTranslated.moveToFirst();
            for (int i = 0; i < totalWordsTranslated; i++) {
                wordsTranslated[i] = cursorWordsTranslated.getString(0);
                cursorWordsTranslated.moveToNext();
            }
        } else {wordsTranslated = new String[]{"Nenhum cartão cadastrado..."};}

        btnFlashcard.setText(wordsJapanese[0]);
        txtWordJapanese.setText(wordsJapanese[0]);
        txtWordRomaji.setText("???");
        txtWordTranslated.setText("???");

        cursorWordsJapanese.close();
        cursorWordsRomaji.close();
        cursorWordsTranslated.close();
        database.close();
    }
    public void turnFlashcard(View view) {
        if (!cardJustEntered){
            txtWordRomaji.setText(wordsRomaji[wordsRomajiCurrentIndex]);
            txtWordTranslated.setText(wordsTranslated[wordsTranslatedCurrentIndex]);
        }

        if(romajiRevealed){
            btnFlashcard.setText(wordsJapanese[wordsJapaneseCurrentIndex]);
        } else{
            btnFlashcard.setText(wordsRomaji[wordsRomajiCurrentIndex]);
        }
        romajiRevealed = !romajiRevealed;
        cardJustEntered = true;
    }

    public void nextFlashcard(View view){
        if(wordsJapaneseCurrentIndex < wordsJapanese.length - 1){
            wordsJapaneseCurrentIndex++;
            wordsRomajiCurrentIndex++;
            wordsTranslatedCurrentIndex++;

            if (wordsJapaneseCurrentIndex < wordsJapanese.length) {
                btnFlashcard.setText(wordsJapanese[wordsJapaneseCurrentIndex]);
                txtWordJapanese.setText(wordsJapanese[wordsJapaneseCurrentIndex]);
                txtWordRomaji.setText("???");
                txtWordTranslated.setText("???");
            }
            romajiRevealed = false;
            cardJustEntered = false;
        } else {
            showFinishedAlert();
        }
    }
    public void showFinishedAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Você revisou todos os cartões deste baralho!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(PlayActivity.this, ViewDecks.class);
                        startActivity(intent);
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void open_decks (View view){
        Intent it_decks = new Intent(this, ViewDecks.class);
        startActivity(it_decks);
    }
}

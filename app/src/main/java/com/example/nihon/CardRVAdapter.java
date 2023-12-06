package com.example.nihon;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CardRVAdapter extends RecyclerView.Adapter<CardRVAdapter.ViewHolder> {
    // variable for the array list and context
    private ArrayList<Flashcard> cardsArrayList;
    private Context context;

    // constructor
    public CardRVAdapter(ArrayList<Flashcard> cardsArrayList, Context context) {
        this.cardsArrayList = cardsArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating the layout file for the recycler view items.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // setting data to the views of recycler view item.
        Flashcard card = cardsArrayList.get(position);
        holder.txtDeckName.setText(card.getDeckName());
        holder.txtWordJapanese.setText(card.getWordJapanese());
        holder.txtWordRomaji.setText(card.getWordRomaji());
        holder.txtWordTranslated.setText(card.getWordTranslated());

        // below line is to add on click listener for our recycler view item.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // on below line we are calling an intent.
                Intent i = new Intent(context, UpdateCardActivity.class);

                // below we are passing all our values.
                i.putExtra("deckName", card.getDeckName());
                i.putExtra("wordJapanese", card.getWordJapanese());
                i.putExtra("wordRomaji", card.getWordRomaji());
                i.putExtra("wordTranslated", card.getWordTranslated());

                // starting our activity.
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        // returning the size of array list
        return cardsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for text views.
        private TextView txtDeckName, txtWordJapanese, txtWordRomaji, txtWordTranslated;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing text views
            txtDeckName = itemView.findViewById(R.id.txtDeckName);
            txtWordJapanese = itemView.findViewById(R.id.txtDeck);
            txtWordRomaji = itemView.findViewById(R.id.idTxtTranslated);
            txtWordTranslated = itemView.findViewById(R.id.idTxtTranslation);
        }
    }
}

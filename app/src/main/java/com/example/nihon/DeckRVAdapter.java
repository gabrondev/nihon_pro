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

public class DeckRVAdapter extends RecyclerView.Adapter<DeckRVAdapter.ViewHolder> {

    // variable for our array list and context
    private ArrayList<Flashcard> decksArrayList;
    private Context context;

    // constructor
    public DeckRVAdapter(ArrayList<Flashcard> decksArrayList, Context context) {
        this.decksArrayList = decksArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // on below line we are inflating our layout
        // file for our recycler view items.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.deck_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // on below line we are setting data
        // to our views of recycler view item.
        Flashcard deck = decksArrayList.get(position);
        holder.txtDeckName.setText(deck.getDeckName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are calling an intent.
                Intent i = new Intent(context, PlayActivity.class);

                // below we are passing all our values.
                i.putExtra("deckName", deck.getDeckName());

                // starting our activity.
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list
        return decksArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        private TextView txtDeckName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            txtDeckName = itemView.findViewById(R.id.txtDeckName);
        }
    }
}

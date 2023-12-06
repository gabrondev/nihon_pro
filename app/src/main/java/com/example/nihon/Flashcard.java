package com.example.nihon;

public class Flashcard {
    // variables for the japanese words, translated words and id
    private String deckName;
    private String wordJapanese;
    private String wordRomaji;
    private String wordTranslated;
    private int id;

    // creating getter and setter methods
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDeckName() { return deckName; }
    public void setDeckName(String deckName) { this.deckName = deckName; }
    public String getWordJapanese() { return wordJapanese; }
    public void setWordJapanese(String wordJapanese) { this.wordJapanese = wordJapanese; }
    public String getWordRomaji() { return wordRomaji; }
    public void setWordRomaji(String courseDuration) { this.wordRomaji = wordRomaji; }
    public String getWordTranslated() { return wordTranslated; }
    public void setWordTranslated(String wordTranslated) { this.wordTranslated = wordTranslated; }

    // constructor
    public Flashcard(String deckName, String wordJapanese,
                     String wordRomaji, String wordTranslated)
    {
        this.deckName = deckName;
        this.wordJapanese = wordJapanese;
        this.wordRomaji = wordRomaji;
        this.wordTranslated = wordTranslated;
    }

    public Flashcard(String deckName)
    {
        this.deckName = deckName;
    }
}

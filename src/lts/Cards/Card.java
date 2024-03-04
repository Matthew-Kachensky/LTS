package lts.Cards;

import lts.utils.CardType;

public abstract class Card{

    protected String name;
    protected String description;
    protected CardType cardType;
    protected boolean canChallenge;

    public Card(String name, String description, CardType ct, boolean canChallenge){
        this.canChallenge = canChallenge;
        this.cardType = ct;
        this.name = name;
        this.description = description;
    }

    public String getName(){
        return this.name;
    };
    public CardType getCardType(){
        return this.cardType;
    };
    public boolean getCanChallenge(){
        return this.canChallenge;
    };
    public String getDescription(){
        return this.description;
    };
}
package lts.Cards.Characters;

import lts.Cards.Card;
import lts.utils.CardType;
import lts.utils.Constants;
import lts.utils.RegionType;

public abstract class Character extends Card {
    protected RegionType region;

    public Character(String name, CardType ct, boolean canChallenge) {
        super(name, Constants.descriptions.get(name), ct, canChallenge);
    }

    public RegionType getRegion(){
        return this.region;
    }

    public void print(){
        System.out.println(this.name);
        System.out.println("Card Description: " + this.description);
    }
}
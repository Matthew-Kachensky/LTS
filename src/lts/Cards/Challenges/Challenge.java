package lts.Cards.Challenges;

import lts.Cards.Card;
import lts.utils.CardType;

public class Challenge extends Card{
    private static Challenge instance;

    public static Challenge getInstance(){
        if(instance == null){
            instance = new Challenge();
        }
        return instance;
    }

    private Challenge(){
        super("Challenge",
                "You may play this card when another player attempts to play a Hero, Item, or Magic card. CHALLENGE that card.",
                CardType.CHALLENGE, false);
    }

}
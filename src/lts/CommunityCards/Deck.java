package lts.CommunityCards;

import lts.Cards.Card;
import lts.Cards.Challenges.Challenge;
import lts.Cards.Characters.Hero.Hero;
import lts.Cards.Characters.Hero.HeroFactory;
import lts.Cards.Items.Item;
import lts.Cards.Items.ItemFactory;
import lts.Cards.Modifiers.ModFactory;
import lts.utils.Constants;

import java.util.*;

public class Deck {
    private final List<Card> deck;

    public Deck() {
        this.deck = new ArrayList<>();

        int numChall = 14;

        // Names of items and their count in the deck
        Map<String, Integer> numItem = Map.of(
                "Death's Dance", 1,
                "Zz'Rot Portal", 1,
                "Banner of Command", 1,
                "Frozen Heart", 1,
                "Guinsoo's Rageblade", 1,
                "Shrink Ray", 1,
                "Doran's Ring", 2,
                "Kleptomancy", 1,
                "ZZ'Rot portal", 2
        );

        // Names of cursed items and corresponding count
        Map<String, Integer> numCItem = Map.of(
                "Anethemas chains", 2,
                "Silence", 1,
                "Chain of corruption", 1
        );

        // Modifiers
        /*
        Map<String, Integer> numMod = Map.of(
                "+2/-2", 9,
                "+1/-3", 4,
                "-1/+3", 4,
                "+4", 4,
                "-4", 4
        );*/

        // Spells
        Map<String, Integer> numSpell = Map.of(

        );

        // Add all items
        for(String s: Constants.itemNames){
            for(int i = 0; i < numItem.get(s); i++) {
                deck.add(ItemFactory.createItem(s));
            }
        }

        /*
        for(String s : numCItem.keySet()){
            for(int i = 0; i < numCItem.get(s); i++){
                deck.add((ItemFactory.createItem(s)));
            }
        }
        */

        // Add all mods
        /*
        for(String s : numMod.keySet()){
            for(int i = 0; i < numMod.get(s); i++){
                deck.add(ModFactory.createMod(s));
            }
        }*/

        for(String s : numSpell.keySet()){
            for(int i = 0; i < numSpell.get(s); i++){
//                deck.add(SpellFactory.createSpell(s));
            }
        }

        // Add all heroes
        for(String s : Constants.heroNames){
            deck.add(HeroFactory.createHero(s));
        }

        // Only need 1 challenge object for all
        Challenge c = Challenge.getInstance();
        for(int i = 0; i < numChall; i++){
            deck.add(c);
        }

        // Shuffle the deck
        Collections.shuffle(deck);
    }

    public int size(){
        return this.deck.size();
    }

    /**
     * Removes and returns the top card of the deck
     * @return the card that is drawn
     */
    public Card Draw(){
        return this.deck.remove(0);
    }

}
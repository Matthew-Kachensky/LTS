package lts.CommunityCards;

import lts.Cards.Card;
import lts.Cards.Characters.Hero.Hero;
import lts.Cards.Items.Item;
import lts.Cards.Spells.Spell;
import lts.utils.CardType;

import java.util.ArrayList;
import java.util.List;

public class Discard {
    private List<Card> discarded;

    private List<Card> heroes;

    private List<Card> spells;

    private List<Card> items;
    private int numChallenges;

    public Discard(){
        this.discarded = new ArrayList<>();
        this.heroes = new ArrayList<>();
        this.spells = new ArrayList<>();
        this.items = new ArrayList<>();
        this.numChallenges = 0;
    }

    /**
     * Discards a card c
     * @param c the card to discard
     */
    public void discard(Card c){
        // Check if the card is a hero and has an item
        this.checkItem(c);

        // Discard the card
        this.discarded.add(c);

        // Add it to the respective pile
        switch(c.getCardType()){
            case HERO -> {
                this.heroes.add(c);
                break;
            }
            case ITEM -> {
                this.items.add(c);
                break;
            }
            case SPELL -> {
                this.spells.add(c);
                break;
            }
            case CHALLENGE -> {
                this.numChallenges++;
                break;
            }
        }
    }


    /**
     * Checks if the card is a hero and if they have an item
     * @param c the card to check
     */
    public void checkItem(Card c){

        // Check if hero
        if(c.getCardType() == CardType.HERO){
            Hero h = (Hero) c; // Cast to get item
            if(h.hasItem()){ // If they have an item
                // Add the item to the discard pile separately
                this.discarded.add(h.getItem());

                // Remove the item from the hero
                h.addItem(null);
            }
        }
    }



    /**
     * Gets a card from the discard pile
     * @param i the location of the card
     * @return The card from the discard pile
     */
    public Card getCard(int i){
        return this.discarded.remove(i);
    }

    /**
     * Gets a hero from the discard pile
     * @param i the hero to remove
     * @return the hero that has been removed from the pile
     */
    public Card getHero(int i){
        if(i > this.heroes.size()){
            return null;
        }
        Card c = this.heroes.remove(i);
        this.discarded.remove(c);
        return c;
    }

    /**
     * Get a spell from the discard pile
     * @param i the spell to remove
     * @return the spell that has been removed
     */
    public Card getSpell(int i){
        if(i > this.spells.size()){
            return null;
        }
        Card c = this.spells.remove(i);
        this.discarded.remove(c);
        return c;
    }

    /**
     * Prints the discard pile
     */
    public void print(){
        int i = 0;
        for(Card c : this.discarded){
            System.out.println(i++ + ". " + c.getName());
        }
    }

    public void printHeroes(){
        int i = 0;
        for(Card c : this.heroes){
            Hero h = (Hero) c;
            System.out.print(i++ + ". ");
            h.print();
        }
    }

    /**
     * Checks if the discard pile has a hero card
     * @return true if it true, false otherwise
     */
    public boolean hasHeroes(){
//        boolean bool = false;
//        for(Card c : this.discarded){
//            if (c.getCardType() == CardType.HERO) {
//                bool = true;
//            }
//        }
//        return bool;
        return !this.heroes.isEmpty();
    }


    /**
     * Checks if the discard pile has a spell card
     * @return true if it does, false otherwise
     */
    public boolean hasSpells(){
        return !this.spells.isEmpty();
    }

    public void printSpells(){
        int i = 1;
        for(Card c : this.spells){
            Spell s = (Spell) c;
            System.out.print(i++ + ". ");
            s.print();
        }
    }
    /**
     * Checks if the discard pile has an item card
     * @return true if it does, false otherwise
     */
    public boolean hasItems(){
        return !this.items.isEmpty();
    }
}


